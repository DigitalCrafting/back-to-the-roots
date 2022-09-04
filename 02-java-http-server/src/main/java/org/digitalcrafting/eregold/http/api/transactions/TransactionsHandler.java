package org.digitalcrafting.eregold.http.api.transactions;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;

import java.io.IOException;

public class TransactionsHandler extends DCAbstractHandler {
    private final String PATH_TRANSFER = "/transfer";
    private final String PATH_DEPOSIT = "/deposit";

    private final TransactionsService service = new TransactionsService();

    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Transactions working!\n";
        sendResponse(exchange, resp);
    }

    @Override
    public void handlePost(HttpExchange exchange) throws IOException {
        if (exchange.getRequestURI().toString().contains(PATH_TRANSFER)) {
            transfer(exchange);
        } else if (exchange.getRequestURI().toString().contains(PATH_DEPOSIT)) {
            deposit(exchange);
        } else {
            sendStatus(exchange, DCHttpStatus.NOT_FOUND);
        }
    }

    private void transfer(HttpExchange exchange) {
        String resp = "Making transfer!\n";
        sendResponse(exchange, resp);
    }

    private void deposit(HttpExchange exchange) {
        String resp = "Making deposit!\n";
        sendResponse(exchange, resp);
    }
}
