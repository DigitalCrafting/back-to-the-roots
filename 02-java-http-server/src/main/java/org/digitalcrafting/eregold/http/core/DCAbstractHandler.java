package org.digitalcrafting.eregold.http.core;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class DCAbstractHandler implements DCHandler {
    private static Gson GSON = new Gson();

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

    protected void sendResponse(HttpExchange exchange, Object respObj) {
        sendResponse(exchange, GSON.toJson(respObj));
    }

    protected void sendResponse(HttpExchange exchange, String resp) {
        try {
            exchange.sendResponseHeaders(200, resp.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(resp.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            exchange.close();
        }
    }
}
