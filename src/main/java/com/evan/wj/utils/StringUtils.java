package com.evan.wj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtils {
    public static String getRandomString(int length) {
        Date nowDate = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-");
        StringBuffer sb = new StringBuffer();

        final String s = "abcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomNum = random.nextInt(s.length());
            sb.append(s.charAt(randomNum));
        }
        return simpleDateFormat.format(nowDate) + sb;
    }
}
