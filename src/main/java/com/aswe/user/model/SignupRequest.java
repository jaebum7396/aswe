package com.aswe.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @ApiModelProperty(value="userId", example="mart", required=true)
    private String userId;
    @ApiModelProperty(value="userPw", example="mart1234", required=true)
    private String userPw;
    @ApiModelProperty(value="userType", example="MART", required=true)
    private String userType;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}