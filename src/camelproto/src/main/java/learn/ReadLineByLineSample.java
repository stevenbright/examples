package learn;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.stream.StreamComponent;
import org.apache.camel.impl.DefaultCamelContext;

public final class ReadLineByLineSample {

  public static void main(String[] args) throws Exception {
    final DefaultCamelContext context = new DefaultCamelContext();
    context.addComponent("stream", new StreamComponent());
    context.addRoutes(new LineByLineRouteBuilder());
    final ProducerTemplate template = context.createProducerTemplate();

    try {
      context.start();
      template.sendBody("direct:in", "one\ntwo\nthree\n\n\nfour\nfive\n");

      Thread.sleep(500L);
    } finally {
      context.stop();
    }
  }

  private static final class LineByLineRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
      from("direct:in")
          .split(body(String.class).tokenize("\n"))
          .filter(new MalformedLineFilter())
          .process(new LineProcessor())
          .to("stream:out");
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

    @Override
    public void process(Exchange exchange) throws Exception {
      final Message in = exchange.getIn();
      in.setBody("LINE=" + in.getBody(String.class));
    }
  }
}
