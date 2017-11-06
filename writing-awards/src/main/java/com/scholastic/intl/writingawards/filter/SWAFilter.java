package com.scholastic.intl.writingawards.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.model.AuthUserVO;

public class SWAFilter implements Filter{
	
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SWAFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		if (request instanceof HttpServletRequest) {
			final HttpServletRequest req = (HttpServletRequest) request;
			String path = req.getRequestURI();
			LOGGER.info("SWAFilter#" + path);
			if (path.contains("rest") &&  !path.contains("public")) {
				HttpSession session = req.getSession();
				if(session != null){
					AuthUserVO authUserVO = (AuthUserVO) session.getAttribute("userObj");
					if (authUserVO != null) {
						
					}else{
				LOGGER.info("Not Authorised ............ ");
						res.sendRedirect(req.getContextPath());
						return;
					}
				}else{
					 LOGGER.info("Session timeout ............ ");
					res.sendRedirect(req.getContextPath());
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
	}
}
