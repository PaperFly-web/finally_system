package com.cslg.utils;

import cn.hutool.core.date.DateTime;

import java.util.Date;

public class MyTimeUtil {
    public static Long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    public static Date millsecondToDate(Long millis){
        DateTime of = DateTime.of(millis);
        return of;
    }

    public static Long getMillsecond(Date date){
        long time = date.getTime();
        return time;
    }
}
