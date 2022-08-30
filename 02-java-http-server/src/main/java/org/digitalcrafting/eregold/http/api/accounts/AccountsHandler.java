package org.digitalcrafting.eregold.http.api.accounts;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

import java.io.IOException;

public class AccountsHandler extends DCAbstractHandler {
    private final AccountsService service = new AccountsService();

    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Accounts working!\n";
        sendResponse(exchange, resp);
    }

    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        super.handlePost(exchange);
    }
}
