package com.sKribble.api.utils;

/**
 * A util to check string inputs at service level, to decide whether inputted values or defaults will be saved.
 */
public class StringCheckerUtil {

    public static Boolean isNotHollow(String string){
        if(string == null || string.isEmpty() || string.isBlank()){
            return false;
        }
        else{
            return true;
        }
    }

    //Ha ha :^). I don't really know why I wrote this. Probably redundant. Remove later.
    public static Boolean isNotNull(String string){
        if(string == null){
            return false;
        }
        else{
            return true;
        }
    }
}