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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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

    public Claims getClaims(HttpServletRequest request){
        try{
            Key secretKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Claims claim = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(request.getHeader("authorization")).getBody();
            return claim;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "로그인 시간이 만료되었습니다.");
        } catch (Exception e) {
            throw new BadCredentialsException("인증 정보에 문제가 있습니다.");
        }
    }

    public Map<String, Object> signup(SignupRequest signupRequest) throws Exception {
        System.out.println("UserService.signup.params : " + signupRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        //중복 아이디 검사
        if (duplicateIdValidate(signupRequest)) {
            throw new BadCredentialsException("중복된 아이디입니다.");
        }
        signupRequest.setUserPw(passwordEncoder.encode(signupRequest.getUserPw()));
        User userEntity = signupRequest.toEntity();
        userEntity.setDeleteYn("N");
        //ROLE 설정
        Set<Auth> roles = new HashSet<>();
        roles.add(Auth.builder().authType("ROLE_USER").build());
        userEntity.setRoles(roles);

        userRepository.save(userEntity);

        return resultMap;
    }
}