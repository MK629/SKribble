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

    //Ha ha :^). I don't really know why I wrote this...
    public static Boolean isNotNull(String string){
        if(string == null){
            return false;
        }
        else{
            return true;
        }
    }
}