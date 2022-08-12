package org.digitalcrafting.eregold.api.transactions;

import org.digitalcrafting.eregold.domain.accounts.CurrencyEnum;
import org.digitalcrafting.eregold.domain.transactions.TransactionModel;
import org.digitalcrafting.eregold.domain.transactions.TransactionTypeEnum;
import org.digitalcrafting.eregold.domain.transactions.TransactionsConverter;
import org.digitalcrafting.eregold.repository.accounts.AccountEntity;
import org.digitalcrafting.eregold.repository.accounts.AccountsEntityManager;
import org.digitalcrafting.eregold.repository.transactions.TransactionEntity;
import org.digitalcrafting.eregold.repository.transactions.TransactionsEntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionsControllerServiceTest {
    @Autowired
    private TransactionsControllerService service;

    @MockBean
    private TransactionsEntityManager transactionsEntityManager;

    @MockBean
    private AccountsEntityManager accountsEntityManager;

    private TransactionModel mockTransferRequest = TransactionModel.builder()
            .amount(BigDecimal.TEN)
            .currency(CurrencyEnum.GLD)
            .description("Test transfer")
            .srcAccount("12ERGD12345")
            .dstAccount("12ERGD67890")
            .build();
    private TransactionEntity mockSrcTransferEntity = TransactionEntity.builder()
            .amount(BigDecimal.TEN.negate())
            .currency(CurrencyEnum.GLD.name())
            .type(TransactionTypeEnum.TRANSFER.name())
            .description("Test transfer")
            .accountNumber("12ERGD12345")
            .build();
    private TransactionEntity mockDstTransferEntity = TransactionEntity.builder()
            .amount(BigDecimal.TEN)
            .currency(CurrencyEnum.GLD.name())
            .type(TransactionTypeEnum.TRANSFER.name())
            .description("Test transfer")
            .accountNumber("12ERGD67890")
            .build();
    private AccountEntity mockSrcAccountEntity = AccountEntity.builder()
            .accountName("Src account")
            .accountNumber("12ERGD12345")
            .currentBalance(new BigDecimal("1000"))
            .currency(CurrencyEnum.GLD.name())
            .build();
    private AccountEntity mockDstAccountEntity = AccountEntity.builder()
            .accountName("Dst account")
            .accountNumber("12ERGD67890")
            .currentBalance(new BigDecimal("1000"))
            .currency(CurrencyEnum.GLD.name())
            .build();

    private TransactionModel mockDepositRequest = TransactionModel.builder()
            .amount(BigDecimal.TEN)
            .currency(CurrencyEnum.GLD)
            .description("Deposit")
            .dstAccount("12ERGD67890")
            .build();
    private TransactionEntity mockDepositEntity = TransactionEntity.builder()
            .amount(BigDecimal.TEN)
            .currency(CurrencyEnum.GLD.name())
            .type(TransactionTypeEnum.DEPOSIT.name())
            .description("Deposit")
            .accountNumber("12ERGD67890")
            .build();
    private Date currentDate = new Date();

    @BeforeAll
    public void setup() {
        mockSrcTransferEntity.setDate(currentDate);
        mockDstTransferEntity.setDate(currentDate);
        mockDepositEntity.setDate(currentDate);
    }

    @Test
    public void should_createTransactionsControllerService() {
        assertNotNull(service);
    }

    @Test
    public void should_createOneTransaction_andUpdateBalance() {
        when(accountsEntityManager.getByAccountNumber("12ERGD12345")).thenReturn(mockSrcAccountEntity);
        when(accountsEntityManager.getByAccountNumber("12ERGD67890")).thenReturn(null);
        try (MockedStatic<TransactionsConverter> converter = Mockito.mockStatic(TransactionsConverter.class)) {
            converter.when(() -> TransactionsConverter.toSrcTransferEntity(any(), any())).thenReturn(mockSrcTransferEntity);
            converter.when(() -> TransactionsConverter.toDstTransferEntity(any(), any())).thenReturn(mockDstTransferEntity);
        }

        service.transfer(mockTransferRequest);

        verify(transactionsEntityManager, times(1)).insert((TransactionEntity) any());
        verify(accountsEntityManager, times(1)).updateAccountBalance(any(), any());
    }

    @Test
    public void should_createTwoTransactions_andUpdateBalance() {
        when(accountsEntityManager.getByAccountNumber("12ERGD12345")).thenReturn(mockSrcAccountEntity);
        when(accountsEntityManager.getByAccountNumber("12ERGD67890")).thenReturn(mockDstAccountEntity);
        try (MockedStatic<TransactionsConverter> converter = Mockito.mockStatic(TransactionsConverter.class)) {
            converter.when(() -> TransactionsConverter.toSrcTransferEntity(any(), any())).thenReturn(mockSrcTransferEntity);
            converter.when(() -> TransactionsConverter.toDstTransferEntity(any(), any())).thenReturn(mockDstTransferEntity);
        }

        service.transfer(mockTransferRequest);

        verify(transactionsEntityManager, times(1)).insert((List<TransactionEntity>) any());
        verify(accountsEntityManager, times(2)).updateAccountBalance(any(), any());
    }

    @Test
    public void should_createDepositTransaction_andUpdateBalance() {
        when(accountsEntityManager.getByAccountNumber("12ERGD12345")).thenReturn(mockSrcAccountEntity);
        when(accountsEntityManager.getByAccountNumber("12ERGD67890")).thenReturn(mockDstAccountEntity);
        try (MockedStatic<TransactionsConverter> converter = Mockito.mockStatic(TransactionsConverter.class)) {
            converter.when(() -> TransactionsConverter.toDepositEntity(any(), any())).thenReturn(mockDepositEntity);
        }

        service.deposit(mockDepositRequest);

        verify(transactionsEntityManager, times(1)).insert((TransactionEntity) any());
        verify(accountsEntityManager, times(1)).updateAccountBalance(any(), any());
    }
}