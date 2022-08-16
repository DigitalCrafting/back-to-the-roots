package org.digitalcrafting.eregold.http.api;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.apache.ibatis.session.SqlSession;
import org.digitalcrafting.eregold.http.core.DCAbstractHandler;
import org.digitalcrafting.eregold.http.core.MybatisSqlSessionFactory;
import org.digitalcrafting.eregold.http.repository.users.UserEntity;
import org.digitalcrafting.eregold.http.repository.users.UsersMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MybatisHandler extends DCAbstractHandler {
    @Override
    public void handleGet(HttpExchange exchange) throws IOException {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        UserEntity userEntity = null;
        try {
            UsersMapper usersMapper = sqlSession.getMapper(UsersMapper.class);
            userEntity = usersMapper.getByUserId("test");
        } finally {
            sqlSession.close();
        }

        String userJson = new Gson().toJson(userEntity);

        exchange.sendResponseHeaders(200, userJson.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(userJson.getBytes(StandardCharsets.UTF_8));
        os.flush();
        exchange.close();
    }
}
