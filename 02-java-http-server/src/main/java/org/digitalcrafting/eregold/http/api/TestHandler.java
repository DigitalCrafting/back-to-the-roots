package org.digitalcrafting.eregold.http.api;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TestHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) throws IOException {
        String resp = "Hello world!\n";
        exchange.sendResponseHeaders(200, resp.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(resp.getBytes(StandardCharsets.UTF_8));
        os.flush();
        exchange.close();
    }

    @Override
    public void handleOptions(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Allow", "GET");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
