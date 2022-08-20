package org.digitalcrafting.eregold.http;

import com.sun.net.httpserver.HttpServer;
import org.digitalcrafting.eregold.http.api.MybatisHandler;
import org.digitalcrafting.eregold.http.api.TestHandler;
import org.digitalcrafting.eregold.http.api.accounts.AccountsHandler;
import org.digitalcrafting.eregold.http.api.login.LoginHandler;
import org.digitalcrafting.eregold.http.api.registration.RegistrationHandler;
import org.digitalcrafting.eregold.http.api.transactions.TransactionsHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JavaHttpApplication {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/test", new TestHandler());
        server.createContext("/db", new MybatisHandler());

        server.createContext("/registration", new RegistrationHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/v1/accounts", new AccountsHandler());
        server.createContext("/v1/transactions", new TransactionsHandler());

        server.setExecutor(null);
        server.start();
    }
}
