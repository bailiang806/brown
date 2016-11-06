package com.happydriving.rockets.server.component.expirable;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * 可过期value的抽象类
 *
 * Created by jasonzhu on 21/7/15.
 */
public abstract class Expirable<T> {
    protected T value;              //当前值
    protected long createTime;      //当前value生成的开始时间戳, 精确到秒
    protected long survivalSpan;    //当前value的生存时间跨度，单位:秒

    /**
     * 获取有效的value
     * @return
     */
    public abstract T getValue() throws IOException;

    /**
     * 跟当前服务器时间相比，如果(createTime+survivalTime < curTime), 返回true；否则，返回false
     * @param deviation 允许的误差时间，单位：秒
     * @return
     */
    protected boolean deadAlready(long deviation)  {
        if(value == null) return true;

        Date lastBreath = new Date(new Timestamp((createTime+survivalSpan-deviation)*1000L).getTime());
        if(lastBreath.compareTo(new Date()) >= 0) return false;
        return true;
    }

    protected boolean deadAlready() {
        return deadAlready(200);
    }



}
