package org.digitalcrafting.eregold.http.api.transactions;

import org.digitalcrafting.eregold.http.repository.accounts.AccountsEntityManager;
import org.digitalcrafting.eregold.http.repository.transactions.TransactionsEntityManager;

public class TransactionsService {
    private final TransactionsEntityManager transactionsEntityManager = new TransactionsEntityManager();
    private final AccountsEntityManager accountsEntityManager = new AccountsEntityManager();
}
