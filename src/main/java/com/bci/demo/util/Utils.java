package com.bci.demo.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    public static String format(Date date){
        LocalDateTime localDateTime = new Timestamp(date.getTime()).toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy 'at' hh:mm a");
        return dateTimeFormatter.format(localDateTime);
    }

    public static boolean format(String param, String regex){
        return param.matches(regex);
    }
}
