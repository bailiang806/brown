package com.happydriving.rockets.server.component.crontask;

import com.happydriving.rockets.campaign.spread.model.PotentialStudentDisplayed;
import com.happydriving.rockets.campaign.spread.model.SpreadStudentDetailInfo;
import com.happydriving.rockets.campaign.spread.service.SpreadStudentService;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.component.message.MailConfiguration;
import com.happydriving.rockets.server.component.message.MailSender;
import com.happydriving.rockets.server.entity.ServiceCity;
import com.happydriving.rockets.server.service.ServiceCityService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 用于记录每个潜在学员发送邮件的任务
 *
 * @author mazhiqiang
 */
@Component
public class PotentialStudentSendTask {

    private static final Log LOG = LogFactory.getLog(PotentialStudentSendTask.class);


    @Autowired
    private MailSender mailSender;

    @Autowired
    private SpreadStudentService spreadStudentService;

    @Autowired
    private ServiceCityService serviceCityService;

    /**
     * 每半个小时发送已经收集到的数据
     */
    public void executeSendHalfHour() throws BusinessException {
        Date currentDate = new Date();

        DateTime dateTime = DateTime.now().plusHours(-1);
        Date startDate = new DateTime(
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                dateTime.getDayOfMonth(),
                dateTime.getHourOfDay(),
                dateTime.getMinuteOfHour()).toDate();

        List<ServiceCity> allServiceCities = serviceCityService.getAllServiceCities();
        for (ServiceCity serviceCity : allServiceCities) {
            Integer cityId = serviceCity.getId();
            String cityName = serviceCity.getName();

            List<SpreadStudentDetailInfo> spreadStudents =
                    spreadStudentService.getSpreadStudentDetailsByCondition(cityId, startDate, currentDate);
            if (!spreadStudents.isEmpty()) {
                //send message
                List<PotentialStudentDisplayed> potentialStudentDisplayeds = new ArrayList<>();
                for (SpreadStudentDetailInfo spreadStudentDetailInfo : spreadStudents) {
                    potentialStudentDisplayeds.add(convertToPotentialStudent(spreadStudentDetailInfo));
                }

                MailConfiguration mailConfiguration = new MailConfiguration();
                String subject =
                        String.format("城市: %s 小时时间段(%2$tF %2$tT 至 %3$tF %3$tT) 的潜在学员报告", cityName, startDate,
                                currentDate);
                mailConfiguration.setSubject(subject);
                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{CommonConstants.CITY_TO_MAILBOX_MAP.get(cityId)}));
//                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"gaochao@ejiapei.com"}));
                mailConfiguration.setTemplate("halfhour-student.ftl");
                mailConfiguration.setMailObject(potentialStudentDisplayeds);
                mailSender.sendTemplateMail(mailConfiguration);
                LOG.info("发送邮件成功，标题为: " + subject);
            } else {
                LOG.info(String.format("城市: %s 无潜在学员的数据，暂不发送邮件!", cityName));
            }
        }
    }

    /**
     * 每天发送整天收集的数据汇总
     */
    public void executeSendPerDayData() throws BusinessException {
        Date currentDate = new Date();

        DateTime dateTime = DateTime.now().plusDays(-1);
        Date startDate = new DateTime(
                dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0).toDate();

        List<ServiceCity> allServiceCities = serviceCityService.getAllServiceCities();
        for (ServiceCity serviceCity : allServiceCities) {
            Integer cityId = serviceCity.getId();
            String cityName = serviceCity.getName();


            List<SpreadStudentDetailInfo> spreadStudents =
                    spreadStudentService.getSpreadStudentDetailsByCondition(cityId, startDate, currentDate);
            if (!spreadStudents.isEmpty()) {
                //send message
                List<PotentialStudentDisplayed> potentialStudentDisplayeds = new ArrayList<>();
                for (SpreadStudentDetailInfo spreadStudentDetailInfo : spreadStudents) {
                    potentialStudentDisplayeds.add(convertToPotentialStudent(spreadStudentDetailInfo));
                }

                MailConfiguration mailConfiguration = new MailConfiguration();
                String subject =
                        String.format("(全天数据)城市: %s 时间段(%2$tF %2$tT 至 %3$tF %3$tT) 的潜在学员报告", cityName, startDate,
                                currentDate);
                mailConfiguration.setSubject(subject);
                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{CommonConstants.CITY_TO_MAILBOX_MAP.get(cityId)}));
//                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"mazhiqiang@ejiapei.com"}));
//                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"gaochao@ejiapei.com"}));
                mailConfiguration.setTemplate("perday-student.ftl");
                mailConfiguration.setMailObject(potentialStudentDisplayeds);
                mailSender.sendTemplateMail(mailConfiguration);
                LOG.info("发送邮件成功，标题为: " + subject);
            } else {
                LOG.info(String.format("城市: %s 全天无潜在学员的数据，暂不发送邮件!", cityName));
            }
        }
    }

    private PotentialStudentDisplayed convertToPotentialStudent(SpreadStudentDetailInfo spreadStudentDetailInfo) {
        PotentialStudentDisplayed potentialStudentDisplayed = new PotentialStudentDisplayed();
        potentialStudentDisplayed.setCity(spreadStudentDetailInfo.getCityName());
        potentialStudentDisplayed.setName(spreadStudentDetailInfo.getStudentName());
        potentialStudentDisplayed.setPhone(spreadStudentDetailInfo.getStudentPhone());
        potentialStudentDisplayed.setReferrerName(StringUtils.isBlank(spreadStudentDetailInfo.getReferrerName()) ? "无" :
                spreadStudentDetailInfo.getReferrerName());
        potentialStudentDisplayed.setReferrerPhone(
                StringUtils.isBlank(spreadStudentDetailInfo.getReferrerPhone()) ? "无" :
                        spreadStudentDetailInfo.getReferrerPhone());
        potentialStudentDisplayed.setTimestamp(
                String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", spreadStudentDetailInfo.getCreatedAt()));
        return potentialStudentDisplayed;
    }

}
