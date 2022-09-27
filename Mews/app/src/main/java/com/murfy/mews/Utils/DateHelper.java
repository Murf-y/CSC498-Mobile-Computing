package com.murfy.mews.Utils;

import android.icu.text.SimpleDateFormat;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static String getTodayDateFormatted(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
            Date date= sdf.parse(String.valueOf(Calendar.getInstance().getTime()));
            sdf.applyPattern("EEEE, MMM d");
            String str = sdf.format(date);
            return str;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
