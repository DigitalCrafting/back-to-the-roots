package org.digitalcrafting.eregold.repository.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

// TODO maybe move EntityManager to domain
@Service
@RequiredArgsConstructor
public class AccountsEntityManager {
     private final AccountsMapper accountsMapper;

    public AccountEntity getByAccountNumber(String accountNumber) {
        return accountsMapper.getByAccountNumber(accountNumber);
    }

    public List<AccountEntity> getAccountsForCustomer(String customerId) {
        return accountsMapper.getAccountsForCustomer(customerId);
    }

    @Transactional
    public void createAccount(AccountEntity entity, String customerId) {
        accountsMapper.insertAccount(entity);
        accountsMapper.insertCustomerAccount(customerId, entity.getAccountNumber());
    }

    public void updateAccountBalance(String accountNumber, BigDecimal balance) {
        accountsMapper.updateAccountBalance(accountNumber, balance);
    }
}
