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
        }

        return nextId;
    }

    public void insert(List<TransactionEntity> transactions) {
        if (transactions != null && !transactions.isEmpty()) {
            SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
            try {
                TransactionsMapper mapper = sqlSession.getMapper(TransactionsMapper.class);
                Long transactionId = getNextId();
                transactions.forEach(transaction -> {
                    transaction.setId(transactionId);
                    mapper.insert(transaction);
                });
                sqlSession.commit();
            } catch (Exception e) {
                log.info(e.getMessage());
            } finally {
                sqlSession.close();
            }
        }
    }

    public void insert(TransactionEntity entity) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        try {
            TransactionsMapper mapper = sqlSession.getMapper(TransactionsMapper.class);
            Long transactionId = getNextId();
            entity.setId(transactionId);
            mapper.insert(entity);
            sqlSession.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
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
        }

        return entities;
    }
}
