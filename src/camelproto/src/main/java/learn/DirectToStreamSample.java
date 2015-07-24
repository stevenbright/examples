package learn;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.stream.StreamComponent;
import org.apache.camel.impl.DefaultCamelContext;

public final class DirectToStreamSample {

  public static void main(String[] args) throws Exception {
    System.out.println("camelproto started...");

    final DefaultCamelContext context = new DefaultCamelContext();
    context.addComponent("stream", new StreamComponent());
    context.addRoutes(new DirectToStreamRouteBuilder());
    final ProducerTemplate template = context.createProducerTemplate();

    try {
      context.start();
      template.sendBody("direct:in", "This is a camel test");

      Thread.sleep(500L);
    } finally {
      context.stop();
    }
  }

  private static final class DirectToStreamRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
      from("direct:in").to("stream:out");
    }
  }
}
