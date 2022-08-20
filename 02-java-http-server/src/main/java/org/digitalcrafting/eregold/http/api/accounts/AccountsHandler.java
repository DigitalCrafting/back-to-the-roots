package org.digitalcrafting.eregold.http.api.accounts;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

public class AccountsHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Accounts working!\n";
        sendResponse(exchange, resp);
    }
}
