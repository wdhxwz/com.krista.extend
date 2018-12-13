package com.krista.extend.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * DateTimeUtil
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/13 14:44
 */
public class DateTimeUtil {

    /**
     * 支持的时间格式
     */
    private static final String[] PARSE_PATTERNS = new String[]{
            "yyyy", "yyyyMM", "yyyy-MM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMddHHmmss",
            "yyyyMMdd HHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyww"
    };

    /**
     * 1天的毫秒数
     */
    private static final long ONE_DAY = 24 * 3600 * 1000;

    /**
     * 获取系统默认时间
     *
     * @return 1970-01-01 00:00:00
     */
    public static Date getDefaultTime() {
        return new Date(-28800000L);
    }

    /**
     * 解析日期时间
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        try {
            return DateUtils.parseDateStrictly(dateStr, PARSE_PATTERNS);
        } catch (ParseException e) {
            throw new RuntimeException("解析日期出错:" + dateStr + e.getMessage());
        }
    }

    /**
     * 格式化字符日期时间
     *
     * @param date   - 日期
     * @param format - 格式
     * @return - String 返回字符型日期
     */
    public static String getDateTime(Date date, String format) {
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        }
        return null;
    }

    /**
     * 取指定时间所在月份的第一天
     *
     * @param date - 指定时间
     * @return String - 第一天时间
     */
    public static String getMonthBegin(Date date) {
        if (date != null) {
            return getDateTime(date, "yyyy-MM") + "-01";
        }
        return null;
    }

    /**
     * 取指定时间所在月份的最后一天
     *
     * @param date - 指定时间
     * @return String - 最后一天时间
     */
    public static String getMonthEnd(Date date) {
        if (date == null) {
            return null;
        }
        String monthBegin = getMonthBegin(date);
        Date tmp = parseDate(monthBegin);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tmp);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return getDateTime(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 取给定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getWeekBegin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    /**
     * 取给定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getWeekEnd(Date date) {
        if (date == null) {
            return null;
        }
        Date weekBegin = getWeekBegin(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekBegin);
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        return calendar.getTime();
    }

    /**
     * 取日期所在周
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 取日期所在周
     *
     * @param date
     * @return
     */
    public static int getWeek(String date) {
        if (date == null) {
            return 0;
        }
        return getWeek(parseDate(date));
    }

    /**
     * 取日期所在周
     *
     * @param date
     * @return - yyyy-ww
     */
    public static String parseWeek(Date date) {
        if (date == null) {
            return null;
        }
        int week = getWeek(date);
        return getDateTime(date, "yyyy") + "-" + (week < 10 ? ("0" + week) : week);
    }

    /**
     * 取日期所在周
     *
     * @param dateStr
     * @return - yyyy-ww
     */
    public static String parseWeek(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        int week = getWeek(dateStr);
        return dateStr.substring(0, 5) + (week < 10 ? ("0" + week) : week);
    }

    public static String addWeek(String weekStr, int n) {
        if (weekStr == null) {
            return null;
        }
        if (n == 0) {
            return weekStr;
        }
        //先获取给定周对应的周一2017-03
        String[] info = weekStr.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR, NumberUtils.toInt(info[0]));
        calendar.set(Calendar.WEEK_OF_YEAR, NumberUtils.toInt(info[1]));
        calendar.add(Calendar.WEEK_OF_YEAR, n);
        return parseWeek(calendar.getTime());
    }

    /**
     * 格式化日期
     *
     * @param pattern - 格式
     * @param date    - 日期
     * @return
     */
    public static String format(String pattern, Date date) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 计算N天前/后的时间
     *
     * @param date - 日期
     * @param day  - 天数
     * @return - {@link Date}
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() + day * ONE_DAY);
        return calendar.getTime();
    }

    /**
     * 计算N天前/后的时间
     *
     * @param date - 日期
     * @param day  - 天数
     * @return - {@link Date}
     */
    public static String addDate(String formatStr, String date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(parseDate(date).getTime() + day * ONE_DAY);
        return format(formatStr, calendar.getTime());
    }

    /**
     * 计算N天前/后的时间
     *
     * @param date   - 日期
     * @param second -
     * @return - {@link Date}
     */
    public static Date addSecond(Date date, long second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() + second * 1000);
        return calendar.getTime();
    }

    /**
     * 计算两日期之间的天数
     *
     * @param start - 起始时间
     * @param end   - 结束时间
     * @return - int 相差天数
     */
    public static int getDayCount(Date start, Date end) {
        long s = start.getTime() / ONE_DAY;
        long e = end.getTime() / ONE_DAY;
        return (int) (s - e);
    }

    /**
     * 返回给定时间+n个月后的月份(n可为负数)
     *
     * @param date - 给定时间
     * @param n    - 月份变动量
     * @return - 变动后的月份
     */
    public static String addMonth(Date date, int n, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        return format(format, calendar.getTime());
    }

    /**
     * 返回给定时间+n个月后的月份(n可为负数)
     *
     * @param dateStr - 给定时间
     * @param n       - 月份变动量
     * @return - 变动后的月份
     */
    public static String addMonth(String dateStr, int n, String format) {
        return addMonth(parseDate(dateStr), n, format);
    }

    /**
     * 计算两个时间段内的日期列表
     *
     * @param start - 开始日期
     * @param end   - 结束日期
     * @return - 时间段内的日期列表
     */
    public static List<String> listDate(Date start, Date end) {
        if (start.after(end)) {
            throw new RuntimeException("开始时间不能开结束时间之后");
        }
        List<String> list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        do {
            list.add(format("yyyy-MM-dd", calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        } while (!end.before(calendar.getTime()));
        return list;
    }

    /**
     * 计算两个时间段内的日期列表
     *
     * @param start - 开始日期
     * @param end   - 结束日期
     * @return - 时间段内的日期列表
     */
    public static List<String> listDate(String start, String end) {
        Date startDate = parseDate(start);
        Date endDate = parseDate(end);
        return listDate(startDate, endDate);
    }

    /**
     * 计算两个时间段内的周列表
     *
     * @param start - 开始
     * @param end   - 结束
     * @return - 时间段内的周列表
     */
    public static List<String> listWeek(String start, String end) {
        Date startDate = parseDate(start);
        Date endDate = parseDate(end);
        if (startDate.after(endDate)) {
            throw new RuntimeException("开始时间不能开结束时间之后");
        }
        List<String> list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        String tmpWeek = "";
        do {
            tmpWeek = parseWeek(calendar.getTime());
            list.add(tmpWeek);
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        } while (!tmpWeek.equalsIgnoreCase(end));
        return list;
    }

    /**
     * 计算两个时间段内的月列表
     *
     * @param start - 开始
     * @param end   - 结束
     * @return - 时间段内的月列表
     */
    public static List<String> listMonth(String start, String end) {
        Date startDate = parseDate(start);
        Date endDate = parseDate(end);
        if (startDate.after(endDate)) {
            throw new RuntimeException("开始时间不能开结束时间之后");
        }
        List<String> list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        do {
            list.add(format("yyyy-MM", calendar.getTime()));
            calendar.add(Calendar.MONTH, 1);
        } while (!endDate.before(calendar.getTime()));
        return list;
    }

    /***
     * 将时间(秒)转换为时分秒格式返回
     * @param time - 时间(秒)
     * @return - 时分秒格式(例： 23:31:34)
     */
    public static String getTimeForm(long time) {
        String hour = time / (60 * 60) + "";
        String minue = (time % (60 * 60)) / 60 + "";
        String second = time % (60) + "";
        hour = hour.length() == 1 ? "0" + hour : hour;
        minue = minue.length() == 1 ? "0" + minue : minue;
        second = second.length() == 1 ? "0" + second : second;
        return hour + ":" + minue + ":" + second;
    }

    /**
     * 计算两个日期之前的时间差
     *
     * @param start - 开始时间
     * @param end   - 结束时间
     * @return - 时间差(单位:秒)
     */
    public static long countDelta(Date start, Date end) {
        long time = start.getTime() - end.getTime();
        return time / 1000;
    }
}
