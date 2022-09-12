package org.digitalcrafting.eregold.http.api.registration;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;

public class RegistrationHandler extends DCAbstractHandler {
    private final RegistrationService service = new RegistrationService();

    @Override
    public void handlePost(HttpExchange exchange) {
        RegisterRequest request = parseRequestBody(exchange, RegisterRequest.class);
        RegisterResponse response = service.register(request);
        sendResponse(exchange, response, DCHttpStatus.CREATED);
    }

    @Override
    public String availableMethods() {
        return "POST, OPTIONS";
    }
}
