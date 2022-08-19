package org.digitalcrafting.eregold.http.repository.accounts;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.digitalcrafting.eregold.http.core.MybatisSqlSessionFactory;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class AccountsEntityManager {
    public AccountEntity getByAccountNumber(String accountNumber) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        AccountEntity entity = null;
        try {
            AccountsMapper mapper = sqlSession.getMapper(AccountsMapper.class);
            entity = mapper.getByAccountNumber(accountNumber);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }

        return entity;
    }

    public List<AccountEntity> getAccountsForCustomer(String customerId) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();
        List<AccountEntity> customerAccounts = null;
        try {
            AccountsMapper mapper = sqlSession.getMapper(AccountsMapper.class);
            customerAccounts = mapper.getAccountsForCustomer(customerId);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }

        return customerAccounts;
    }

    public void insertAccount(AccountEntity entity) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();

        try {
            AccountsMapper mapper = sqlSession.getMapper(AccountsMapper.class);
            mapper.insertAccount(entity);
            sqlSession.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }

    public void insertCustomerAccount(String customerId, String accountNumber) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();

        try {
            AccountsMapper mapper = sqlSession.getMapper(AccountsMapper.class);
            mapper.insertCustomerAccount(customerId, accountNumber);
            sqlSession.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }

    public void updateAccountBalance(String accountNumber, BigDecimal balance) {
        SqlSession sqlSession = MybatisSqlSessionFactory.get().openSession();

        try {
            AccountsMapper mapper = sqlSession.getMapper(AccountsMapper.class);
            mapper.updateAccountBalance(accountNumber, balance);
            sqlSession.commit();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }
}
