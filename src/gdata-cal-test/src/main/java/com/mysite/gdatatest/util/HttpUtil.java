package com.mysite.gdatatest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Http utilities.
 */
public final class HttpUtil {
    public static final String ENCODING = "UTF-8";


    public static final List<String> makeRequest(String hostname, int port, byte[] buffer) throws IOException {
        final InetAddress inetAddress = InetAddress.getByName(hostname);
        final Socket socket = new Socket(inetAddress, port);

        try {
            // write post content
            socket.getOutputStream().write(buffer);
            socket.shutdownOutput();

            // read post content
            final List<String> response = new ArrayList<String>();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING));

            try {
                for (;;) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }

                    response.add(line);
                }

                return response;
            } finally {
                reader.close();
            }
        } finally {
            socket.close();
        }
    }

    /** Hidden ctor */
    private HttpUtil() {}
}
