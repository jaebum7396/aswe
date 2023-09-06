package com.aswe.user.model.dto;

import com.aswe.user.model.entity.User;
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
    @ApiModelProperty(value="userId", example="mart_test2", required=true)
    private String userId;
    @ApiModelProperty(value="userPw", example="mart1234", required=true)
    private String userPw;
    @ApiModelProperty(value="UserType", example="enum(MART,USER)", required=true)
    private UserType userType;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}