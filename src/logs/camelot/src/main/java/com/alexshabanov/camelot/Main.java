package com.alexshabanov.camelot;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Entry point.
 *
 * @author Alexander Shabanov
 */
public final class Main {

  public static void main(String[] args) throws Exception {
    final DefaultCamelContext context = new DefaultCamelContext();
    context.addRoutes(new MainRouteBuilder());

    try {
      context.start();

      Thread.sleep(500L);
    } finally {
      context.stop();
    }
  }

  //
  // Private
  //

  private static final class MainRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
      from("stream:file?fileName=/tmp/slp.log&scanStream=true&scanStreamDelay=100") // analog of UNIX tail
          .split(body(String.class).tokenize("\n"))
          .filter(new MalformedLineFilter())
          .process(new LineProcessor())
          .to("stream:file?fileName=/dev/stdout")
      ;
    }
  }

  private static final class MalformedLineFilter implements Predicate {

    @Override
    public boolean matches(Exchange exchange) {
      final String line = exchange.getIn().getBody(String.class);
      return line.length() > 0;
    }
  }

  private static final class LineProcessor implements Processor {
    private int count = 0;

    @Override
    public void process(Exchange exchange) throws Exception {
      final Message in = exchange.getIn();
      in.setBody("#" + count + ": " + in.getBody(String.class));
      ++count;
    }
  }
}
