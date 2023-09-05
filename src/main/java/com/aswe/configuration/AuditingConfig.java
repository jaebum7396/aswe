package com.aswe.configuration;

import com.aswe.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AuditingConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        System.out.println("AuditorAware<String> auditorProvider()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication : " + authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            log.debug("Not found AuthenticationToken");
            return null;
        }
        String currentUser = "anonymous"; // 기본값 설정
        User userEntity = (User) authentication.getDetails();
        return () -> Optional.of(userEntity.getUserCd());
    }
}
