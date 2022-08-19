package org.digitalcrafting.eregold.http.repository.customers;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.digitalcrafting.eregold.http.core.MybatisSqlSessionFactory;

@Slf4j
public class CustomersEntityManager {
    public CustomerEntity getByEmail(String email) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        CustomerEntity entity = null;

        try {
            CustomersMapper mapper = sqlSession.getMapper(CustomersMapper.class);
            entity = mapper.getByEmail(email);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }

        return entity;
    }

    public CustomerEntity getByCustomerId(String customerId) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        CustomerEntity entity = null;

        try {
            CustomersMapper mapper = sqlSession.getMapper(CustomersMapper.class);
            entity = mapper.getByCustomerId(customerId);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }

        return entity;
    }

    public void insert(CustomerEntity entity) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();

        try {
            CustomersMapper mapper = sqlSession.getMapper(CustomersMapper.class);
            mapper.insert(entity);
            sqlSession.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }
}
