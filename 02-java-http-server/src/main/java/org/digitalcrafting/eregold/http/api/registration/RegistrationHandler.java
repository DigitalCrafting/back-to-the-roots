package org.digitalcrafting.eregold.http.api.registration;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

public class RegistrationHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Registration working!\n";
        sendResponse(exchange, resp);
    }
}