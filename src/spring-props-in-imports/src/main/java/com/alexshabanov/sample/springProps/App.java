package com.alexshabanov.sample.springProps;

import com.alexshabanov.sample.springProps.context.AppContextInitializer;
import com.alexshabanov.sample.springProps.service.FooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Entry point.
 */
public final class App implements Runnable {
  private final Logger log;
  private final FooService fooService;

  public App(FooService fooService) {
    log = LoggerFactory.getLogger(App.class);
    this.fooService = fooService;
  }

  public static void main(String[] args) {
    initContextOverride(Collections.unmodifiableList(Arrays.asList(args)));

    final String[] contextPaths = {"classpath:/springPropsApp/spring/app-context.xml"};
    // NOTE: DO NOT refresh context here as it will result in spring not being able to resolve all the imports
    try (final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(contextPaths, false)) {
      // this makes environment properties known to spring, it should be called PRIOR to app context refresh
      new AppContextInitializer().initialize(context);
      context.refresh();
      context.start();

      // get Runnable application bean and run it
      context.getBean("app", Runnable.class).run();
    }
  }

  @Override
  public void run() {
    log.info("Application started");
    fooService.bar();
  }

  //
  // Private
  //

  private static void initContextOverride(List<String> args) {
    System.setProperty("app.contextOverride", getContextOverridePath(args));
  }

  private static String getContextOverridePath(List<String> args) {
    int overridePropIndex = args.indexOf("--override") + 1;
    if (overridePropIndex <= 0) {
      return "classpath:/springPropsApp/empty.properties";
    }
    return args.get(overridePropIndex);
  }
}
