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

    // MockMvc를 사용하여 요청을 보내는 메서드
    public ResultActions sendMockRequest(String uri, String method, String token, String requestBody) throws Exception {
        return mockMvc.perform(
            MockMvcRequestBuilders.request(HttpMethod.valueOf(method), uri)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );
    }

    // "getGoodsTest" 메서드에 대한 테스트
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

    // "getGoodsPriceTest" 메서드에 대한 테스트
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

    // "createGoodsTest" 메서드에 대한 테스트
    @Test
    @Transactional
    public void createGoodsTest() throws Exception {
        String requestBody = "{ \"goodsNm\": \"test_goods3\", \"price\": 23000 }";
        String uri = "/goods";

        // user 권한일 경우 - 권한 없음으로 응답을 기대합니다.
        sendMockRequest(uri, "POST", testUserToken, requestBody)
            .andExpect(status().isUnauthorized());

        // mart 권한일 경우 - 성공적으로 상품을 생성하고 응답을 기대합니다.
        sendMockRequest(uri, "POST", testMartToken, requestBody)
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsNm").value("test_goods3"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsPrices[0].goodsPrice").value(23000));
    }

    // "updateGoodsTest" 메서드에 대한 테스트
    @Test
    @Transactional
    public void updateGoodsTest() throws Exception {
        String requestBody = "{ \"goodsCd\": \"1\", \"goodsNm\": \"test_goods1\", \"price\": 12345 }";
        String uri = "/goods";

        // user 권한일 경우 - 권한 없음으로 응답을 기대합니다.
        sendMockRequest(uri, "PUT", testUserToken, requestBody)
            .andExpect(status().isUnauthorized());

        // mart 권한일 경우 - 성공적으로 상품을 업데이트하고 응답을 기대합니다.
        sendMockRequest(uri, "PUT", testMartToken, requestBody)
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsNm").value("test_goods1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.goods.goodsPrices[0].goodsPrice").value(12345));
    }

    // "deleteGoodsTest" 메서드에 대한 테스트
    @Test
    @Transactional
    public void deleteGoodsTest() throws Exception {
        String uri = "/goods";

        // user 권한일 경우 - 권한 없음으로 응답을 기대합니다.
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(uri)
                .param("goodsCd", "1")
                .header("Authorization", testUserToken)
        )
        .andExpect(status().isUnauthorized());

        // mart 권한일 경우 - 성공적으로 상품을 삭제하고 응답을 기대합니다.
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(uri)
                .param("goodsCd", "1")
                .header("Authorization", testMartToken)
        )
        .andExpect(status().isOk());

        // 상품 삭제 후 상품 조회 시 "상품을 찾을 수 없음"으로 응답을 기대합니다.
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(uri)
                .param("goodsCd", "1")
        )
        .andExpect(status().isNotFound());
    }
}
