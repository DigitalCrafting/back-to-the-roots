package org.digitalcrafting.eregold.http.api.login;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

public class LoginHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Login working!\n";
        sendResponse(exchange, resp);
    }
}
