package ${package};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents sample service.
 */
public final class HelloService {
    private static final Logger LOG = LoggerFactory.getLogger(HelloService.class);

    public int add(int a, int b) {
        LOG.info("adding {} + {}", a, b);
        return a + b;
    }
}
