package com.aswe.user.model.dto;

import com.aswe.user.model.entity.Auth;
import com.aswe.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<Auth> roles = new ArrayList<>();
    private String token;
}