package com.zhrenjie04.alex.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 张人杰
 */
public class NoCachedFilter implements Filter {
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response=(HttpServletResponse)resp;
		chain.doFilter(req, resp);
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Pragma","no-cache");
		response.addIntHeader("Expires", -1);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}
