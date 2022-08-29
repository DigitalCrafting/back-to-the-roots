package org.digitalcrafting.eregold.http.api.login;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import lombok.extern.slf4j.Slf4j;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.consts.DCHttpHeader;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;
import org.digitalcrafting.eregold.http.core.security.BCrypt;
import org.digitalcrafting.eregold.http.core.security.JWTUtils;
import org.digitalcrafting.eregold.http.core.session.DCSession;
import org.digitalcrafting.eregold.http.core.session.DCUserContext;
import org.digitalcrafting.eregold.http.repository.users.UserEntity;
import org.digitalcrafting.eregold.http.repository.users.UsersEntityManager;

import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public class LoginHandler extends DCAbstractHandler {
    private UsersEntityManager usersEntityManager;

    public LoginHandler() {
        usersEntityManager = new UsersEntityManager();
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        String resp = "Login working!\n";
        sendResponse(exchange, resp);
    }

    @Override
    public void handlePost(HttpExchange exchange) {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        LoginRequest request = GSON.fromJson(isr, LoginRequest.class);


        UserEntity userEntity = usersEntityManager.getByUserId(request.getUserId());
        if (userEntity == null) {
            sendStatus(exchange, DCHttpStatus.NOT_FOUND);
            return;
        }

        byte[] passwordb = StandardCharsets.UTF_8.encode(CharBuffer.wrap(request.getPassword())).array();
        if (BCrypt.checkpw(passwordb, userEntity.getPasswordHash())) {
            String token = JWTUtils.generateAccessToken(userEntity.getUserId());
            String jsessionid = UUID.randomUUID().toString();

            DCUserContext userContext = new DCUserContext();
            userContext.setUserId(userEntity.getUserId());
            userContext.setToken(token);

            DCSession.INSTANCE.sessionMap.put(jsessionid, userContext);

            Headers headers = exchange.getResponseHeaders();
            headers.set(DCHttpHeader.SET_COOKIE, String.format("%s=%s", DCHttpHeader.Values.JSESSIONID, jsessionid));
            headers.set(DCHttpHeader.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate");

            LoginResponse response = new LoginResponse(token);
            sendResponse(exchange, response);
        }

        sendStatus(exchange, DCHttpStatus.NOT_FOUND);
    }
}
