package org.digitalcrafting.eregold.api.accounts;

import lombok.RequiredArgsConstructor;
import org.digitalcrafting.eregold.authentication.EregoldSessionContext;
import org.digitalcrafting.eregold.domain.accounts.AccountDetailsModel;
import org.digitalcrafting.eregold.domain.accounts.AccountModel;
import org.digitalcrafting.eregold.domain.accounts.AccountsConverter;
import org.digitalcrafting.eregold.domain.transactions.TransactionHistoryModel;
import org.digitalcrafting.eregold.domain.transactions.TransactionsConverter;
import org.digitalcrafting.eregold.repository.accounts.AccountEntity;
import org.digitalcrafting.eregold.repository.accounts.AccountsEntityManager;
import org.digitalcrafting.eregold.repository.transactions.TransactionEntity;
import org.digitalcrafting.eregold.repository.transactions.TransactionsEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountsControllerService {

    private final TransactionsEntityManager transactionsEntityManager;
    private final AccountsEntityManager accountsEntityManager;
    private final EregoldSessionContext sessionContext;

    public List<AccountModel> getAccounts() {
        List<AccountEntity> entityList = accountsEntityManager.getAccountsForCustomer(sessionContext.getCustomerId());
        return AccountsConverter.toModelList(entityList);
    }

    public void createAccount(CreateAccountRequest request) {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountNumber(generateAccountNumber())
                .accountName(request.getAccountName())
                .type(request.getAccountType().name())
                .currency(request.getCurrency().name())
                .currentBalance(BigDecimal.ZERO)
                .build();
        accountsEntityManager.createAccount(accountEntity, sessionContext.getCustomerId());
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
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }

        AccountDetailsModel detailsModel = AccountsConverter.toDetailsModel(entity);
        List<TransactionEntity> transactionEntityList = transactionsEntityManager.getByAccountNumber(accountNumber);
        List<TransactionHistoryModel> transactionHistoryModelList = TransactionsConverter.toModelList(transactionEntityList);
        transactionHistoryModelList.sort(new TransactionHistoryModel.DateDescendingComparator());
        detailsModel.setTransactionsList(transactionHistoryModelList);

        return detailsModel;
    }
}
