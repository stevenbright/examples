package com.mysite.jbossesb;


/**
 * Entry point for ESB Hello Client
 */
public class App {
    private static void usage() {
        System.out.println("Usage:");
        System.out.println("app.jar jms helloJms");
        System.out.println("app.jar esb helloEsb");
    }

    public static void main(String[] args) {
        System.out.println("Hello Sample Client");

        if (args.length < 1) {
            System.out.println("Too few arguments: assuming sample code - jms or esb");
            usage();
            return;
        }

        final ClientSample clientSample;
        if (args[0].equals("jms")) {
            clientSample = new JmsClientSample();
        } else if (args[0].equals("esb")) {
            clientSample = new EsbClientSample();
        } else {
            System.out.println("Unknown sample code - jms or esb expected");
            return;
        }

        try {
            clientSample.apply(args);
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
            usage();
        }
    }
}
