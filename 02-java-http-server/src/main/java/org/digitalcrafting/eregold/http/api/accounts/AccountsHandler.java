package org.digitalcrafting.eregold.http.api.accounts;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;
import org.digitalcrafting.eregold.http.core.session.DCUserContext;
import org.digitalcrafting.eregold.http.domain.accounts.AccountDetailsModel;
import org.digitalcrafting.eregold.http.domain.accounts.AccountModel;

import java.util.List;

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
        DCUserContext userContext = getUserContext(exchange);
        List<AccountModel> accountModelList = service.getAccounts(userContext.getCustomerId());
        sendResponse(exchange, accountModelList);
    }

    private void getAccountDetails(HttpExchange exchange, String accountNumber) {
        AccountDetailsModel accountDetailsModel = service.getAccountDetails(accountNumber);
        if (accountDetailsModel == null) {
            sendStatus(exchange, DCHttpStatus.NOT_FOUND);
        }

        sendResponse(exchange, accountDetailsModel);
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        CreateAccountRequest request = parseRequestBody(exchange, CreateAccountRequest.class);
        service.createAccount(request, getUserContext(exchange));
        sendStatus(exchange, DCHttpStatus.CREATED);
    }

    @Override
    public String availableMethods() {
        return "GET, POST, OPTIONS";
    }
}
