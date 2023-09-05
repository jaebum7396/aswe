package com.aswe.configuration;

import com.aswe.common.CachedBodyHttpServletWrapper;
import com.aswe.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
public class MyInterceptor implements HandlerInterceptor{
	private String ACTIVE_PROFILE;

	public MyInterceptor(String ACTIVE_PROFILE) {
		this.ACTIVE_PROFILE = ACTIVE_PROFILE;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("===============================================");
		log.info("==================== BEGIN ====================");

		// 요청 헤더를 나열하고 로그로 남깁니다.
		Enumeration eHeader = request.getHeaderNames();
		while (eHeader.hasMoreElements()) {
			String key = (String)eHeader.nextElement();
			String value = request.getHeader(key);
			log.info("헤더 키 : " + key + " ===> 값 : " + value);
		}

		// requestUri 체크 (운영환경에서 게이트웨이에서만 접근가능하도록 체크하기 위한 부분)
		// String requestUri = request.getHeader("x-forwarded-host");

		// 활성 프로파일을 확인하고 그에 따라 로그를 남깁니다.
		System.out.println("ACTIVE_PROFILE : " + ACTIVE_PROFILE);
		if (ACTIVE_PROFILE.equals("local")) {
			log.info("로컬에서 실행 중입니다.");
		}
		/*else {
			log.info("요청_URI : " + requestUri);
			if (("".equals(requestUri) || requestUri == null || !requestUri.equals(GATEWAY_URI))) {
				throw new BadCredentialsException("잘못된 접근입니다.");
			}
		}*/

		// 요청 파라미터를 HashMap으로 변환하고 로그로 남깁니다.
		HashMap<String, Object> requestParameterMap = CommonUtils.convertRequestParameterMap(request);
		StringBuffer paramLog = new StringBuffer();

		if (!requestParameterMap.isEmpty()) {
			paramLog.append("\n========== 파라미터 ==========");
			paramLog.append("\n요청 URI : " + request.getRequestURI());
			paramLog.append("\n요청 파라미터:\n" + CommonUtils.getJsonPretty(requestParameterMap));
			paramLog.append("\n==============================");
			log.info("{}", paramLog.toString());
		}

		// 요청 본문을 읽고 출력합니다.
		final CachedBodyHttpServletWrapper cachingRequest = (CachedBodyHttpServletWrapper) request;
		String requestBodyStr = "";
		requestBodyStr = CommonUtils.readRequestBody(cachingRequest);
		System.out.println("requestBody : " + requestBodyStr); // 요청 본문 내용을 출력합니다.

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("==================== END ======================");
        log.info("===============================================");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)	throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
