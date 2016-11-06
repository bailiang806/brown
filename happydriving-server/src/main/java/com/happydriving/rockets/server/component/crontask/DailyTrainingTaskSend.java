package com.happydriving.rockets.server.component.crontask;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.component.message.MailConfiguration;
import com.happydriving.rockets.server.component.message.MailSender;
import com.happydriving.rockets.server.component.tools.JdbcTemplateComponent;
import com.happydriving.rockets.server.entity.DsTrainingSchedule;
import com.happydriving.rockets.server.entity.ServiceCity;
import com.happydriving.rockets.server.mapper.DsStudentReservationMapper;
import com.happydriving.rockets.server.service.ServiceCityService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by gaoc10 on 2015/10/15 0015.
 */
public class DailyTrainingTaskSend {
    private static final Log LOG = LogFactory.getLog(DailyTrainingTaskSend.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    DsStudentReservationMapper dsStudentReservationMapper;

    @Autowired
    ServiceCityService serviceCityService;

    @Autowired
    JdbcTemplateComponent jdbcTemplateComponent;

    public static final String Coach_SQL = "select distinct coach.name as coachName," +
            "coach.id as coachId from ds_student_reservation " +
            "inner join ds_training_schedule on ds_student_reservation.course_id=ds_training_schedule.id " +
            "inner join coach on coach.id=ds_training_schedule.coach_id " +
            "where ds_training_schedule.training_date='%s' " +
            "and coach.city_id=%d";
    public static final String CourseIdByCoachId_SQL = "select distinct ds_student_reservation.course_id as courseId " +
            "from ds_student_reservation " +
            "inner join ds_training_schedule on ds_training_schedule.id=ds_student_reservation.course_id " +
            "where ds_training_schedule.coach_id='%s' " +
            "and ds_training_schedule.training_date='%s' ";
    public static final String CourseDetailByCourseId_SQL = "select ds_training_schedule.training_date as trainingDate," +
            "enumeration1.description as trainingCourse, "+
            "enumeration2.description as trainingTime, " +
            "ds_training_site.name as trainingSite " +
            "from ds_training_schedule " +
            "inner join enumeration enumeration1 on enumeration1.enum_id=ds_training_schedule.training_course "+
            "inner join enumeration enumeration2 on ds_training_schedule.training_time=enumeration2.enum_id "+
            "inner join ds_training_site on ds_training_schedule.training_site_id=ds_training_site.id "+
            "where ds_training_schedule.id='%s'";
    public static final String StudentInfoByCourseId_SQL = "select ds_signup_student.name as studentName," +
            "ds_signup_student.phone as studentPhone " +
            "from ds_student_reservation " +
            "inner join ds_signup_student on ds_signup_student.id=ds_student_reservation.student_id " +
            "where ds_student_reservation.course_id='%s'";

    public void sendTomorrowTrainingTask() throws BusinessException {
        DateTime dateTime = new DateTime();
        String tomorrow = dateTime.plusDays(0).toString("yyyy-MM-dd");
        List<ServiceCity> serviceCities=serviceCityService.getAllServiceCities();
        for(ServiceCity city:serviceCities){
            int cityId=city.getId();
            String cityName=city.getName();
            List<CoachAndTrainingTasks> list = new ArrayList<>();
            List<Map<String, Object>> coachSqlList = jdbcTemplateComponent.queryForList(
                    String.format(Coach_SQL,tomorrow,cityId));
            if(!coachSqlList.isEmpty()) {
                for (Map<String, Object> coachSql : coachSqlList) {
                    CoachAndTrainingTasks coachAndTrainingTasks = new CoachAndTrainingTasks();
                    String coachName = (String) coachSql.get("coachName");
                    coachAndTrainingTasks.setCoachName(coachName);
                    String coachId = String.valueOf((Integer) coachSql.get("coachId"));
                    List<Map<String, Object>> courseIdSqlList = jdbcTemplateComponent.queryForList(
                            String.format(CourseIdByCoachId_SQL, coachId, tomorrow));
                    List<TrainingTask> trainingTasks = new ArrayList<>();
                    for (Map<String, Object> courseIdSql : courseIdSqlList) {
                        String courseId = (String) courseIdSql.get("courseId");
                        Map<String, Object> courseDetail = jdbcTemplateComponent.queryForMap(String.format(CourseDetailByCourseId_SQL, courseId));
                        TrainingTask trainingTask = new TrainingTask();
                        trainingTask.setTrainingCourse((String) courseDetail.get("trainingCourse"));
                        trainingTask.setTrainingDate((Date) courseDetail.get("trainingDate"));
                        trainingTask.setTrainingTime((String) courseDetail.get("trainingTime"));
                        trainingTask.setTrainingSite((String) courseDetail.get("trainingSite"));
                        List<StudentInfo> studentInfos = new ArrayList<>();
                        List<Map<String, Object>> studentInfoSqlList = jdbcTemplateComponent.queryForList(
                                String.format(StudentInfoByCourseId_SQL, courseId));
                        for (Map<String, Object> studentInfoSql : studentInfoSqlList) {
                            StudentInfo studentInfo = new StudentInfo();
                            studentInfo.setName((String) studentInfoSql.get("studentName"));
                            studentInfo.setPhone((String) studentInfoSql.get("studentPhone"));
                            studentInfos.add(studentInfo);
                        }
                        trainingTask.setStudentInfos(studentInfos);
                        trainingTasks.add(trainingTask);
                    }
                    coachAndTrainingTasks.setTrainingTasks(trainingTasks);
                    list.add(coachAndTrainingTasks);
                }
                MailConfiguration mailConfiguration = new MailConfiguration();
                String subject = String.format("%s%s的训练任务", cityName, tomorrow);
                mailConfiguration.setSubject(subject);
                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{CommonConstants.CITY_TO_MAILBOX_MAP.get(cityId), "zhangshuyu@ejiapei.com"}));
//                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"mazhiqiang@ejiapei.com"}));
//                mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"gaochao@ejiapei.com"}));
                mailConfiguration.setTemplate("perday-trainingtask.ftl");
                mailConfiguration.setMailObject(list);
                try {
                    mailSender.sendTemplateMail(mailConfiguration);
                    LOG.info(String.format("发送邮件成功，标题为：" + subject));
                } catch (BusinessException e) {
                    LOG.error(e);
                }
            }
            else LOG.info(String.format("城市：%s 今天无训练任务，暂不发送邮件！",cityName));
            }


    }

    public static class CoachAndTrainingTasks {
        private String coachName;
        private List<TrainingTask> trainingTasks;

        public String getCoachName() {
            return coachName;
        }

        public void setCoachName(String coachName) {
            this.coachName = coachName;
        }

        public List<TrainingTask> getTrainingTasks() {
            return trainingTasks;
        }

        public void setTrainingTasks(List<TrainingTask> trainingTasks) {
            this.trainingTasks = trainingTasks;
        }
    }

    public static class TrainingTask {
        private Date trainingDate;
        private String trainingCourse;
        private String trainingTime;
        private String trainingSite;
        private List<StudentInfo> studentInfos;

        public Date getTrainingDate() {
            return trainingDate;
        }

        public void setTrainingDate(Date trainingDate) {
            this.trainingDate = trainingDate;
        }

        public String getTrainingCourse() {
            return trainingCourse;
        }

        public void setTrainingCourse(String trainingCourse) {
            this.trainingCourse = trainingCourse;
        }

        public String getTrainingTime() {
            return trainingTime;
        }

        public void setTrainingTime(String trainingTime) {
            this.trainingTime = trainingTime;
        }

        public String getTrainingSite() {
            return trainingSite;
        }

        public void setTrainingSite(String trainingSite) {
            this.trainingSite = trainingSite;
        }

        public List<StudentInfo> getStudentInfos() {
            return studentInfos;
        }

        public void setStudentInfos(List<StudentInfo> studentInfos) {
            this.studentInfos = studentInfos;
        }
    }

    public static class StudentInfo {
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}


