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
    @ApiModelProperty(value="userId", example="mart", required=true)
    private String userId;
    @ApiModelProperty(value="userPw", example="mart1234", required=true)
    private String userPw;
}