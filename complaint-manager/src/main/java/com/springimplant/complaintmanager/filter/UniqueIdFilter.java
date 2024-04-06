package com.springimplant.complaintmanager.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springimplant.complaintmanager.config.UniqueIdFilterConfiguration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class UniqueIdFilter extends OncePerRequestFilter{
	
	private final String responseHeader;
	private final String mdcKey;
	private final String requestHeader;
	
	public UniqueIdFilter() {
		responseHeader= UniqueIdFilterConfiguration.DEFAULT_HEADER_TOKEN;
		mdcKey= UniqueIdFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY;
		requestHeader= UniqueIdFilterConfiguration.DEFAULT_HEADER_TOKEN;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			final String token = extractToken(request);
			MDC.put(mdcKey, token);
			if(StringUtils.hasText(responseHeader)) {
				response.addHeader(responseHeader, token);
			}
			filterChain.doFilter(request, response);	
		} finally {
			MDC.remove(mdcKey);
		}
	}
	
	private String extractToken(final HttpServletRequest request) {
		final String token;
		if(StringUtils.hasText(requestHeader) && StringUtils.hasText(request.getHeader(requestHeader))) {
			token= request.getHeader(requestHeader);
		} else {
			token=UUID.randomUUID().toString().toUpperCase().replace("-","");
		}
		return token;
	}

	public UniqueIdFilter(String responseHeader, String mdcKey, String requestHeader) {
		super();
		this.responseHeader = responseHeader;
		this.mdcKey = mdcKey;
		this.requestHeader = requestHeader;
	}
	
	@Override
	protected boolean isAsyncDispatch(HttpServletRequest request) {
		return false;
	}
	
	@Override
	protected boolean shouldNotFilterErrorDispatch() {
		return false;
	}
	
}
