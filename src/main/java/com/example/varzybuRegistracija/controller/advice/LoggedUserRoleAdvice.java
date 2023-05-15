package com.example.varzybuRegistracija.controller.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
@RequiredArgsConstructor
@Slf4j
public class LoggedUserRoleAdvice {

    @ModelAttribute("isAdmin")
    public boolean isLoggedInUserAdmin() {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.getAuthorities().isEmpty()) {
                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    SimpleGrantedAuthority auth = (SimpleGrantedAuthority) grantedAuthority;
                    if (auth.getAuthority().equals("ROLE_ADMIN")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Exception while attempting to determine if the logged in user is admin", e);
        }
        return false;
    }
}
