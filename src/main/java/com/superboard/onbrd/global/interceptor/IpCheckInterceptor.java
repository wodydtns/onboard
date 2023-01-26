package com.superboard.onbrd.global.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IpCheckInterceptor implements HandlerInterceptor{
	
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final List<String> WhiteIpList = Arrays.asList("59.17.157.228","59.12.62.150","127.0.0.1");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 		throws Exception {
		
		boolean isAccessable = false;
		String visitIp = request.getRemoteAddr();
		System.out.println(visitIp);
		for (String whiteIp : WhiteIpList) {
			if(visitIp.equals(whiteIp)) {
				return true;
			}
		}
		
		return isAccessable;
	
	}
	
	


}
