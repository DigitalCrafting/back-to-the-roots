package org.digitalcrafting.eregold.http.core;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class DCAbstractHandler implements DCHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (DCHttpMethod.GET.equals(exchange.getRequestMethod())) {
            this.handleGet(exchange);
        } else if (DCHttpMethod.POST.equals(exchange.getRequestMethod())) {
            this.handlePost(exchange);
        } else if (DCHttpMethod.PUT.equals(exchange.getRequestMethod())) {
            this.handlePut(exchange);
        } else if (DCHttpMethod.PATCH.equals(exchange.getRequestMethod())) {
            this.handlePatch(exchange);
        } else if (DCHttpMethod.DELETE.equals(exchange.getRequestMethod())) {
            this.handleDelete(exchange);
        } else if (DCHttpMethod.OPTIONS.equals(exchange.getRequestMethod())) {
            this.handleOptions(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}
