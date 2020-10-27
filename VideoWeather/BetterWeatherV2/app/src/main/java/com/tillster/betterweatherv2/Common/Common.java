package com.tillster.betterweatherv2.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common
{
    public static final String APP_ID="52f3835251437df7c089e24f9acd17e5";
    public static Location currentLocation =null;


    public static String convertTime(int dt)
    {
        Date date = new Date (dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm EEEE MM yyyy");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;

    }
}
