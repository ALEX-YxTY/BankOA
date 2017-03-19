package com.meishipintu.library.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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

}
