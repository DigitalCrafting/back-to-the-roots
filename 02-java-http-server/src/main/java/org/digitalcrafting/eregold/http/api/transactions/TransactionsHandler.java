package org.digitalcrafting.eregold.http.api.transactions;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;
import org.digitalcrafting.eregold.http.domain.transactions.TransactionModel;

import java.io.IOException;
import java.io.InputStreamReader;

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
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        TransactionModel request = GSON.fromJson(isr, TransactionModel.class);
        service.transfer(request);
        sendStatus(exchange, DCHttpStatus.OK);
    }

    private void deposit(HttpExchange exchange) {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        TransactionModel request = GSON.fromJson(isr, TransactionModel.class);
        service.deposit(request);
        sendStatus(exchange, DCHttpStatus.OK);
    }

    @Override
    public void handleOptions(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Allow", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        defaultHeaders(exchange);

        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
