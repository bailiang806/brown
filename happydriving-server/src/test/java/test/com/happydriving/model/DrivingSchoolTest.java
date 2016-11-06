//package test.com.happydriving.model;
//
//import com.happydriving.rockets.server.common.BusinessException;
//import com.happydriving.rockets.server.entity.DrivingSchool;
//import com.happydriving.rockets.server.service.DrivingSchoolService;
//import org.aspectj.lang.annotation.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//
///**
// * @author mazhiqiang
// */
//public class DrivingSchoolTest {
//
//    private static DrivingSchoolService drivingSchoolService;
//
//    @BeforeClass
//    public static void init() {
//        ApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/config/spring.xml");
//        drivingSchoolService = context.getBean(DrivingSchoolService.class);
//    }
//
//    @Test
//    public void testInitDrivingSchool() throws BusinessException {
//        drivingSchoolService.addDrivingSchool("厦门", "露名泉驾校", "湖里，马垅公交站总站旁");
//        drivingSchoolService.addDrivingSchool("厦门", "双寅驾校", "厦门市湖里区湖里大道");
//        drivingSchoolService.addDrivingSchool("厦门", "通凯驾校", "厦门市集美区杏林杏前路114号之三");
//        drivingSchoolService.addDrivingSchool("厦门", "鑫良友驾校", "厦门市同安区阳翟路");
//        drivingSchoolService.addDrivingSchool("厦门", "鑫双榕驾校", "厦门市同安区外较场路");
//        drivingSchoolService.addDrivingSchool("厦门", "阳齐驾校", "厦门市湖里区江头北路148号（天地花园公交车站旁）");
//    }
//
//    public static void main(String[] args) {
//
//    }
//}
