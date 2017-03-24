package com.meishipintu.library.util;

import android.provider.SyncStateContract;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.ConsoleHandler;

/**
 * Created by Administrator on 2017/3/17.
 * <p>
 * 主要功能：日期处理工具类
 */

public class DateUtil {

    //显示剩余时间
    public static String showTimeRemain(String timeLong) {
        long now = System.currentTimeMillis() / 1000;
        long time = Long.parseLong(timeLong);
        long minutes = (Math.abs(now - time) / 60) % 60;
        long hour = Math.abs(now - time) / 3600;
        if (hour >= 24) {
            return String.format("%d天 %d小时 %d分钟", hour / 24, hour % 24, minutes);
        } else {
            return hour + "小时 "+minutes+"分钟";
        }
    }

    //显示时间格式为 MM-dd hh:mm
    public static String getTimeFormart2(String remark_time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd hh:mm");

        return simpleDateFormat.format(new Date(Long.parseLong(remark_time)*1000));
    }

    //显示时间格式为yyy年MM月dd日
    public static String formart2(String timeStamp) {
        if (timeStamp == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String format = simpleDateFormat.format(new Date(Long.parseLong(timeStamp) * 1000));
        return format;
    }

    //将格式为 yyyy年MM月dd日字串转化为时间戳
    public static String deformar2(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(time);
            parse.setHours(12);
            return (parse.getTime()) / 1000 + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //显示时间格式为yyyy-MM-dd mm:ss
    public static String formart3 (String comment_time) {
        if (comment_time == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd mm:ss");
        String format = simpleDateFormat.format(new Date(Long.parseLong(comment_time) * 1000));
        return format;
    }
}
