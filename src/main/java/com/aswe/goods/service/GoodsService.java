package com.aswe.goods.service;

import com.aswe.common.CommonUtils;
import com.aswe.common.Constants.Constants;
import com.aswe.common.exception.NotFoundException;
import com.aswe.goods.model.dto.GoodsDTO;
import com.aswe.goods.model.dto.UpdateGoodsRequest;
import com.aswe.goods.model.entity.Goods;
import com.aswe.goods.model.entity.GoodsPrice;
import com.aswe.goods.repository.GoodsRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {
    @Autowired GoodsRepository goodsRepository;
    @Autowired CommonUtils commonUtils;

    public Map<String, Object> getGoods(String goodsCd) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        // 주어진 goodsCd에 해당하는 상품을 조회합니다.
        Goods goods = goodsRepository.getGoods(goodsCd).orElseThrow(() -> new NotFoundException(Constants.PRODUCT_NOT_FOUND));
        resultMap.put("goods", goods);
        return resultMap;
    }

    public Map<String, Object> getGoodsPrice(String goodsCd, String insertDT) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        // 주어진 goodsCd와 insertDT에 해당하는 상품을 조회합니다.
        Goods goods = goodsRepository.getGoodsPrice(goodsCd, insertDT).orElseThrow(() -> new NotFoundException(Constants.PRODUCT_NOT_FOUND));
        resultMap.put("goods", goods);
        return resultMap;
    }

    public Map<String, Object> createGoods(HttpServletRequest request, GoodsDTO createGoodsDTO) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        ArrayList<HashMap<String, String>> roles = claim.get("roles", ArrayList.class);
        roles.stream().filter(m -> m.get("authType").equals("MART")).findAny().orElseThrow(() -> new BadCredentialsException(Constants.BAD_CREDENTIAL_MART));

        // 새로운 상품을 생성하고 저장합니다.
        Goods goods = createGoodsDTO.toEntity();
        goods.getGoodsPrices().get(0).setInsertUserCd(claim.getSubject());
        goods.getGoodsPrices().get(0).setCurrentPriceYn("Y");
        goods.setInsertUserCd(claim.getSubject());
        goodsRepository.save(goods);

        resultMap.put("goods", goods);
        return resultMap;
    }

    public Map<String, Object> updateGoods(HttpServletRequest request, GoodsDTO updateGoodsDTO) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        ArrayList<HashMap<String, String>> roles = claim.get("roles", ArrayList.class);
        roles.stream().filter(m -> m.get("authType").equals("MART")).findAny().orElseThrow(() -> new BadCredentialsException(Constants.BAD_CREDENTIAL_MART));

        // 업데이트할 상품을 조회하고, 상품 정보를 업데이트합니다.
        Goods goods = goodsRepository.getGoods(((UpdateGoodsRequest) updateGoodsDTO).getGoodsCd()).orElseThrow(() -> new NotFoundException(Constants.PRODUCT_NOT_FOUND));
        goods.setGoodsNm(updateGoodsDTO.getGoodsNm());
        goods.getGoodsPrices().forEach(goodsPrice -> {
            goodsPrice.setCurrentPriceYn("N"); // 이전 가격들의 현재가 여부를 N으로 변경
            goodsPrice.setUpdateUserCd(claim.getSubject());
        });
        ArrayList<GoodsPrice> goodsPrices = new ArrayList<GoodsPrice>();
        goodsPrices.add(GoodsPrice.builder()
            .goods(goods)
            .goodsPrice(((UpdateGoodsRequest) updateGoodsDTO).getPrice())
            .currentPriceYn("Y")
            .insertUserCd(claim.getSubject())
            .build());
        goods.setGoodsPrices(goodsPrices);
        goods.setUpdateUserCd(claim.getSubject());
        goodsRepository.save(goods);

        resultMap.put("goods", goods);
        return resultMap;
    }

    public Map<String, Object> deleteGoods(HttpServletRequest request, String goodsCd) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        ArrayList<HashMap<String, String>> roles = claim.get("roles", ArrayList.class);
        roles.stream().filter(m -> m.get("authType").equals("MART")).findAny().orElseThrow(() -> new BadCredentialsException(Constants.BAD_CREDENTIAL_MART));

        // 상품을 삭제 처리합니다.
        Goods goods = goodsRepository.getGoods(goodsCd).orElseThrow(() -> new NotFoundException(Constants.PRODUCT_NOT_FOUND));
        goods.setDeleteYn("Y");
        goods.setDeleteUserCd(claim.getSubject());
        goodsRepository.save(goods);

        return resultMap;
    }
}