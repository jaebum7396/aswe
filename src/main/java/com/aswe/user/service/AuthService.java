package com.aswe.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aswe.user.model.*;
import com.aswe.user.repository.AuthRepository;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    @Autowired AuthRepository authRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    private Key secretKey;
    @Value("${jwt.secret.key}") private String JWT_SECRET_KEY;
    // 만료시간
    @Value("${token.access-expired-time}") private long ACCESS_EXPIRED_TIME;
    // 재발급 토큰 만료시간
    @Value("${token.refresh-expired-time}") private long REFRESH_EXPIRED_TIME;
    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    public String createAccessToken(String userCd, List<Auth> roles) {
        Claims claims = Jwts.claims().setSubject(userCd);
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()  + ACCESS_EXPIRED_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> generateToken(LoginRequest loginRequest){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User userEntity = authRepository.findByUserId(loginRequest.getUserId())
            .orElseThrow(() -> new BadCredentialsException(loginRequest.getUserId()+" : 아이디가 존재하지 않습니다."));
        if (!passwordEncoder.matches(loginRequest.getUserPw(), userEntity.getUserPw())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }
        String userCd = userEntity.getUserCd();
        List<Auth> roles = userEntity.getRoles();

        String accessToken = createAccessToken(userCd, roles);

        resultMap.put("token", accessToken);
        return resultMap;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User userEntity = authRepository.findByUserId(userId).orElseThrow(
            () -> new UsernameNotFoundException("Invalid authentication!")
        );
        return new CustomUserDetails(userEntity);
    }
}