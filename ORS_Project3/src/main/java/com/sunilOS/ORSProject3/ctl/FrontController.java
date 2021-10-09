package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunilOS.ORSProject3.util.ServletUtility;

/**
 * Front Functionality ctl. to perform session management operation
 * @author Anshul
 *
 */


@WebFilter(urlPatterns={"/ctl/*","/doc/*"})
public class FrontController implements Filter{
	public void init(FilterConfig conf) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		HttpSession session = request.getSession();
		
		String uri=request.getRequestURI();
		request.setAttribute("uri", uri);
		
		if (session.getAttribute("user") == null) {
			ServletUtility.setErrorMessage("Your session has been expired. Please re-login!", request);
			ServletUtility.forward(ORSView.LOGIN_VIEW,request,response);
			return;
		} else {
			chain.doFilter(req, resp);
		}
	}

	public void destroy() {
	}
}

