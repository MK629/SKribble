package com.sKribble.api.utils;

public class StringCheckerUtil {

    public static Boolean isNotHollow(String string){
        if(string == null || string.isEmpty() || string.isBlank()){
            return false;
        }
        else{
            return true;
        }
    }

    //Ha ha :^)
    public static Boolean isNotNull(String string){
        if(string == null){
            return false;
        }
        else{
            return true;
        }
    }
}