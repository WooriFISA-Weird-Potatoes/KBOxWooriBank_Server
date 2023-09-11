package com.woorifisa.kboxwoori.global.util;

import com.woorifisa.kboxwoori.domain.user.entity.Role;
import com.woorifisa.kboxwoori.global.config.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

    public static Role getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return Role.valueOf(authorities.iterator().next().getAuthority());
    }
}
