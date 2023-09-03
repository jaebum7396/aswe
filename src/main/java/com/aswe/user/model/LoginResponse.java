package com.aswe.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    public LoginResponse(User userEntity) {
        this.userId = userEntity.getUserId();
        this.roles = userEntity.getRoles();
    }
    private String userId;
    @Builder.Default
    private Set<Auth> roles = new HashSet<Auth>();
    private String token;
}