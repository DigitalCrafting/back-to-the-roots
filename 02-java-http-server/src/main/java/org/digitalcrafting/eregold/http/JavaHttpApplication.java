package org.digitalcrafting.eregold.http;

import com.sun.net.httpserver.HttpServer;
import org.digitalcrafting.eregold.http.api.TestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JavaHttpApplication {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/test", new TestHandler());

        server.setExecutor(null);
        server.start();
    }
}
