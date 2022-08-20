package org.digitalcrafting.eregold.http.api;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

import java.io.IOException;

public class TestHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Hello world!\n";
        sendResponse(exchange, resp);
    }

    @Override
    public void handleOptions(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Allow", "GET");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
