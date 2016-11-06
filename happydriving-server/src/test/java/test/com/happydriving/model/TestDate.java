package test.com.happydriving.model;

import org.joda.time.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mazhiqiang
 */
public class TestDate {

    public static void main(String[] args) {
//        System.out.println(String.format("%1$tY%1$tm%1$td%1$tH", new Date()));
//
//
//        System.out.println(String.format("%tF", new Date()));
//        System.out.println(String.format("%tR", new Date()));
//        System.out.println(Integer.parseInt(String.format("%tH", new Date()))-
//                Integer.parseInt(String.format("%tH", new Date())));
//
//
//        System.out.println("Start to test joda time---------");
//
//        DateTime start1 = new DateTime(2015, 6, 30, 10, 0, 0);
//        DateTime end1 = new DateTime(2015, 6, 30, 12, 0, 0);
//        Interval interval = new Interval(start1, end1);
//
//        DateTime currentDate1 = new DateTime(2015, 6, 30, 9, 0, 0);
//        DateTime currentDate2 = new DateTime(2015, 6, 30, 13, 0, 0);
//        Interval interval2 = new Interval(currentDate1, currentDate2);
//
//
//        System.out.println(interval.overlaps(interval2));
//
//        float aaa = BigDecimal.valueOf(12.35).floatValue();
//        System.out.println(aaa * 100);
//
//        BigDecimal bigDecimal = new BigDecimal(12.35, new MathContext(5, RoundingMode.HALF_UP));
//        BigDecimal multiply = bigDecimal.multiply(BigDecimal.valueOf(100));
//        System.out.println(multiply.setScale(0, BigDecimal.ROUND_HALF_UP));

//        Date date = new Date();
//        Calendar instance = Calendar.getInstance();
//
//        int minuteOfHour = instance.get(Calendar.MINUTE);
//        DateTime dateTime = new DateTime(
//                instance.get(Calendar.YEAR),
//                instance.get(Calendar.MONTH),
//                instance.get(Calendar.DAY_OF_MONTH),
//                minuteOfHour >= 30 ? instance.get(Calendar.HOUR) : instance.get(Calendar.HOUR) - 1,
//                minuteOfHour >= 30 ? 0 : 30);
//
//        System.out.println(dateTime.plusMinutes(-30).toString("yyyyMMdd hh:mm:ss"));


//        DateTime dateTime = DateTime.now();
//        System.out.println(dateTime.getMinuteOfHour());
//        System.out.println(dateTime.getHourOfDay());

        Calendar instance = Calendar.getInstance();
        System.out.println(instance.get(Calendar.YEAR));
        System.out.println(instance.get(Calendar.MONTH) + 1);
        System.out.println(instance.get(Calendar.DAY_OF_MONTH));

        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);

        System.out.println(instance.getTime());

        new java.sql.Date(instance.getTime().getTime());
    }
}
