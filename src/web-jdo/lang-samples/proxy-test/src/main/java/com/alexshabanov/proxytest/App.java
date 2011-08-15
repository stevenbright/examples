package com.alexshabanov.proxytest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Entry point.
 */
public final class App {

    private static final String PAGE_URL = "http://www.google.com";

    private static void fetchPage(Proxy proxy) throws IOException {
        System.out.println("Fetching " + PAGE_URL + "...");

        final URL url = new URL(PAGE_URL);

        final HttpURLConnection connection;
        if (proxy != null) {
            connection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            connection = (HttpURLConnection) url.openConnection();
        }

        connection.connect();

        final StringBuilder page = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null){
            page.append(line + "\n");
        }

        System.out.println(page);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Proxy test");

            final Proxy proxy;

            if (args.length > 1) {
                final String proxyIp = args[0];
                final int proxyPort = Integer.parseInt(args[1]);

                System.out.println("Using IP=" + proxyIp + ", port=" + proxyPort);

                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
            } else {
                System.out.println("No proxy specified, continue with no proxy settings");
                proxy = null;
            }

            fetchPage(proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
