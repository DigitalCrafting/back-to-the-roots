package org.digitalcrafting.eregold.http.api;

import com.sun.net.httpserver.HttpExchange;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.repository.users.UserEntity;
import org.digitalcrafting.eregold.http.repository.users.UsersEntityManager;

public class MybatisHandler extends DCAbstractHandler {
    UsersEntityManager usersEntityManager;

    public MybatisHandler() {
        usersEntityManager = new UsersEntityManager();
    }

    @Override
    public void handleGet(HttpExchange exchange) {
        UserEntity userEntity = usersEntityManager.getByUserId("test");
        sendResponse(exchange, userEntity);
    }
}
