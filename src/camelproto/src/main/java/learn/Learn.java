package learn;

import org.apache.camel.impl.DefaultCamelContext;

public final class Learn {
    
    public static void main(String[] args) throws Exception {
        System.out.println("camelproto started...");

        final DefaultCamelContext context = new DefaultCamelContext();
        try {
            context.start();
        } finally {
            context.stop();
        }
    }
}
