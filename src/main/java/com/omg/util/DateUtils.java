package com.omg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	 /**
     * 判断字符串是否为正整数
     * @param code
     * @return 若是则返回true，否则返回false
     * @author Jesse Lu
     */
    public static boolean isUInteger(String code) {
        if (null == code) return false;
        return code.matches("^\\d+");
    }

    /**
     * 判断字符串是否为整数
     * @param code
     * @return 若是则返回true，否则返回false
     * @author Jesse Lu
     */
    public static boolean isInteger(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+");
    }

    /**
     * 判断字符串是否为正浮点数
     * @param code
     * @return 若是则返回true，否则返回false
     * @author Jesse Lu
     */
    public static boolean isUFloat(String code) {
        if (null == code) return false;
        return code.matches("^\\d+(\\.\\d+)?");
    }

    /**
     * 判断字符串是否为浮点数
     * @param code
     * @return 若是则返回true，否则返回false
     * @author Jesse Lu
     */
    public static boolean isFloat(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+(\\.\\d+)?");
    }

    /**
     * 将长日期字符串转换为Date
     * @param strValue
     * @return java.util.Date
     */
    public static java.util.Date str2date(String strValue) {
        if (strValue == null) return null;
        if (strValue.equals("")) return null;
        java.util.Date theDate;
        try {
            String str = strValue.substring(4, 5);
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd" + " " + "HH" + ":" + "mm" + ":" + "ss");
            theDate = theFormat.parse(strValue);
        } catch (Exception ex) {
            theDate = null;
        }
        return theDate;
    }
    /**
     * 将指定字符串转换为double基础类型 若转换出错或参数为null，则返回0.0D
     * @param strValue
     * @return
     */
    public static double str2double(String strValue) {
        if (strValue == null) return 0.0D;
        double nValue = 0.0D;
        try {
            String temp = strValue.trim();
            nValue = Double.parseDouble(temp);
        } catch (NumberFormatException numberformatexception) {
            return 0.0D;
        }
        return nValue;
    }
    /**
     * Double过滤方法
     * @param dou
     * @return 若dou为null，则返回0.0D
     * @author Zhuang jie
     */
    public static Double douNull(Double dou) {
        if (dou == null) {
            return 0.0D;
        } else {
            return dou;
        }
    }

    /**
     * 字符串过滤方法
     * @param str
     * @return 若str为null，则返回""
     */
    public static String strNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 将相关的长日期格式字符串转换日期对象 若字符串格式错误，则转换为参数中提供的默认日期
     * @param strValue 长日期格式字符串 "yyyy-MM-dd HH:mm:ss"
     * @param theDefault 默认日期
     * @return
     */
    public static java.util.Date str2date(String strValue, java.util.Date theDefault) {
        if (strValue == null) return theDefault;
        SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date theDate;
        try {
            theDate = theFormat.parse(strValue);
        } catch (ParseException ex) {
            theDate = theDefault;
        }
        return theDate;
    }

    /**
     * 将日期转换为长日期字符串格式
     * @param aDate
     * @return
     */
    public static String date2str(java.util.Date aDate) {
        if (aDate == null) {
            return "";
        } else {
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return theFormat.format(aDate);
        }
    }

    /**
     * 将日期转换为短日期字符串格式
     * @param date
     * @return
     */
    public static String date2shortstr(java.util.Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd");
            return theFormat.format(date);
        }
    }

    /**
     * 将短日期格式字符串转换为Date
     * @param strValue
     * @return
     */
    public static java.util.Date shortstr2date(String strValue) {
        if (strValue == null) return null;
        if (strValue.equals("")) return null;
        java.util.Date theDate;
        try {
            String str = strValue.substring(4, 5);
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd");
            theDate = theFormat.parse(strValue);
        } catch (Exception ex) {
            theDate = null;
        }
        return theDate;
    }

    /**
     * 日期快捷格式化方法
     * @param date
     * @param parse 格式化格式
     * @return
     * @author Jesse Lu
     */
    public static String parseDate(java.util.Date date, String parse) {
        if (date == null || parse == null) return "";
        SimpleDateFormat theFormat = new SimpleDateFormat(parse);
        return theFormat.format(date);
    }
    /**
     * 计算给出的日期相关sum天的日期
     * @param date 指定日期
     * @param sum 相差天数，负数就是前几天
     * @return 相差sum天的日期
     * @author Jesse Lu
     */
    public static Date DayDifference(Date date, int sum) {
        return new Date(date.getTime() + (sum * 86400000l));
    }
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 相差天数，若返回0，则表示参数错误或是同一天
     * @author Jesse Lu
     */
    public static int DayDifference(Date date1, Date date2) {
        if (null == date1 || null == date2) return 0;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return DayDifference(c1, c2);
    }
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param cal1 第一个日期
     * @param cal2 第二个日期
     * @return 相差天数，若返回0，则表示参数错误或是同一天
     * @author Jesse Lu
     */
    public static int DayDifference(Calendar cal1, Calendar cal2) {
        if (null == cal1 || null == cal2) return 0;
        Calendar c = Calendar.getInstance();
        c.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
        long millis1 = c.getTimeInMillis();
        c.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
        long millis2 = c.getTimeInMillis();
        return Long.valueOf(Math.abs(millis1 - millis2) / 86400000).intValue();
    }
    /**
     * 判定字符串是否有值;
     * @param str - 待判定的字符串;
     * @param trim - 是否要去前后的空白;
     * @return
     */
    public static boolean isNullOrEmpty(String str, boolean trim) {
        if (str == null) return true;
        if (str.isEmpty() || (trim && str.trim().isEmpty())) return true;
        return false;
    }
    public static Calendar dateToCal(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    public static Date calTodate(Calendar cal) {
        Date date = cal.getTime();
        return date;
    } 
    
    
    /**
     * 指定日期加上天数后的日期
     * @param num 为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException
     */
    public static Date plusDay(int num, Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        return ca.getTime();
    }
    
    /**
     * 指定日期加上月数后的日期
     * @param num 为增加的月数
     * @param newDate 创建时间
     * @return
     * @throws ParseException
     */
    public static Date plusMonth(int num, Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, num);// num为增加的天数，可以改变的
        return ca.getTime();
    }
    
    
    public static String getLastTimeInterval() {
		 SimpleDateFormat sdf = new SimpleDateFormat("");
	     Calendar calendar1 = Calendar.getInstance();
	     Calendar calendar2 = Calendar.getInstance();
	     int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
	     int offset1 = 1 - dayOfWeek;  
	     int offset2 = 7 - dayOfWeek;  
	     calendar1.add(Calendar.DATE, offset1 - 7);
	     calendar2.add(Calendar.DATE, offset2 - 7);
	     // System.out.println(sdf.format(calendar1.getTime()));// last Monday  
	     String lastBeginDate = date2shortstr(calendar1.getTime());
	     // System.out.println(sdf.format(calendar2.getTime()));// last Sunday  
	     String lastEndDate = date2shortstr(calendar2.getTime());
	     return lastBeginDate + "," + lastEndDate;  
	}

    public static String getDateCn(Date date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
