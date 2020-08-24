package com.cslg.properties;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class RoomOrderProperties {
    private static String one="9:00->12:00";
    private static String two="13:00->15:00";
    private static String three="15:30->17:30";
    private static String four="19:30->22:00";

    /**
     * 会议室时间段的开始结束时间的毫秒时间
     */
    public static Long start_one= 1000*60*60*9L;
    public static Long start_two= 1000*60*60*13L;
    public static Long start_three= 1000*60*6*155L;
    public static Long start_four= 1000*60*6*195L;

    public static Long end_one= 1000*60*60*12L;
    public static Long end_two= 1000*60*60*15L;
    public static Long end_three= 1000*60*6*175L;
    public static Long end_four= 1000*60*60*22L;
    private static Date day;
    /**
     * 指定时间段，获取时间描述信息
     * @param num
     * @return
     */
    public static String getTimeDescription(Date day,Integer num){
        if(num==1){
            return getOneTime(day);
        }else if(num==2){
            return getTwoTime(day);
        }else if(num==3){
            return getThreeTime(day);
        }else if(num==4){
            return getFourTime(day);
        }else {
            return null;
        }
    }/**
     * 9:00->12:00 时间段
     * @return
     */
    public static String getOneTime(Date date){
        return dateToString(date)+" "+one;
    }
    /**
     * 13:00->15:00 时间段
     * @return
     */
    public static String getTwoTime(Date date){
        return dateToString(date)+" "+two;
    }
    /**
     * 15:30->17:30 时间段
     * @return
     */
    public static String getThreeTime(Date date){
        return dateToString(date)+" "+three;
    }

    /**
     * 19:30->22:00 时间段
     * @return
     */
    public static String getFourTime(Date date){
        return dateToString(date)+" "+four;
    }

    /**
     * 获取时间，精确到天
     */
    public static Date getDay(Date day){
        String format = dateToString(day);
        day = DateUtil.parse(format, "yyyy-MM-dd");
        return day;
    }

    public static String dateToString(Date date){
        String format = DateUtil.format(date, "yyyy-MM-dd");
        return format;
    }

    public static Long getMills(Date date,Integer timeSlot,Boolean isStart){
        if(timeSlot==1){
            if(isStart){
                return date.getTime()+start_one;
            }else {
                return date.getTime()+end_one;
            }
        }
        if(timeSlot==2){
            if(isStart){
                return date.getTime()+start_two;
            }else {
                return date.getTime()+end_two;
            }
        }
        if(timeSlot==3){
            if(isStart){
                return date.getTime()+start_three;
            }else {
                return date.getTime()+end_three;
            }
        }
        if(timeSlot==4){
            if(isStart){
                return date.getTime()+start_four;
            }else {
                return date.getTime()+end_four;
            }
        }
        return null;
    }
}
