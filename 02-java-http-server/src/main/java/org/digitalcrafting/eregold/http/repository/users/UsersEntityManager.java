package org.digitalcrafting.eregold.http.repository.users;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.digitalcrafting.eregold.http.core.MybatisSqlSessionFactory;

@Slf4j
public class UsersEntityManager {
    public UserEntity getByUserId(String userId) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        UserEntity userEntity = null;
        try {
            UsersMapper usersMapper = sqlSession.getMapper(UsersMapper.class);
            userEntity = usersMapper.getByUserId(userId);
        } catch (Exception e) {
            log.info(e.getMessage());
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
            sqlSession.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }
}
