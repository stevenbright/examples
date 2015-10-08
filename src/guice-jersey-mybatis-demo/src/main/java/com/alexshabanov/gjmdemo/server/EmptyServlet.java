package com.alexshabanov.gjmdemo.server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An Empty, "do nothing servlet" to add to the context. Otherwise, the filters
 * will never kick in.
 */
public final class EmptyServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setHeader("X-Position", "Base");
    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
  }
}