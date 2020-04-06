package com.wfq.manager.service;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYMMDDHHMMSS = "yyMMddHHmmss";

    public final static long nd = 24 * 60 * 60;
    public final static long nh = 60 * 60;

    public static final String getYYMMDDHHMMSS() {
        return dateTimeNow(YYMMDDHHMMSS);
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    public static Long getTodayTimeStemp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Long getEndTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime().getTime() / 1000;
    }

    /**
     * 获取当天23时59分59秒
     */
    public static Long getTodayEndTimeStemp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static Long getYesterdayTimeStemp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 得到前一小时时间
     */
    public static Long getOneHourAgo(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * 计算两个时间差
     */
    public static String getTimeDeffiemce(Long timeEnd, Long timeStart) {

        long diff = timeEnd - timeStart;

        return getTimeDeffiemce(diff);
    }

    public static String getTimeDeffiemce(long sec) {

        long day = sec / nd;
        BigDecimal hour = new BigDecimal(sec % nd).divide(new BigDecimal(String.valueOf(nh)), 2, BigDecimal.ROUND_HALF_EVEN);

        if (day > 0) {
            return day + "天" + hour.toString() + "小时";
        }
        return hour.toString() + "小时";
    }

    public static String getHoursFromSec(long sec) {
        BigDecimal hour = new BigDecimal(sec).divide(new BigDecimal(String.valueOf(nh)), 1, BigDecimal.ROUND_HALF_EVEN);
        return hour.toString();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期增/减
     *
     * @param date   日期
     * @param field  年/月/日/时/分/秒
     * @param offset 增减的数
     */
    public static Date addOrSubField(Date date, int field, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(field, calendar.get(field) + offset);
        return calendar.getTime();
    }

    /**
     * 清楚日期的毫秒值
     *
     * @param date
     * @return
     */
    public static Date clearDateMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取对应时间增加/删减后的23:59:59的日期
     *
     * @param millis
     * @return
     */
    public static Date getSpecialDate(long millis, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.add(Calendar.DATE, offset);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取0点指定分钟的日期
     *
     * @param date
     * @param assignMinutes
     * @return
     */
    public static Date getDateBeginAssignMinutes(Date date, Integer assignMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, assignMinutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期所在月的天数
     * @param date
     * @return
     */
    public static int getDateActualMaximum(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取月份的最后一天
     * @param date
     * @return
     */
    public static String getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        return parseDateToStr(YYYY_MM_DD, calendar.getTime());
    }
}
