package com.happydriving.rockets.server.common;

import java.util.Date;

/**
 * @author mazhiqiang
 */
public class CommonUtils {

    /**
     * 将Date类型数据转换成String类型 2015-01-01 12:00:00 的格式
     * @param date
     * @return
     */
    public static String transferDateToString(Date date) {
        return date != null ?
                String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", date)
                : "";
    }

}
