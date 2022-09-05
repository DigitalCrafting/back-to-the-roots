package org.digitalcrafting.eregold.http;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.digitalcrafting.eregold.http.api.MybatisHandler;
import org.digitalcrafting.eregold.http.api.TestHandler;
import org.digitalcrafting.eregold.http.api.accounts.AccountsHandler;
import org.digitalcrafting.eregold.http.api.login.LoginHandler;
import org.digitalcrafting.eregold.http.api.registration.RegistrationHandler;
import org.digitalcrafting.eregold.http.api.transactions.TransactionsHandler;
import org.digitalcrafting.eregold.http.core.DCAuthenticator;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JavaHttpApplication {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        HttpContext testContext = server.createContext("/test", new TestHandler());
        server.createContext("/db", new MybatisHandler());

        server.createContext("/registration", new RegistrationHandler());
        server.createContext("/login", new LoginHandler());
        HttpContext accountsContext = server.createContext("/v1/accounts", new AccountsHandler());
        HttpContext transactionsContext = server.createContext("/v1/transactions", new TransactionsHandler());

        DCAuthenticator authenticator = new DCAuthenticator();

//        testContext.setAuthenticator(authenticator);

        accountsContext.setAuthenticator(authenticator);
//        transactionsContext.setAuthenticator(authenticator);

        server.setExecutor(null);
        server.start();
    }
}
