package com.sKribble.api.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserInfoUtil {

    public static String getCurrentUserPrincipalName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Collection<? extends GrantedAuthority> getCurrentUseAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
