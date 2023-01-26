package com.superboard.onbrd.global.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.superboard.onbrd.global.interceptor.IpCheckInterceptor;

/**
 * @author Park jae wook
 *
 */
@Configuration
public class ServerConfig implements WebMvcConfigurer{
	
	@Autowired
	private IpCheckInterceptor ipCheckInterceptor;
	
	private static final List<String> URL_PATTERNS = Arrays.asList("/swagger-ui/*");  //인터셉터가 동작 해야 될 요청 주소 mapping 목록
	// 인터셉터 주소 세팅
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(ipCheckInterceptor).addPathPatterns(URL_PATTERNS);
	}
}
