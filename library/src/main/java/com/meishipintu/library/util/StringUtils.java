package com.meishipintu.library.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s.trim().replace(" ", ""));
    }

    public static boolean isNullOrEmpty(String[] strings) {
        for(int i=0;i<strings.length;i++) {
            if (isNullOrEmpty(strings[i])) {
                return true;
            }
        }
        return false;
    }

    public static String NullToEmpty(String s) {
        return s == null ? "无" : s;
    }

    //反相序每四位添加空格，适用于电话号码等
    public static String stringWithSpaceReverse(String s) {
        if (s == null) {
            return "";
        }
        s = s.replace(" ", "");
        if (s.length() <= 3) {
            return s;
        }
        int length = s.length();
        StringBuffer result = new StringBuffer();

        int index = 0;      //标记空格数，第一个空格第三位，后面每4位一个空格
        int start = 0;      //标记每次开始位数
        for(int i=1;i<length;i++) {
            if (i % (3 + 4 * index) == 0 ) {
                result.append(s.substring(start, i));
                result.append(" ");
                index++;
                start = i;
            }
        }
        result.append(s.substring(start));
        return result.toString();
    }

    //正向每四位加空格，适用于卡券号等
    public static String stringWithSpace(String s) {
        if (s == null) {
            return "";
        }
        s = s.replace(" ", "");
        if (s.length() < 4) {
            return s;
        } else {
            StringBuilder result = new StringBuilder();
            int start = 0;
            for(int i=1;i<s.length();i++) {
                if (i % 4 == 0) {
                    result.append(s.substring(start, i));
                    result.append(" ");
                    start = i;
                }
                if (i == s.length() - 1) {
                    result.append(s.substring(start));
                }
            }
            return result.toString();
        }
    }

    public static String delTheSpace(String s) {
        return s.replace(" ", "");
    }

    public static String decimalFormat(String num) {
        DecimalFormat df = new DecimalFormat("00000000");
        return df.format(Integer.parseInt(num));
    }

    public static boolean isNullOrEmptOrZero(String money) {
        return isNullOrEmpty(money) || Integer.parseInt(money) == 0;
    }

    public static boolean isTel(String tel) {
        if (isNullOrEmpty(tel)) {
            return false;
        }
        Pattern p = Pattern.compile("^1[3|5|7|8][0-9]\\d{8}$");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    public static boolean contains(String[] specialCenterBranch, String s) {
        for (String item : specialCenterBranch) {
            if (s.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
