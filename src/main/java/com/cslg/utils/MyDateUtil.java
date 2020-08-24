package com.cslg.utils;

import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {
    public static Date getAfterDateByNum(int n){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DATE, n);
        String day = sdf2.format(calendar.getTime());
        return DateUtil.parse(day,"yyyy-MM-dd");
    }
}
