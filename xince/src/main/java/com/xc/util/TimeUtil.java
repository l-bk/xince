package com.xc.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
    计算日期差
        newDate 结束时间
        oldDate 开始时间

     */
    public static int differentDaysByMillisecond(Date newdDate,Date oldDate ){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(newdDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(oldDate);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);//获取这一天的年总日期
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int day= day1 -day2;
        return  day+1;//包含最后一天
    }

    /*
        获取当前日期是星期几
     */
    public static int dayForWeek(Date date) throws Exception {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }
}
