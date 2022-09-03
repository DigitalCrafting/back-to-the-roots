package org.digitalcrafting.eregold.http.api.accounts;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

import java.io.IOException;

public class AccountsHandler extends DCAbstractHandler {
    private final AccountsService service = new AccountsService();

    @Override
    public void handleGet(HttpExchange exchange) {
        String[] uriParts = exchange.getRequestURI().getPath().split("/");
        if (uriParts.length == 3) { // first one is empty due to leading slash
            getAccounts(exchange);
        } else {
            String accountNumber = uriParts[3];
            getAccountDetails(exchange, accountNumber);
        }
    }

    private void getAccounts(HttpExchange exchange) {
        String resp = "Returning accounts!\n";
        sendResponse(exchange, resp);
    }

    private void getAccountDetails(HttpExchange exchange, String accountNumber) {
        String resp = "Returning account details "  + accountNumber + "!\n";
        sendResponse(exchange, resp);
    }

    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        super.handlePost(exchange);
    }
}
