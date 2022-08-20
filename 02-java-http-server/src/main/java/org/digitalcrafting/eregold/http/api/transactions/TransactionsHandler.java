package org.digitalcrafting.eregold.http.api.transactions;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;

public class TransactionsHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Transactions working!\n";
        sendResponse(exchange, resp);
    }
}
