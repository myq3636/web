package com.jx.tennis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }
    
    public static String getCurrentMonth()
    {
        return dateTimeNow(YYYY_MM);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }
    
    public static String getNextMonthString(String strdate, String timezone) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        Calendar nextTime = Calendar
                .getInstance(TimeZone.getTimeZone(timezone));
        nextTime.set(Calendar.YEAR, Integer.parseInt(strdate.substring(0, 4)));
        nextTime.set(Calendar.MONTH,
                Integer.parseInt(strdate.substring(5, 7)) - 1);
        nextTime.add(Calendar.MONTH, 1);

        return formatter.format(nextTime.getTime());
    }
    
    public static String getPreMonthString(String strdate, String timezone) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        Calendar nextTime = Calendar
                .getInstance(TimeZone.getTimeZone(timezone));
        nextTime.set(Calendar.YEAR, Integer.parseInt(strdate.substring(0, 4)));
        nextTime.set(Calendar.MONTH,
                Integer.parseInt(strdate.substring(5, 7)) - 1);
        nextTime.add(Calendar.MONTH, -1);

        return formatter.format(nextTime.getTime());
    }
    
    public static String getFormatTimeFromMsWithTimeZone(long timems,
            String timeFormat, String timeZone) {
        Date d = new Date(timems);
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(d);
    }
    
    public static long getMsFromFormatTimeWithTimeZone(String formatTime,
            String timeFormat, String timeZone) {  
    	long mstime = 0L;
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
            sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
            Date date = null;            
            date = sdf.parse(formatTime);
            mstime = date.getTime();
        } catch (ParseException e) {
        }

        return mstime;
    }

}
