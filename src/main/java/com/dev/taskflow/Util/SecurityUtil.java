package com.dev.taskflow.Util;

import com.dev.taskflow.Entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtil {

    private SecurityUtil() {}

    public static User getAuthenticatedUser() {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() ||
                    Objects.equals(authentication.getPrincipal(), "anonymousUser")) {
                throw new SecurityException("Usuário não autenticado");
            }

            return (User) authentication.getPrincipal();
        } catch (ClassCastException e) {
            throw new SecurityException("Erro ao identificar o usuário logado");
        }
    }
}
