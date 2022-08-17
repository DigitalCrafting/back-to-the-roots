package org.digitalcrafting.eregold.http.repository.users;

import org.apache.ibatis.session.SqlSession;
import org.digitalcrafting.eregold.http.core.MybatisSqlSessionFactory;

public class UsersEntityManager {
    public UserEntity getByUserId(String userId) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        UserEntity userEntity = null;
        try {
            UsersMapper usersMapper = sqlSession.getMapper(UsersMapper.class);
            userEntity = usersMapper.getByUserId(userId);
        } catch (Exception e) {
          // TODO log
        } finally {
            sqlSession.close();
        }
        return userEntity;
    }

    public void insert(UserEntity entity) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        try {
            UsersMapper usersMapper = sqlSession.getMapper(UsersMapper.class);
            usersMapper.insert(entity);
        } catch (Exception e) {
            // TODO log
        } finally {
            sqlSession.close();
        }
    }
}
