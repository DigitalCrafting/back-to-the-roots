package org.digitalcrafting.eregold.http.api.registration;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;

import java.io.IOException;

public class RegistrationHandler extends DCAbstractHandler {
    private final RegistrationService service = new RegistrationService();

    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Registration working!\n";
        sendResponse(exchange, resp);
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        RegisterRequest request = parseRequestBody(exchange, RegisterRequest.class);
        RegisterResponse response = service.register(request);
        sendResponse(exchange, response, DCHttpStatus.CREATED);
    }

    @Override
    public void handleOptions(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Allow", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        defaultHeaders(exchange);

        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
