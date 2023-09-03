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
    @ApiModelProperty(value="userId", example="TEST_PLUS", required=true)
    private String userId;
    @ApiModelProperty(value="userPw", example="TEST_PLUS1234", required=true)
    private String userPw;
    @ApiModelProperty(value="userPhoneNo", example="010-0000-0366", required=true)
    private String userPhoneNo;
    @ApiModelProperty(value="userGender", example="M,W", required=true)
    private String userGender;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}