package org.digitalcrafting.eregold.http.api.transactions;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;
import org.digitalcrafting.eregold.http.domain.transactions.TransactionModel;

import java.io.IOException;

public class TransactionsHandler extends DCAbstractHandler {
    private final String PATH_TRANSFER = "/transfer";
    private final String PATH_DEPOSIT = "/deposit";

    private final TransactionsService service = new TransactionsService();

    @Override
    public void handlePost(HttpExchange exchange) {
        if (exchange.getRequestURI().toString().contains(PATH_TRANSFER)) {
            transfer(exchange);
        } else if (exchange.getRequestURI().toString().contains(PATH_DEPOSIT)) {
            deposit(exchange);
        } else {
            sendStatus(exchange, DCHttpStatus.NOT_FOUND);
        }
    }

    private void transfer(HttpExchange exchange) {
        TransactionModel request = parseRequestBody(exchange, TransactionModel.class);
        service.transfer(request);
        sendStatus(exchange, DCHttpStatus.OK);
    }

    private void deposit(HttpExchange exchange) {
        TransactionModel request = parseRequestBody(exchange, TransactionModel.class);
        service.deposit(request);
        sendStatus(exchange, DCHttpStatus.OK);
    }

    @Override
    public void handleOptions(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Allow", "POST, OPTIONS");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        defaultHeaders(exchange);

        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
