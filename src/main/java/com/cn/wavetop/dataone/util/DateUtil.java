package com.cn.wavetop.dataone.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public static String dateAdd(String dateStr,int num){
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateMaxDate  = format.parse(dateStr);
            Calendar calendar  =  new GregorianCalendar();
            calendar.setTime(dateMaxDate);
            calendar.add(calendar.DATE,num);//把日期往后增加一天.整数往后推,负数往前移动
            dateStr=format.format(calendar.getTime());   //这个时间就是日期往后推一天的结果
        } catch ( ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

}
