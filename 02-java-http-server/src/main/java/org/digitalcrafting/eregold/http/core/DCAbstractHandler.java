package org.digitalcrafting.eregold.http.core;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import lombok.extern.slf4j.Slf4j;
import org.digitalcrafting.eregold.http.core.consts.DCHttpHeader;
import org.digitalcrafting.eregold.http.core.consts.DCHttpMethod;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;
import org.digitalcrafting.eregold.http.core.session.DCSession;
import org.digitalcrafting.eregold.http.core.session.DCUserContext;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public abstract class DCAbstractHandler implements DCHandler {
    protected static Gson GSON = new Gson();

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

    protected void sendStatus(HttpExchange exchange, DCHttpStatus status) {
        defaultHeaders(exchange);
        try {
            exchange.sendResponseHeaders(status.value(), -1);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            exchange.close();
        }
    }

    protected void sendResponse(HttpExchange exchange, Object respObj) {
        sendResponse(exchange, respObj, DCHttpStatus.OK);
    }

    protected void sendResponse(HttpExchange exchange, Object respObj, DCHttpStatus status) {
        sendResponse(exchange, GSON.toJson(respObj), status);
    }

    private void sendResponse(HttpExchange exchange, String resp, DCHttpStatus status) {
        defaultHeaders(exchange);

        try {
            exchange.sendResponseHeaders(status.value(), resp.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(resp.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            exchange.close();
        }
    }

    protected void defaultHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Credentials", "true");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "authorization, content-type");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:4200");
        exchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        exchange.getResponseHeaders().set("Connection", "keep-alive");
    }

    protected <T> T parseRequestBody(HttpExchange exchange, Class<T> clazz) {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        T request = GSON.fromJson(isr, clazz);
        return request;
    }

    /* TODO refactor + logging */
    protected DCUserContext getUserContext(HttpExchange exchange) {
        Headers headers = exchange.getRequestHeaders();

        List<String> cookies = headers.get(DCHttpHeader.COOKIE);

        Optional<String> jsessionIdOpt = cookies
                .stream()
                .map(s -> s.split(";"))
                .flatMap(Stream::of)
                .filter(c -> c.trim().startsWith(DCHttpHeader.Values.JSESSIONID))
                .findFirst();

        if (jsessionIdOpt.isEmpty()) {
            return null;
        }

        String jsessionid = jsessionIdOpt.get().split("=")[1];

        return DCSession.INSTANCE.sessionMap.get(jsessionid);
    }
}
