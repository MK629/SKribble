package com.sKribble.api.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;

public class CurrentUserInfoUtil {

    public static String getCurrentUserPrincipalName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Collection<? extends GrantedAuthority> getCurrentUserAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public static void checkExistence(User user){
        if(user == null){
            throw new IllogicalNullException(CRUDErrorMessages.ILLOGICAL_NULL_ERROR);
        }
    }
}
