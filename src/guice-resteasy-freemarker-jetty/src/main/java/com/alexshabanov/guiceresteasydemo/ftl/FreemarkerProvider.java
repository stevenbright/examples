package com.alexshabanov.guiceresteasydemo.ftl;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;

/**
 * @author Alexander Shabanov
 */
public class FreemarkerProvider {
  private String encoding;
  private final Configuration configuration;
  private final TaglibFactory taglibFactory;

  public FreemarkerProvider(Configuration configuration, TaglibFactory taglibFactory) {
    this.configuration = configuration;
    this.taglibFactory = taglibFactory;
  }
}
