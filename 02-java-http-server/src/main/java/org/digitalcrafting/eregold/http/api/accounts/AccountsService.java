package org.digitalcrafting.eregold.http.api.accounts;

import org.digitalcrafting.eregold.http.core.session.DCUserContext;
import org.digitalcrafting.eregold.http.domain.accounts.AccountDetailsModel;
import org.digitalcrafting.eregold.http.domain.accounts.AccountModel;
import org.digitalcrafting.eregold.http.domain.accounts.AccountsConverter;
import org.digitalcrafting.eregold.http.domain.transactions.TransactionHistoryModel;
import org.digitalcrafting.eregold.http.domain.transactions.TransactionsConverter;
import org.digitalcrafting.eregold.http.repository.accounts.AccountEntity;
import org.digitalcrafting.eregold.http.repository.accounts.AccountsEntityManager;
import org.digitalcrafting.eregold.http.repository.transactions.TransactionEntity;
import org.digitalcrafting.eregold.http.repository.transactions.TransactionsEntityManager;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class AccountsService {
    private final AccountsEntityManager accountsEntityManager = new AccountsEntityManager();
    private final TransactionsEntityManager transactionsEntityManager = new TransactionsEntityManager();

    public List<AccountModel> getAccounts(String customerId) {
        List<AccountEntity> entityList = accountsEntityManager.getAccountsForCustomer(customerId);
        return AccountsConverter.toModelList(entityList);
    }

    public void createAccount(CreateAccountRequest request, DCUserContext userContext) {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountNumber(generateAccountNumber())
                .accountName(request.getAccountName())
                .type(request.getAccountType().name())
                .currency(request.getCurrency().name())
                .currentBalance(BigDecimal.ZERO)
                .build();
        accountsEntityManager.createAccount(accountEntity, userContext.getCustomerId());
    }

    private String generateAccountNumber() {
        StringBuilder number;
        do {
            number = new StringBuilder("12ERGD");
            // toEpochMilli should return number of length 13 at the time of writing this code, so we want to limit ourselves to 12
            number.append(String.valueOf(Instant.now().toEpochMilli()).substring(0, 12));
        } while (accountsEntityManager.getByAccountNumber(number.toString()) != null);
        return number.toString();
    }

    public AccountDetailsModel getAccountDetails(String accountNumber) {
        AccountEntity entity = accountsEntityManager.getByAccountNumber(accountNumber);

        if (entity == null) {
            return null;
        }

        AccountDetailsModel detailsModel = AccountsConverter.toDetailsModel(entity);
        List<TransactionEntity> transactionEntityList = transactionsEntityManager.getByAccountNumber(accountNumber);
        List<TransactionHistoryModel> transactionHistoryModelList = TransactionsConverter.toModelList(transactionEntityList);
        transactionHistoryModelList.sort(new TransactionHistoryModel.DateDescendingComparator());
        detailsModel.setTransactionsList(transactionHistoryModelList);

        return detailsModel;
    }
}
