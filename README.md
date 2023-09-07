### 구현 방법 및 구현 여부 
Java11, Spring Boot2.7.8, JPA, QueryDSL, H2, Gradle, JUNIT

### 실행방법
* [H2 Database](https://www.h2database.com/html/main.html)설치가 필요합니다!
* 이후 빌드 한 뒤 메인 메서드 실행

### 구현기능
![캡처](https://github.com/jaebum7396/aswe/assets/38182229/31cae4f1-5111-4f73-8986-43f8ee8cd1b1)


### 서비스 요구사항

1. 마트 권한과 일반 사용자 권한이 구분되어있다. ✔
2. 상품에 대한 생성, 수정, 삭제는 마트 권한이 필요하다. ✔
3. 상품을 생성할 수 있다. ✔
4. 상품 가격을 수정할 수 있다. ✔
5. 특정 시점의 상품 가격을 조회할 수 있다. ✔
   - 예시
     - 2023-01-01 00:00:00 시점의 A 상품 가격 = 1500원
     - 2023-01-15 12:00:00 시점의 A 상품 가격 = 2000원
6. 상품을 삭제할 수 있다. ✔
7. 주문에 대한 총 금액을 계산할 수 있다.✔
   - (각 주문 목록의 상품가격 * 개수) + 배달비
8. 주문에 대한 필요 결제 금액을 계산할 수 있다.✔
   - 쿠폰을 적용하는 경우, 쿠폰으로 할인되는 금액을 반영해서 계산

### 필수 데이터

- 상품 (TB_GOODS)
  - 상품명(GOODS_NM)
  - 가격(TB_GOODS_PRICE.GOODS_PRICE)
- 쿠폰 (TB_COUPON)
  - 쿠폰 적용 방법 (COUPON_TYPE)
    - 비율(RATE) / 고정(FIX) 
    - 각 적용 방법에 따른 적용 비율 / 적용 금액 (DISCOUNT)
  - 쿠폰 적용 범위
    - 주문 전체 (배달비 제외)
    - 특정 상품 한정 (특정 상품의 모든 개수에 적용) (GOODS_CD)
- 주문 (TB_ORDER)
  - 주문 목록 (TB_ORDER_DETAIL)
    - 상품 (GOODS_CD)
    - 개수 (QUANTITY)
  - 배달비 (DELIVERY_PRICE)
