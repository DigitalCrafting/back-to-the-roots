package org.digitalcrafting.eregold.http.api.accounts;

import org.digitalcrafting.eregold.http.domain.accounts.AccountModel;
import org.digitalcrafting.eregold.http.domain.accounts.AccountsConverter;
import org.digitalcrafting.eregold.http.repository.accounts.AccountEntity;
import org.digitalcrafting.eregold.http.repository.accounts.AccountsEntityManager;

import java.util.List;

public class AccountsService {
    private final AccountsEntityManager accountsEntityManager = new AccountsEntityManager();

    public List<AccountModel> getAccounts(String customerId) {
        List<AccountEntity> entityList = accountsEntityManager.getAccountsForCustomer(customerId);
        return AccountsConverter.toModelList(entityList);
    }
}
