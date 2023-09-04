package com.aswe.goods.service;

import com.aswe.common.CommonUtils;
import com.aswe.goods.model.Goods;
import com.aswe.user.model.Auth;
import com.aswe.user.model.SignupRequest;
import com.aswe.user.model.User;
import com.aswe.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsService {
    @Autowired UserRepository userRepository;
    @Autowired CommonUtils commonUtils;

    public Map<String, Object> searchGoods(String goodsCd) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> searchGoodsPrice(String goodsCd, LocalDateTime insertDT) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> createGoods(HttpServletRequest request, Goods goods) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Claims claim = commonUtils.getClaims(request);
        ArrayList<Auth> auth = claim.get("roles", ArrayList.class);
        System.out.println(auth.toString());
        return resultMap;
    }

    public Map<String, Object> updateGoods(Goods goods) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }

    public Map<String, Object> deleteGoods(Goods goods) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        return resultMap;
    }
}