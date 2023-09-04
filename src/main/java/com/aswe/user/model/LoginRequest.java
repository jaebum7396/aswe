package com.aswe.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @ApiModelProperty(value="userId", example="TEST_PLUS", required=true)
    private String userId;
    @ApiModelProperty(value="userPw", example="TEST_PLUS1234", required=true)
    private String userPw;
}