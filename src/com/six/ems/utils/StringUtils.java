package com.six.ems.utils;

public class StringUtils {
    public static boolean isNotEmpty(String string){
        if (string!=null && string.length()>0){
            return true;
        }
        return false;
    }
}
