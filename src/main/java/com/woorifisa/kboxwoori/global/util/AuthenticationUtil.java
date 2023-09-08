package com.woorifisa.kboxwoori.global.util;

import com.woorifisa.kboxwoori.global.config.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {
    public static String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails currentUser = (CustomUserDetails) principal;
        return currentUser.getUsername();
    }

    public static Boolean isWooriLinked() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails currentUser = (CustomUserDetails) principal;
        return currentUser.isWooriLinked();
    }
}
