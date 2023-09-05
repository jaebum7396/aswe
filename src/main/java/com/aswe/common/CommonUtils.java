package com.aswe.common;

import com.aswe.common.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommonUtils {
    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    public ResponseEntity<Response> okResponsePackaging(Map<String, Object> result) {
        Response response = Response.builder()
                .message("요청 성공")
                .result(result).build();
        return ResponseEntity.ok().body(response);
    }

    // objectMapper 는 전역으로 두고 쓴다 - 생성 비용이 비싸기때문
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
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

    public static HashMap<String, Object> convertRequestParameterMap(HttpServletRequest request) {
        HashMap<String, Object> mapParam = new HashMap<String, Object>();
        try {
            // parameter 추가
            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String key = params.nextElement();
                System.out.println("key : " +request.getParameter(key));
                mapParam.put(key, request.getParameter(key));
            }

        } catch (Exception e) {
            log.debug("{}", e.toString());
        }
        return mapParam;
    }

    public static String getJsonPretty(Object p_obj) {
        String strReturn = "";
        try {
            if (p_obj != null) {
                strReturn = CommonUtils.getString(CommonUtils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(p_obj));
            }
        } catch (Exception e) {
            log.debug("{}", e.toString());
        }
        return strReturn;
    }

    // getString 함수
    public static String getString(Object p_Object) {
        String strReturn = "";

        try {
            if (p_Object != null) {
                strReturn = StringUtils.trimToEmpty(p_Object.toString());
            }

        } catch (Exception e) {
            log.debug("{}", e.toString());
        }
        return strReturn;
    }

    public static String readRequestBody(CachedBodyHttpServletWrapper requestWrapper) {
        try (BufferedReader reader = requestWrapper.getReader()) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
