package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessRuntimeException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.service.UserService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个性测试后端
 *
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/personalityTest")
public class PersonalityTestController {

    public static final String SPLITTER = ";";

    public static final String ROLE_COACH = "coach";
    public static final String ROLE_STUDENT = "student";

    public static final Map<Integer, PictureUrl> indexToPictureUrlMap = new HashMap<>(9);
    public static final Map<Integer, Personality[]> indexToPersonalityMap = new HashMap<>(9);

    @Autowired
    public UserService userService;

    static {
        for (PictureUrl pictureUrl : PictureUrl.values()) {
            indexToPersonalityMap.put(pictureUrl.getIndex(), pictureUrl.getValues());
            indexToPictureUrlMap.put(pictureUrl.getIndex(), pictureUrl);
        }
    }

    @RequestMapping(value = "/doTest", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject doTestOperation(HttpServletRequest request) {
        //selectImg should like this : "1;3;5"
        String selectImg = request.getParameter("selectImg");
        String roleName = request.getParameter("roleName");
        String[] splitImgs = selectImg.split(SPLITTER);
        Map<Personality, Integer> personalityCountMap = new HashMap<>(4);

        StringBuilder finalContent = new StringBuilder();

        for (String splitImg : splitImgs) {
            int selectImgIndex = Integer.parseInt(splitImg);
            PictureUrl pictureUrl = indexToPictureUrlMap.get(selectImgIndex);
            finalContent.append(String.format("您选择了图片: %s <br/> %s <br/> %s <br/><br/>",selectImgIndex,
                    pictureUrl.getTitle(), pictureUrl.getContent()));

            for (Personality personality : getPersonality(selectImgIndex)) {
                if (!personalityCountMap.containsKey(personality)) {
                    personalityCountMap.put(personality, 1);
                } else {
                    personalityCountMap.put(personality, personalityCountMap.get(personality) + 1);
                }
            }
        }

        IFinalPersonality finalPersonality = analyseFinalPersonality(personalityCountMap);
        String stringPersonality = finalPersonality.getStringPersonality(roleName);
        finalContent.append(stringPersonality);
        Object userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
        if(userId != null){
            userService.updatePersonalTest(selectImg,roleName, userId.toString());
        }
        return new ResponseJsonObject(true, finalContent.toString());
    }

    /**
     * 如果我选择 3 张喜欢的图片是 3、4、7，对应的个性是：
     * C+C+P+D+C
     * C 最多，所以我属于 C 个性，测试结果页中显示 C 个性对应的文案解释和 3、4、7 对应的文案解释（具体文案我重新整理一遍，后续给出）。
     * 如果我选择2张喜欢的图片是 3、7，对应的个性是：
     * C+D+C
     * C 最多，所有我属于 C 个性，测试结果页中显示 C 个性对应的文案解释和 3、7 对应的文案解释。
     * 如果我选择 3 张喜欢的图片是 1、2、3，对应的个性是：
     * E+D+E+D+C
     * E 和 D 都最多，所有我属于 E + D 个性，测试结果页显示 “您拥有下面两种个性”，下面先后显示 E 和 D 对应的文案解释和 1、2、3 对应的文案解释。
     * 如果我选择 1、3，或者 1、4，对应的个性分别是：
     * E + D +C 和 E + D + C + P
     * 类型最多的数值达到 3 或 4，那么我属于 S 个性，测试结果页中显示 S 个性对应的文案解释。
     *
     * @param personalityCountMap
     * @return
     */
    private IFinalPersonality analyseFinalPersonality(Map<Personality, Integer> personalityCountMap) {
        int maxCount = 0;
        List<Personality> maxPersonalityList = new ArrayList<>(4);
        for (Map.Entry<Personality, Integer> entry : personalityCountMap.entrySet()) {
            Personality personality = entry.getKey();
            Integer currentCount = entry.getValue();
            if (maxCount < currentCount) {
                maxCount = currentCount;
                maxPersonalityList.clear();
                maxPersonalityList.add(personality);
            } else if (maxCount == currentCount) {
                maxPersonalityList.add(personality);
            }
        }
        IFinalPersonality finalPersonality = null;
        if (maxPersonalityList.size() >= 3) {
            finalPersonality = new SinglePersonality(Personality.Sigma);
        } else if (maxPersonalityList.size() == 1) {
            finalPersonality = new SinglePersonality(maxPersonalityList.get(0));
        } else if (maxPersonalityList.size() == 2) {
            finalPersonality = new TwicePersonality(maxPersonalityList.get(0), maxPersonalityList.get(1));
        } else {
            throw new BusinessRuntimeException("分析不出具体最终人格：" + maxPersonalityList.size() + ": " + maxPersonalityList);
        }
        return finalPersonality;
    }

    private Personality[] getPersonality(int index) {
        if (index > 9 || index < 1) {
            throw new BusinessRuntimeException(String.format("找不到:%s 对应的性格，没有相关图片!", index));
        }
        return indexToPersonalityMap.get(index);
    }


    /**
     *
     */
    public static enum Personality {
        /**
         *
         */
        Dominance("充满力量的控制型、支配型",
                "你好胜，自信，自律，权威，敏锐，个性积极，竞争力、执行力、决断力、攻击力强，喜欢冒险、掌控全局、发号施令，对抗性强，不用手照做俯卧撑，可单手把玩双手剑，不满足于现状，胸怀大志，勇往直前，不畏反抗。\n" +
                        "示例：毛泽东，朱熔基，撒切尔夫人（前英国首相），施振荣（宏碁），韦尔奇（美国GE前总裁），夏侯渊、夏侯惇（魏），关羽、张飞（蜀），孙悟空。\n" +
                        "该类人占比最多的国家：德国。<br/>" +
                        "行为表象：语速快，说话直截了当，对听者目光直视；目的明确，行动迅速。<br/>" +
                        "<br/>" +
                        "适合开创性、改革性工作，在开拓市场的时代或需要执行改革的环境中，最容易有出色的表现。",
                "你好胜，自信，自律，权威，敏锐，个性积极，竞争力、执行力、决断力、攻击力强，喜欢冒险、掌控全局、发号施令，对抗性强，不用手照做俯卧撑，可单手把玩双手剑，不满足于现状，胸怀大志，勇往直前，不畏反抗。<br/>" +
                        "示例：施振荣（宏碁），韦尔奇（美国GE前总裁），夏侯渊、夏侯惇（魏），关羽、张飞（蜀），孙悟空，战士、术士（WOW）。<br/>" +
                        "该类人占比最多的国家：德国。<br/>" +
                        "行为表象：语速快，说话直截了当，对听者目光直视；目的明确，行动迅速。<br/>" +
                        "<br/>" +
                        "适合开创性、改革性工作，在开拓市场的时代或需要执行改革的环境中，最容易有出色的表现。"),

        /**
         *
         */
        Conscientiousness("追求完美的分析型、责任型",
                "你传统，保守，拘谨，含蓄，谨守分寸，忠于职责，重视纪律，分析力强，前瞻性强，精确度高，思路清晰，条理分明，说服力强，做事不分心，品质保证者，处事客观合理，注重细节，喜欢把细节条理化。<br/>" +
                        "示例：刘少奇，包青天，比尔·盖茨，郭嘉（魏）。<br/>" +
                        "该类人占比最多的国家：日本。<br/>" +
                        "行为表象：面部表情少；动作缓慢；说话用词精确；注意细节；事事以规则为准绳；不擅长语言沟通。<br/>" +
                        "<br/>" +
                        "架构稳定和制度健全的组织最好用你来当各级领导人，因为你喜欢在安全架构的环境中工作，表现也会最好。其行事讲究制度化，事事求依据和规律的习性，极为适合事务机构的行事方式。你尊重传统、重视架构、事事求据、喜爱工作安定的性格，是企业安定力量的来源。<br/>",
                "你传统，保守，拘谨，含蓄，谨守分寸，忠于职责，重视纪律，分析力强，前瞻性强，精确度高，思路清晰，条理分明，说服力强，做事不分心，品质保证者，处事客观合理，注重细节，喜欢把细节条理化。<br/>" +
                        "示例：包青天，比尔·盖茨，郭嘉（魏）。<br/>" +
                        "该类人占比最多的国家：日本。<br/>" +
                        "行为表象：面部表情少；动作缓慢；说话用词精确；注意细节；事事以规则为准绳；不擅长语言沟通。<br/>" +
                        "<br/>" +
                        "架构稳定和制度健全的组织最好用你来当各级领导人，因为你喜欢在安全架构的环境中工作，表现也会最好。其行事讲究制度化，事事求依据和规律的习性，极为适合事务机构的行事方式。你尊重传统、重视架构、事事求据、喜爱工作安定的性格，是企业安定力量的来源。<br/>"),

        /**
         * ：
         */
        Extroversion("活泼的表现型、影响型",
                "你热心，乐观，和善，诚恳，活泼，口才好，人缘好，脸皮厚，没心没肺，幽默风趣，惹人喜爱，热情洋溢，好交朋友，不减肥，却依旧风度翩翩，表现欲强，影响力强，富同情心，表达、社交能力强，擅于建立人际关系，容易广结善缘、建立知名度。<br/>" +
                        "示例：孙中山，克林顿，里根，戈尔巴乔夫，史玉柱，马云，石滋宜（台湾企管大师），史蒂夫·鲍尔默（微软），刘备（蜀），孙权（吴）。<br/>" +
                        "该类人占比最多的国家：美国。<br/>" +
                        "行为表象：沟通时手势快；面部表情丰富；运用有说服力的语言。<br/>" +
                        "<br/>" +
                        "你适合人际导向、当众表现、引人注目、态度公开的工作。你天生具有鼓吹理想的特质，在推动新思维、执行新使命或推广宣传等任务的工作中，会有出色的表现。你在开发市场或创建产业的工作环境中，最能发挥所长。",
                "你热心，乐观，和善，诚恳，活泼，口才好，人缘好，脸皮厚，没心没肺，幽默风趣，惹人喜爱，热情洋溢，好交朋友，不减肥，却依旧风度翩翩，表现欲强，影响力强，富同情心，表达、社交能力强，擅于建立人际关系，容易广结善缘、建立知名度。<br/>" +
                        "示例：史玉柱，马云，石滋宜（台湾企管大师），史蒂夫·鲍尔默（微软），刘备（蜀），孙权（吴），八戒，法师（WOW）。<br/>" +
                        "该类人占比最多的国家：美国。<br/>" +
                        "行为表象：沟通时手势快；面部表情丰富；运用有说服力的语言。<br/>" +
                        "<br/>" +
                        "你适合人际导向、当众表现、引人注目、态度公开的工作。你天生具有鼓吹理想的特质，在推动新思维、执行新使命或推广宣传等任务的工作中，会有出色的表现。你在开发市场或创建产业的工作环境中，最能发挥所长。"),

        /**
         *
         */
        Patience("崇尚和平的友善型、耐心型、稳健型",
                "你稳定，敦厚，温和，善良，忠诚，和事佬，可信赖，行事稳健，强调平实，性情平和，善解人意，感情敏感，耐力过人，不善变，不劈腿，不跳槽，不篡位，不好冲突，不喜欢制造麻烦，不兴风作浪，容易左右逢源，生活讲求规律，行事冷静自持、泰然自若，防高血厚，最佳调解者，“路遥知马力”的最佳典型，世界末日后的唯一幸存者。<br/>" +
                        "示例：甘地（印度），朱德，蒋经国，宋庆龄，萨姆·沃尔顿（沃尔玛创始人），张昭（吴），沙僧。<br/>" +
                        "该类人占比最多的国家：中国。<br/>" +
                        "行为表象：和蔼可亲；说话慢条斯理，声音轻柔；用赞同型、鼓励性的语言。<br/>" +
                        "<br/>" +
                        "你适宜从事安定内部的管理工作，在需要专业精密技巧的领域，或在气氛和谐且不具赶迫时间表等的职场环境中，最能发挥所长。当企业的产品稳踞市场时，你将是极佳的总舵手。你强调无为而治，能与周围的人和睦相处而不树敌，是极佳的人事领导者，适宜在企业改革后，为公司和员工重建互信的工作。又由于你具有高度的耐心，有能力为企业赚取长远的利益，或为公司打好永续经营的基础。",
                "你稳定，敦厚，温和，善良，忠诚，和事佬，可信赖，行事稳健，强调平实，性情平和，善解人意，感情敏感，耐力过人，不善变，不劈腿，不跳槽，不篡位，不好冲突，不喜欢制造麻烦，不兴风作浪，容易左右逢源，生活讲求规律，行事冷静自持、泰然自若，防高血厚，最佳调解者，“路遥知马力”的最佳典型，世界末日后的唯一幸存者。<br/>" +
                        "示例：萨姆·沃尔顿（沃尔玛创始人），张昭（吴），沙僧，圣骑士、萨满祭司、牧师（WOW）。<br/>" +
                        "该类人占比最多的国家：中国。<br/>" +
                        "行为表象：和蔼可亲；说话慢条斯理，声音轻柔；用赞同型、鼓励性的语言。<br/>" +
                        "<br/>" +
                        "你适宜从事安定内部的管理工作，在需要专业精密技巧的领域，或在气氛和谐且不具赶迫时间表等的职场环境中，最能发挥所长。当企业的产品稳踞市场时，你将是极佳的总舵手。你强调无为而治，能与周围的人和睦相处而不树敌，是极佳的人事领导者，适宜在企业改革后，为公司和员工重建互信的工作。又由于你具有高度的耐心，有能力为企业赚取长远的利益，或为公司打好永续经营的基础。<br/>"),

        /**
         *
         */
        Sigma("整合型",
                "你中庸而不极端，不树敌，不落败，不掉血，沟通能力强，是天生的谈判家，处事圆融，弹性、适应性极强，魔法免疫，懂得顺水推舟，善于调整角色适应环境，是个能游走折中的高手，尊崇“没有原则就是最高原则”。<br/>" +
                        "示例：周恩来，基辛格（美国前国务卿），诸葛亮，曹操，唐僧。<br/>" +
                        "该类人占比最多的地区：港台。<br/>" +
                        "行为表象：没有强烈的个人意识形态。<br/>" +
                        "<br/>" +
                        "你具有高度的应变能力，处事极具弹性，能为了适应环境的要求而调整其决定甚至信念。<br/>" +
                        "你是支配型、责任型、表现型、稳健型四种特质的综合体，没有突出的个性，对事也没有什么强烈的个人意识形态，擅长整合内外资源，兼容并蓄，以中庸之道处世，事事求中立并倾向站在没有立场的位置，在冲突的环境中如鱼得水。你做事处处留有余地，绝对不会走偏锋极端，是一个办事让人放心的人物。由于你能密切地融合于各种环境中，可以为企业进行对内对外的各种交涉，只要任务确实和目标清楚，你都能恰如其分地完成任务。",
                "你中庸而不极端，不树敌，不落败，不掉血，沟通能力强，是天生的谈判家，处事圆融，弹性、适应性极强，魔法免疫，懂得顺水推舟，善于调整角色适应环境，是个能游走折中的高手，尊崇“没有原则就是最高原则”。<br/>" +
                        "示例：诸葛亮，曹操，唐僧，盗贼、猎人、德鲁伊（WOW）。<br/>" +
                        "该类人占比最多的地区：港台。<br/>" +
                        "行为表象：没有强烈的个人意识形态。<br/>" +
                        "<br/>" +
                        "你具有高度的应变能力，处事极具弹性，能为了适应环境的要求而调整其决定甚至信念。<br/>" +
                        "你是支配型、责任型、表现型、稳健型四种特质的综合体，没有突出的个性，对事也没有什么强烈的个人意识形态，擅长整合内外资源，兼容并蓄，以中庸之道处世，事事求中立并倾向站在没有立场的位置，在冲突的环境中如鱼得水。你做事处处留有余地，绝对不会走偏锋极端，是一个办事让人放心的人物。由于你能密切地融合于各种环境中，可以为企业进行对内对外的各种交涉，只要任务确实和目标清楚，你都能恰如其分地完成任务。<br/>"
        );

        private String overAll;
        private String coachConclude;
        private String studentConclude;

        Personality(String overAll, String coachConclude, String studentConclude) {
            this.overAll = overAll;
            this.coachConclude = coachConclude;
            this.studentConclude = studentConclude;
        }

        public String getOverAll() {
            return overAll;
        }

        public String getCoachConclude() {
            return coachConclude;
        }

        public String getStudentConclude() {
            return studentConclude;
        }
    }

    /**
     * 图片与个性对应关系：
     * 1 - E+D
     * 2 - E+D
     * 3 - C
     * 4 - C+P
     * 5 - D
     * 6 - P
     * 7 - D+C
     * 8 - E+P
     * 9 - E
     */
    public static enum PictureUrl {
        ONE(1, new Personality[]{Personality.Extroversion, Personality.Dominance}, "无忧无虑，顽皮，愉快",
                "你喜欢自由自在，无拘无束的生活。你的座右铭是：生命只能活一次，因此你尽量享受每一刻。你好奇心旺盛，对新事物抱有开放的态度；你向往改变，讨厌束缚。你觉得身边的环境都不断在变，而且经常为你带来惊喜。"),
        TWO(2, new Personality[]{Personality.Extroversion, Personality.Dominance}, "独立，前卫，不受拘束",
                "你追求自由及不受拘束，自我的生活。你的工作及消闲活动都与艺术有关。你对于自由的渴求有时候会使你做出令人出人意表的事。你的生活方式极具个人色彩；你永远不会盲目追逐潮流。相反地，你会根据自己的意思和信念去生活，就算是逆流而上也在所不惜。"),
        THREE(3, new Personality[]{Personality.Conscientiousness}, "时常自我反省，敏感的思想家",
                "你对于自己及四周的环境能够比一般人控制得更好更彻底。你讨厌表面化及肤浅的东西；你宁愿独自一人也不愿跟别人闲谈，但你跟朋友的关系却非常深入，这令你的心境保持和谐安逸。你不介意长时间独自一人，而且绝少会觉得沉闷。"),
        FOUR(4, new Personality[]{Personality.Conscientiousness, Personality.Patience}, "务实，头脑清醒，和谐",
                "你作风自然，喜欢简单的东西。人们欣赏你脚踏实地，他们觉得你稳重，值得信赖。你能够给予身边的人安全感，你给人一种亲切，温暖的感觉。你对于俗气的，花花绿绿的东西都不屑一顾，对潮流抱着怀疑的态度。"),
        FIVE(5, new Personality[]{Personality.Dominance}, "专业，实事求事，自信",
                "你掌管自己的生活，你相信自己的能力多于相信命运的安排。你以实际，简单的方式去解决问题。你对日常生活中所遇到的事物抱有现实的看法，并且能够应付自如。人们知道你可担重任，因此都放心把大量工作交给你处理。你那坚强的意志使你时刻都充满信心。未达到自己的目标之前，你绝不罢休。"),
        SIX(6, new Personality[]{Personality.Patience}, "温和，谨慎，无攻击性",
                "你生性随和，但处事谨慎。你很容易认识朋友，但同时享受你的私人时间及独立生活。有时候，你会从人群中抽身而出，一个人静静地思考生活的意义，并自娱一番。你需要个人的空间，因此有时会隐匿于美梦当中，但你并不是一个爱孤独的人。你跟自己及这个世界都能够和睦共处，而你对现状亦非常满意。"),
        SEVER(7, new Personality[]{Personality.Dominance, Personality.Conscientiousness}, "具分析力，可靠，自信",
                "你对事物的灵敏度令你可以发现到旁人忽略了的东西。这些就是你的宝石，你喜欢发掘这些美好的东西。你的教养对于你的生活有很特别的影响。你有自己高雅独特的一套，无视任何潮流。你的理想生活是优雅而愉快的，而你亦希望跟你接触的人们都是高雅而有教养的。"),
        EIGHT(8, new Personality[]{Personality.Extroversion, Personality.Patience}, "浪漫，爱幻想，情绪化",
                "你是一个感性的人。你拒绝只从一个严肃，理智的角度去理解事物。你的感觉亦十分重要。事实上，你觉得人生必需要有梦想才叫活得充实。你不接受那些轻视浪漫主义及被理智牵着鼻子走的人；而且不会让任何事物影响到你那丰富的感情及情绪。"),
        NINE(9, new Personality[]{Personality.Extroversion}, "精力充沛，好动，外向",
                "你不介意冒险，特别喜欢有趣的，多元化的工作。相比之下，例行公事及惯例会令你没精打采。你最兴奋的是可以积极参与任何比赛活动，因为这样你就可以在众人面前大显身手。");

        private int index;
        private Personality[] values;
        private String title;
        private String content;

        PictureUrl(int index, Personality[] values, String title, String content) {
            this.index = index;
            this.values = values;
            this.title = title;
            this.content = content;
        }

        public int getIndex() {
            return index;
        }

        public Personality[] getValues() {
            return values;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }

    public static interface IFinalPersonality {
        String getStringPersonality(String roleName);
    }

    public static class SinglePersonality implements IFinalPersonality {

        private final Personality personality;

        public SinglePersonality(Personality personality) {
            this.personality = personality;
        }

        @Override
        public String getStringPersonality(String roleName) {
            return String.format("您的个性为: %s <br/> 具体表现为: %s <br/>", personality.getOverAll(),
                    roleName.equals(ROLE_COACH) ? personality.getCoachConclude() : personality.getStudentConclude());
        }
    }

    public static class TwicePersonality implements IFinalPersonality {
        private final Personality firstPersonality;
        private final Personality secondPersonality;

        public TwicePersonality(
                Personality firstPersonality,
                Personality secondPersonality) {
            this.firstPersonality = firstPersonality;
            this.secondPersonality = secondPersonality;
        }

        @Override
        public String getStringPersonality(String roleName) {
            return String.format("您同时拥有下列个性: <br/> 1. %s <br/> 具体表现为: %s <br/> 2. %s <br/> 具体表现为: %s <br/>",
                    firstPersonality.getOverAll(), roleName.equals(ROLE_COACH) ? firstPersonality.getCoachConclude() : firstPersonality.getStudentConclude()
                    , secondPersonality.getOverAll(), roleName.equals(ROLE_COACH) ? secondPersonality.getCoachConclude() : secondPersonality.getStudentConclude());
        }
    }

}
