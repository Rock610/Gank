package com.rock.android.rocklibrary.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rock on 16/3/22.
 */
public class DateFormat {

    public static final long hour24 = 24*60*60*1000;

    public static String dateToString(long date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date(date));
    }

    public static String dateToString(Date date){
        if(date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }


    public static Date String2Date(String dateStr,String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date String2Date(String dateStr){
        return String2Date(dateStr,"yyyy-MM-dd'T'HH:mm:ss'Z'");
    }
}
