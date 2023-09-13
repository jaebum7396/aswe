# ASWE
## 구현스펙
Java 11, Spring Boot 2.7.8, JPA, QueryDSL, H2, Gradle

## 실행방법
* [H2 Database](https://www.h2database.com/html/main.html) 설치가 필요합니다!
* 이후 빌드 한 뒤 메인 메서드 실행


## 구현기능
![캡처](https://github.com/jaebum7396/aswe/assets/38182229/56e57b0c-8fb1-4ff6-8a91-5e1905636226)
* 일부 요청에는 요청 헤더에 Authorization이 필요합니다!(-H Authorization : ${jwt} -- Bearer 없이 token만!)
### CouponController
##### 1. 쿠폰생성 -- 요청 헤더에 MART권한을 가진 Authorization 토큰이 필요합니다
![쿠폰생성](https://github.com/jaebum7396/aswe/assets/38182229/13649b0f-0278-46ff-992d-32912fed482d)
### GoodsController
##### 1. 상품생성 -- 요청 헤더에 MART권한을 가진 Authorization 토큰이 필요합니다
![상품생성](https://github.com/jaebum7396/aswe/assets/38182229/f30e8b69-da30-4e97-ba41-1944d6ea00d7)
##### 2. 상품수정 -- 요청 헤더에 MART권한을 가진 Authorization 토큰이 필요합니다
![상품수정](https://github.com/jaebum7396/aswe/assets/38182229/ea928cf4-7bbb-42c1-908e-40ab4eca7aa1)
##### 3. 상품삭제 -- 요청 헤더에 MART권한을 가진 Authorization 토큰이 필요합니다
![상품삭제](https://github.com/jaebum7396/aswe/assets/38182229/e14f54c9-12a6-431e-a67a-ec2aae3c34e6)
##### 4. 상품조회
![상품조회](https://github.com/jaebum7396/aswe/assets/38182229/617638f6-fde3-4a3b-9722-9f5be3efa51c)
##### 5. 상품금액시간별조회
![상품금액시간조회](https://github.com/jaebum7396/aswe/assets/38182229/542b526e-f6cd-45b0-bb04-c90547fe9a97)
### OrderController
##### 1. 주문생성
![주문생성](https://github.com/jaebum7396/aswe/assets/38182229/23e83ef3-f497-4ca9-98bb-69a307c9bc83)
##### 2. 주문조회
![주문조회](https://github.com/jaebum7396/aswe/assets/38182229/76b2e29f-4950-4d12-88dd-255140983363)
### UserContorller
##### 1. 로그인
![로그인](https://github.com/jaebum7396/aswe/assets/38182229/3019cd43-9fc9-4412-a632-c61c01fadc7e)

## 서비스 요구사항

#### 1. 마트 권한과 일반 사용자 권한이 구분되어있다. ✔  
- MART, USER 별로 유저를 생성하는 기능 및 로그인 시 JWT에 적재하여 권한을 구분하였습니다. MART(id:test_mart/pw:1) USER(id:test/pw:1)
#### 2. 상품에 대한 생성, 수정, 삭제는 마트 권한이 필요하다. ✔  
- MART 권한 유저만 가능하도록 구현하였습니다. 
#### 3. 상품을 생성할 수 있다. ✔  
- MART 권한 유저만 가능하도록 구현하였습니다. 
#### 4. 상품 가격을 수정할 수 있다. ✔  
- MART 권한 유저만 가능하도록 구현하였고 상품 가격에 한해 물리적 수정이 아닌 논리 수정으로 구현하였습니다.(가격 히스토리를 위해 TB_GOODS_PRICE 테이블에 INSERT하고 MAIN_PRICE 한행을 가격으로 가져오도록)
#### 5. 특정 시점의 상품 가격을 조회할 수 있다. ✔  
- (String goodsCd, String insertDt("yyyy-MM-dd hh:mm:ss"))
#### 6. 상품을 삭제할 수 있다. ✔  
- 물리삭제가 아닌 DELETE_YN 값으로 처리하도록 구현하였습니다.(Y값 일때 삭제)
#### 7. 주문에 대한 총 금액을 계산할 수 있다.✔  
- 상품내용 및 배달비, 쿠폰 등 JSON 데이터 전송시 계산하여 TB_ORDER 및 TB_ORDER_DETAIL 데이터 생성하고 각각 금액을 DELIVERY_PRICE(배달비), TOTAL_GOODS_PRICE(상품총합금액), TOTAL_DISCOUNT_PRICE(할인금액), TOTAL_PAY_PRICE(총 결제예정금액) 필드에 저장하도록 구현하였습니다. 
#### 8. 주문에 대한 필요 결제 금액을 계산할 수 있다.✔  
- 제품별 쿠폰 및 할인 반영 상황은 TB_ORDER_DETAIL 테이블에 저장토록, 종합 쿠폰 및 할인 반영 상황은 TB_ORDER 테이블에 저장토록 구현하였습니다.

#### 테스트 코드 -> GoodsControllerTest, OrderControllerTest 
##### 이외 swagger 구성하여 요청 테스트 가능토록 하였습니다
