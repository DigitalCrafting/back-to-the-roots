package org.digitalcrafting.eregold.http.repository.transactions;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.digitalcrafting.eregold.http.core.MybatisSqlSessionFactory;

import java.util.List;

@Slf4j
public class TransactionsEntityManager {
    public Long getNextId() {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        Long nextId = null;
        try {
            TransactionsMapper mapper = sqlSession.getMapper(TransactionsMapper.class);
            nextId = mapper.getNextId();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
            log.info("Successfully called database");
        }

        return nextId;
    }

    public void insert(TransactionEntity entity) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        try {
            TransactionsMapper mapper = sqlSession.getMapper(TransactionsMapper.class);
            mapper.insert(entity);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
            log.info("Successfully called database");
        }
    }

    public List<TransactionEntity> getByAccountNumber(String accountNumber) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        List<TransactionEntity> entities = null;

        try {
            TransactionsMapper mapper = sqlSession.getMapper(TransactionsMapper.class);
            entities = mapper.getByAccountNumber(accountNumber);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
            log.info("Successfully called database");
        }

        return entities;
    }
}
