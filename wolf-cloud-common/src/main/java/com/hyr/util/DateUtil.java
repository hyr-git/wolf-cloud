package com.hyr.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc：时间工具类
 * @CreateName： Major
 * @CreateTime：2020/3/24 14:52
 */

public class DateUtil {
    public static final String DATE_TIME = "yyyy-mm-dd HH:mm:ss";


    /**
     * @desc： 获取当前时间 包含时分秒
     * @CreateTime：2020/3/24 14:57
     */
    public static String getDateTimeInstanceStr(Date date) {
        return getSimpleDateFormatStr(date, DATE_TIME);
    }

    /**
     * @desc： 获取当前时间
     * @CreateTime：2020/3/24 14:57
     */
    public static String getDateInstanceStr(Date date) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(new Date());
    }

    /**
     * @desc： 自定一数据格式
     * @CreateTime：2020/3/24 14:57
     */
    public static String getSimpleDateFormatStr(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}
