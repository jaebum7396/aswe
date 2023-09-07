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
class OrderControllerTest {
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
    @Transactional
    public void createOrderTest() throws Exception {
        String uri = "/order";
        //coupon(test_goods1, 20%) - test_goods1(10000원)*3에 한해 20% 할인(30000*20/100=6000) 적용되어야 함
        String requestBody = "{ \"couponCd\": 1, \"deliveryPrice\": 3000, \"orderDetailList\": [ { \"goodsCd\": 1, \"quantity\": 3 } , { \"goodsCd\": 2, \"quantity\": 2 } ]}";
        sendMockRequest(uri, "POST", testMartToken, requestBody)
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.deliveryPrice").value(3000)) // 배달비
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalGoodsPrice").value(60000)) // 상품가격 합산한 금액
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalDiscountPrice").value(6000))  // 총 할인 금액
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalPayPrice").value(57000)); // 총 지불할 금액(상품가격 합산한 금액+배달비-총 할인 금액)

        //coupon(test_goods1, 5000) - test_goods1에 한해 5000원 할인 적용되어야 함
        requestBody = "{ \"couponCd\": 2, \"deliveryPrice\": 2500, \"orderDetailList\": [ { \"goodsCd\": 1, \"quantity\": 3 } , { \"goodsCd\": 2, \"quantity\": 2 } ]}";
        sendMockRequest(uri, "POST", testMartToken, requestBody)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.deliveryPrice").value(2500)) // 배달비
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalGoodsPrice").value(60000)) // 상품가격 합산한 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalDiscountPrice").value(5000))  // 총 할인 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalPayPrice").value(57500)); // 총 지불할 금액(상품가격 합산한 금액+배달비-총 할인 금액)

        //coupon(test_goods2, 13%) - test_goods2(15000원)*2에 한해 13% 할인(30000*13/100=3900)이 적용되어야 함
        requestBody = "{ \"couponCd\": 3, \"deliveryPrice\": 3000, \"orderDetailList\": [ { \"goodsCd\": 1, \"quantity\": 3 } , { \"goodsCd\": 2, \"quantity\": 2 } ]}";
        sendMockRequest(uri, "POST", testMartToken, requestBody)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.deliveryPrice").value(3000)) // 배달비
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalGoodsPrice").value(60000)) // 상품가격 합산한 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalDiscountPrice").value(3900))  // 총 할인 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalPayPrice").value(59100)); // 총 지불할 금액(상품가격 합산한 금액+배달비-총 할인 금액)

        //coupon(test_goods2, 1327) - test_goods2에 한해 1327원 할인 적용되어야 함
        requestBody = "{ \"couponCd\": 4, \"deliveryPrice\": 3000, \"orderDetailList\": [ { \"goodsCd\": 1, \"quantity\": 3 } , { \"goodsCd\": 2, \"quantity\": 2 } ]}";
        sendMockRequest(uri, "POST", testMartToken, requestBody)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.deliveryPrice").value(3000)) // 배달비
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalGoodsPrice").value(60000)) // 상품가격 합산한 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalDiscountPrice").value(1327))  // 총 할인 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalPayPrice").value(61673)); // 총 지불할 금액(상품가격 합산한 금액+배달비-총 할인 금액)

        //coupon(total, 24%) - 전체 가격(60000원 - 배달비 제외)에 24% 할인(60000*24/100=14400원)이 적용되어야 함
        requestBody = "{ \"couponCd\": 5, \"deliveryPrice\": 3000, \"orderDetailList\": [ { \"goodsCd\": 1, \"quantity\": 3 } , { \"goodsCd\": 2, \"quantity\": 2 } ]}";
        sendMockRequest(uri, "POST", testMartToken, requestBody)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.deliveryPrice").value(3000)) // 배달비
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalGoodsPrice").value(60000)) // 상품가격 합산한 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalDiscountPrice").value(14400))  // 총 할인 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalPayPrice").value(48600)); // 총 지불할 금액(상품가격 합산한 금액+배달비-총 할인 금액)

        //coupon(total, 6937) - 전체 가격(60000원 - 배달비 제외)에 6937원 할인이 적용되어야 함
        requestBody = "{ \"couponCd\": 6, \"deliveryPrice\": 3000, \"orderDetailList\": [ { \"goodsCd\": 1, \"quantity\": 3 } , { \"goodsCd\": 2, \"quantity\": 2 } ]}";
        sendMockRequest(uri, "POST", testMartToken, requestBody)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.deliveryPrice").value(3000)) // 배달비
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalGoodsPrice").value(60000)) // 상품가격 합산한 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalDiscountPrice").value(6937))  // 총 할인 금액
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.order.totalPayPrice").value(56063)); // 총 지불할 금액(상품가격 합산한 금액+배달비-총 할인 금액)

    }
}
