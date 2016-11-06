package com.happydriving.rockets.server.component.crontask;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.component.message.MailConfiguration;
import com.happydriving.rockets.server.component.message.MailSender;
import com.happydriving.rockets.server.component.tools.JdbcTemplateComponent;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author mazhiqiang
 */
@Component
public class DailyReportSendTask {

    private static final Log LOG = LogFactory.getLog(DailyReportSendTask.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JdbcTemplateComponent jdbcTemplateComponent;

    public static final String SIGNUP_SQL = "select service_city.name as cityName, count(*) as total " +
            "from ds_signup_student " +
            "left outer join service_city on service_city.id = ds_signup_student.service_city_id " +
            "where signup_date %s group by service_city.name";

    public static final String POTENTIAL_SQL = "select service_city.name as cityName, count(*) as total " +
            "from ds_potential_student " +
            "left outer join service_city on service_city.id = ds_potential_student.service_city_id " +
            "where record_date %s group by service_city.name";

    public static final String GIVENUP_SQL = "select service_city.name as cityName, count(*) as total " +
            "from ds_signup_student " +
            "inner join service_city on service_city.id = ds_signup_student.service_city_id " +
            "where study_progress='STUDY_PROGRESS_TF' " +
            "and ds_signup_student.last_updated_stamp > '%s' " +
            "group by service_city.name;";

    public static final String TOTAL_NAME = "合计";

    public void sendDailyReportToday() {
        DailyReportObject dailyReportObject = new DailyReportObject();

        DateTime dateTime = DateTime.now().plusHours(-1);
        String firstDayOfMonth = new DateTime(
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                1, 0, 0).toString("yyyy-MM-dd");

        List<Map<String, Object>> citySqlList = jdbcTemplateComponent.queryForList("select name from service_city");
        List<String> cityNameList = new ArrayList<>(3);
        for (Map<String, Object> citySql : citySqlList) {
            cityNameList.add((String)citySql.get("name"));
        }
        cityNameList.add(TOTAL_NAME);
        dailyReportObject.setCityList(cityNameList);


        String monthlyCitySignupSql = String.format(SIGNUP_SQL,
                String.format(" > '%s'", firstDayOfMonth));
        List<Map<String, Object>> stringObjectMap = jdbcTemplateComponent.queryForList(monthlyCitySignupSql);
        Map<String, Long> monthlySignupMap = new HashMap<>(3);
        long total = 0;
        for (Map<String, Object> objectMap : stringObjectMap) {
            long signupTotal = (Long) objectMap.get("total");
            monthlySignupMap.put((String) objectMap.get("cityName"), signupTotal);
            total += signupTotal;
        }
        monthlySignupMap.put(TOTAL_NAME, total);
        dailyReportObject.setMonthlySignupMap(monthlySignupMap);

        String todayCitySignupSql = String.format(SIGNUP_SQL,
                String.format(" = '%s' ", dateTime.toString("yyyy-MM-dd")));
        stringObjectMap = jdbcTemplateComponent.queryForList(todayCitySignupSql);
        Map<String, Long> todaySignupMap = new HashMap<>(3);
        total = 0;
        for (Map<String, Object> objectMap : stringObjectMap) {
            long signupTotal = (Long) objectMap.get("total");
            todaySignupMap.put((String) objectMap.get("cityName"), signupTotal);
            total += signupTotal;
        }
        todaySignupMap.put(TOTAL_NAME, total);
        dailyReportObject.setTodaySignupMap(todaySignupMap);


        String monthlyPotentialSql = String.format(POTENTIAL_SQL,
                String.format(" > '%s'", firstDayOfMonth));
        stringObjectMap = jdbcTemplateComponent.queryForList(monthlyPotentialSql);
        Map<String, Long> monthlyPotentialMap = new HashMap<>(3);
        total = 0;
        for (Map<String, Object> objectMap : stringObjectMap) {
            long signupTotal = (Long) objectMap.get("total");
            monthlyPotentialMap.put((String) objectMap.get("cityName"), signupTotal);
            total += signupTotal;
        }
        monthlyPotentialMap.put(TOTAL_NAME, total);
        dailyReportObject.setMonthlyPotentialMap(monthlyPotentialMap);

        String todayPotentialSql = String.format(POTENTIAL_SQL,
                String.format(" = '%s' ", dateTime.toString("yyyy-MM-dd")));
        stringObjectMap = jdbcTemplateComponent.queryForList(todayPotentialSql);
        Map<String, Long> todayPotentialMap = new HashMap<>(3);
        total = 0;
        for (Map<String, Object> objectMap : stringObjectMap) {
            long signupTotal = (Long) objectMap.get("total");
            todayPotentialMap.put((String) objectMap.get("cityName"), signupTotal);
            total += signupTotal;
        }
        todayPotentialMap.put(TOTAL_NAME, total);
        dailyReportObject.setTodayPotentialupMap(todayPotentialMap);

        String givenupSql = String.format(GIVENUP_SQL, firstDayOfMonth);
        stringObjectMap = jdbcTemplateComponent.queryForList(givenupSql);
        Map<String, Long> givenupMap = new HashMap<>(3);
        total = 0;
        for (Map<String, Object> objectMap : stringObjectMap) {
            long signupTotal = (Long) objectMap.get("total");
            givenupMap.put((String) objectMap.get("cityName"), signupTotal);
            total += signupTotal;
        }
        givenupMap.put(TOTAL_NAME, total);
        dailyReportObject.setGivenupMap(givenupMap);

        List<DailyReportElement> dailyReportElements = dailyReportObject.toDailyReportElements();
        MailConfiguration mailConfiguration = new MailConfiguration();
        String subject =
                String.format("CRM系统定时报表: %1$tF %1$tT 的学员报告", new Date());
        mailConfiguration.setSubject(subject);
        mailConfiguration.setMailToReceipients(
                new ArrayList<String>(CommonConstants.CITY_TO_MAILBOX_MAP.values()));
//                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"mazhiqiang@ejiapei.com"}));
//        mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"gaochao@ejiapei.com"}));
        mailConfiguration.setTemplate("crm-report-daily.ftl");
        mailConfiguration.setMailObject(dailyReportElements);
        try {
            mailSender.sendTemplateMail(mailConfiguration);
        } catch (BusinessException e) {
            LOG.error(e);
        }
    }

    public static class DailyReportObject {
        private List<String> cityList;

        private Map<String, Long> monthlySignupMap;
        private Map<String, Long> todaySignupMap;

        private Map<String, Long> monthlyPotentialMap;
        private Map<String, Long> todayPotentialupMap;

        private Map<String, Long> givenupMap;

        public void setCityList(List<String> cityList) {
            this.cityList = cityList;
        }

        public void setMonthlySignupMap(Map<String, Long> monthlySignupMap) {
            this.monthlySignupMap = monthlySignupMap;
        }

        public void setTodaySignupMap(Map<String, Long> todaySignupMap) {
            this.todaySignupMap = todaySignupMap;
        }

        public void setMonthlyPotentialMap(Map<String, Long> monthlyPotentialMap) {
            this.monthlyPotentialMap = monthlyPotentialMap;
        }

        public void setGivenupMap(Map<String, Long> givenupMap) {
            this.givenupMap = givenupMap;
        }

        public void setTodayPotentialupMap(Map<String, Long> todayPotentialupMap) {
            this.todayPotentialupMap = todayPotentialupMap;
        }

        public List<DailyReportElement> toDailyReportElements() {
            List<DailyReportElement> result = new ArrayList<>(3);
            for (String cityName : cityList) {
                long monthSignupCount = getCountFromKey(monthlySignupMap, cityName);
                long monthContactCount = monthSignupCount + getCountFromKey(monthlyPotentialMap, cityName);
                long todaySignupCount = getCountFromKey(todaySignupMap, cityName);
                long todayContactCount = todaySignupCount + getCountFromKey(todayPotentialupMap, cityName);
                long givenupCount = getCountFromKey(givenupMap, cityName);
                result.add(new DailyReportElement(cityName, monthContactCount, monthSignupCount,
                        todayContactCount, todaySignupCount, givenupCount));
            }
            return result;
        }

        private Long getCountFromKey(Map<String, Long> map, String key) {
            return map.containsKey(key) ? map.get(key) : 0L;
        }
    }

    public static class DailyReportElement {
        private String cityName;
        private long monthContactCount;
        private long monthSignupCount;
        private long todayContactCount;
        private long todaySignupCount;
        private long monthGivenupCount;

        public DailyReportElement(String cityName, long monthContactCount, long monthSignupCount,
                                  long todayContactCount,
                                  long todaySignupCount, long monthGivenupCount) {
            this.cityName = cityName;
            this.monthContactCount = monthContactCount;
            this.monthSignupCount = monthSignupCount;
            this.todayContactCount = todayContactCount;
            this.todaySignupCount = todaySignupCount;
            this.monthGivenupCount = monthGivenupCount;
        }

        public String getCityName() {
            return cityName;
        }

        public long getMonthContactCount() {
            return monthContactCount;
        }

        public long getMonthSignupCount() {
            return monthSignupCount;
        }

        public long getTodayContactCount() {
            return todayContactCount;
        }

        public long getTodaySignupCount() {
            return todaySignupCount;
        }

        public long getMonthGivenupCount() {
            return monthGivenupCount;
        }
    }

}
