package org.digitalcrafting.eregold.http.api.login;

import com.sun.net.httpserver.HttpExchange;
import lombok.extern.slf4j.Slf4j;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class LoginHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Login working!\n";
        sendResponse(exchange, resp);
    }

    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        LoginRequest request = GSON.fromJson(isr, LoginRequest.class);
        
        LoginResponse response = new LoginResponse("very_secret_token");
        sendResponse(exchange, response);
    }
}
