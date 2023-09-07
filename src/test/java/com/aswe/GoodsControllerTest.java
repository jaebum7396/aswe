package com.aswe;

import com.aswe.user.model.dto.LoginRequest;
import com.aswe.user.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GoodsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;

    String testMartToken;
    String testMartUserId = "test_mart";
    String testMartPassword = "1";
    String testUserToken;
    String testUserUserId = "test";
    String testUserPassword = "1";

    @BeforeEach
    void login(){
        LoginRequest loginRequestMart = LoginRequest.builder()
                .userId(testMartUserId)
                .userPw(testMartPassword)
                .build();
        testMartToken = String.valueOf(authService.generateToken(loginRequestMart).get("token"));

        LoginRequest loginRequestUser = LoginRequest.builder()
                .userId(testUserUserId)
                .userPw(testUserPassword)
                .build();
        testUserToken = String.valueOf(authService.generateToken(loginRequestUser).get("token"));
    }

    public ResultActions sendMockRequest(String uri, String method, String token, String requestBody) throws Exception {
        return mockMvc.perform(
            MockMvcRequestBuilders.request(HttpMethod.valueOf(method), uri)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }

    @Test
    public void getGoodsTest() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/goods")
                .param("goodsCd", "1")
        )
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsNm").value("test_goods1"));
    }

    @Test
    public void getGoodsPriceTest() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/goods/price")
                .param("goodsCd", "1")
                .param("insertDT", "2023-09-07 15:00:00")
        )
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsPrices[0].goodsPrice").value(8000));
    }

    @Test
    @Transactional
    public void createGoodsTest() throws Exception {
        String requestBody = "{ \"goodsNm\": \"test_goods3\", \"price\": 23000 }";
        String uri = "/goods";

        // user 권한일 경우
        sendMockRequest(uri, "POST", testUserToken, requestBody)
                .andExpect(status().isUnauthorized());
        // mart 권한일 경우
        sendMockRequest(uri, "POST", testMartToken, requestBody)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsNm").value("test_goods3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsPrices[0].goodsPrice").value(23000));
    }

    @Test
    @Transactional
    public void updateGoodsTest() throws Exception {
        String requestBody = "{ \"goodsCd\": \"1\", \"goodsNm\": \"test_goods1\", \"price\": 12345 }";
        String uri = "/goods";

        // user 권한일 경우
        sendMockRequest(uri, "PUT", testUserToken, requestBody)
                .andExpect(status().isUnauthorized());
        // mart 권한일 경우
        sendMockRequest(uri, "PUT", testMartToken, requestBody)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsNm").value("test_goods1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsPrices[0].goodsPrice").value(12345));
    }

    @Test
    @Transactional
    public void deleteGoodsTest() throws Exception {
        String uri = "/goods";

        // user 권한일 경우
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(uri)
                .param("goodsCd", "1")
                .header("Authorization", testUserToken)
        )
        .andExpect(status().isUnauthorized());
        // mart 권한일 경우
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(uri)
                .param("goodsCd", "1")
                .header("Authorization", testMartToken)
        )
        .andExpect(status().isOk());

        mockMvc.perform(
            MockMvcRequestBuilders
                .get(uri)
                .param("goodsCd", "1")
        )
        .andExpect(status().isNotFound());
    }
}
