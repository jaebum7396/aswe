package com.aswe.goods.service;

import com.aswe.common.CommonUtils;
import com.aswe.goods.model.dto.GoodsRequest;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {
    @Autowired GoodsRepository goodsRepository;
    @Autowired CommonUtils commonUtils;

    public Map<String, Object> searchGoods(String goodsCd) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> searchGoodsPrice(String goodsCd, LocalDateTime insertDT) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> createGoods(HttpServletRequest request, GoodsRequest createGoodsRequest) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        ArrayList<HashMap<String,String>> roles = claim.get("roles", ArrayList.class);
        roles.stream().filter(m -> m.get("authType").equals("MART")).findAny().orElseThrow(() -> new BadCredentialsException("마트 권한이 없습니다."));
        Goods goods = createGoodsRequest.toEntity();
        goods.getGoodsPrices().get(0).setInsertUserCd(claim.getSubject());
        goods.setInsertUserCd(claim.getSubject());
        goodsRepository.save(goods);
        return resultMap;
    }

    public Map<String, Object> updateGoods(HttpServletRequest request, GoodsRequest updateGoodsRequest) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        ArrayList<HashMap<String,String>> roles = claim.get("roles", ArrayList.class);
        roles.stream().filter(m -> m.get("authType").equals("MART")).findAny().orElseThrow(() -> new BadCredentialsException("마트 권한이 없습니다."));

        Goods goods = goodsRepository.findById(((UpdateGoodsRequest)updateGoodsRequest).getGoodsCd())
                .orElseThrow(() -> new BadCredentialsException("상품이 존재하지 않습니다."));

        if(((UpdateGoodsRequest)updateGoodsRequest).getPrice().compareTo(new BigDecimal(0))<0){
            throw new BadCredentialsException("가격은 0원 이상이어야 합니다.");
        }

        goods.setGoodsNm(updateGoodsRequest.getGoodsNm());
        goods.addGoodsPrice(GoodsPrice.builder()
                .goodsPrice(((UpdateGoodsRequest)updateGoodsRequest).getPrice())
                .insertUserCd(claim.getSubject())
                .build());
        goods.setUpdateUserCd(claim.getSubject());
        goodsRepository.save(goods);
        return resultMap;
    }

    public Map<String, Object> deleteGoods(HttpServletRequest request, String goodsCd) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        ArrayList<HashMap<String,String>> roles = claim.get("roles", ArrayList.class);
        roles.stream().filter(m -> m.get("authType").equals("MART")).findAny().orElseThrow(() -> new BadCredentialsException("마트 권한이 없습니다."));

        Goods goods = goodsRepository.findById(goodsCd)
                .orElseThrow(() -> new BadCredentialsException("상품이 존재하지 않습니다."));

        goods.setDeleteYn("Y");
        goods.setDeleteUserCd(claim.getSubject());
        goodsRepository.save(goods);
        return resultMap;
    }
}