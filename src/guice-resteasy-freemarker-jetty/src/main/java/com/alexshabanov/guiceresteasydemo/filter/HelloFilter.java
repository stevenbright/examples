package com.alexshabanov.guiceresteasydemo.filter;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author Alexander Shabanov
 */
@Singleton
@WebFilter(urlPatterns = "/*")
public class HelloFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

  }

  @Override
  public void destroy() {

  }
}
