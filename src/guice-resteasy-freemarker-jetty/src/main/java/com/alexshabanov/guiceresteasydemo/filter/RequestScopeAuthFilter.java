package com.alexshabanov.guiceresteasydemo.filter;

import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author Alexander Shabanov
 */
@Singleton
@WebFilter(urlPatterns = "/rest/*")
public class RequestScopeAuthFilter implements Filter {
  public RequestScopeAuthFilter() {
    LoggerFactory.getLogger(getClass()).info("RequestScopeAuthFilter created");
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {

  }
}
