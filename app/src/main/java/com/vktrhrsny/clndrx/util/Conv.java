package com.vktrhrsny.clndrx.util;

import com.vktrhrsny.clndrx.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Conv {

   public static String dateToString(Date date){
       Calendar cal = Calendar.getInstance(TimeZone.getDefault());
       cal.setTime(date);
       int year = cal.get(Calendar.YEAR);
       int month = cal.get(Calendar.MONTH);
       int day = cal.get(Calendar.DAY_OF_MONTH);
       return year+"_"+month+"_"+day;
   }

    public static Date stringToDate(String sDate){
       Date date = new Date();
       String[] ymd = sDate.split("_");
        Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.set(Integer.valueOf(ymd[0]),Integer.valueOf(ymd[1]),Integer.valueOf(ymd[2]));
       return cal.getTime();
    }

    public static float map(float init, float begin1, float end1, float begin2, float end2){
       return begin2+(end2-begin2)*((init-begin1)/(end1-begin1));
    }

    public static int getEmotIconImageResource(int emotIcon){
       int imageResCode;
       switch (emotIcon){
           case 1: imageResCode = R.drawable.ic_vhappy;
           break;
           case 2: imageResCode = R.drawable.ic_happy;
           break;
           case 3: imageResCode = R.drawable.ic_neutral;
           break;
           case 4: imageResCode = R.drawable.ic_sad;
           break;
           case 5: imageResCode = R.drawable.ic_vsad;
           break;
           default: imageResCode = R.drawable.ic_unspec;
       }
       return imageResCode;
    }
}
