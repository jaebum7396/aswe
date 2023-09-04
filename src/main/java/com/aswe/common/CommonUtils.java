package com.aswe.common;

import com.aswe.common.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonUtils {
    public ResponseEntity<Response> okResponsePackaging(Map<String, Object> result) {
        Response response = Response.builder()
                .message("요청 성공")
                .result(result).build();
        return ResponseEntity.ok().body(response);
    }
}
