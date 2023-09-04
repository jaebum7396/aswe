package com.aswe.user.service;

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
import com.aswe.user.model.Auth;
import com.aswe.user.model.SignupRequest;
import com.aswe.user.model.User;
import com.aswe.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    public boolean duplicateIdValidate(SignupRequest signupRequest) {
        boolean check = userRepository.findByUserId(signupRequest.getUserId()).isPresent();
        return check;
    }

    public Map<String, Object> signup(SignupRequest signupRequest) throws Exception {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        //중복 아이디 검사
        if (duplicateIdValidate(signupRequest)) {
            throw new BadCredentialsException("중복된 아이디입니다.");
        }
        signupRequest.setUserPw(passwordEncoder.encode(signupRequest.getUserPw()));
        User userEntity = signupRequest.toEntity();
        userEntity.setDeleteYn("N");
        //ROLE 설정
        String userType = signupRequest.getUserType();
        userEntity.setRoles(Collections.singletonList(Auth.builder().authType(userType).build()));

        userRepository.save(userEntity);

        return resultMap;
    }
}