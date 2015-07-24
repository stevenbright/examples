package learn;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class DirectToStreamSampleTest extends CamelTestSupport {

  @EndpointInject(uri = "mock:result")
  protected MockEndpoint resultEndpoint;

  @Produce(uri = "direct:start")
  protected ProducerTemplate template;

  @Test
  public void shouldGetMessage() throws InterruptedException {
    // Given:
    final String expectedBody = "<matched/>";
    resultEndpoint.expectedBodiesReceived(expectedBody);

    // When:
    template.sendBodyAndHeader(expectedBody, "foo", "bar");

    // Then:
    resultEndpoint.assertIsSatisfied();
  }

  @Test
  public void shouldFailToGetMessage() throws InterruptedException {
    // Given:
    resultEndpoint.expectedMessageCount(0);

    // When:
    template.sendBodyAndHeader("<notMatched/>", "foo", "notMatchedHeaderValue");

    // Then:
    resultEndpoint.assertIsSatisfied();
  }

  @Override
  protected RouteBuilder createRouteBuilder() {
    return new RouteBuilder() {
      public void configure() {
        from("direct:start").filter(header("foo").isEqualTo("bar")).to("mock:result");
      }
    };
  }
}
