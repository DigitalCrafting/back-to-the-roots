package org.digitalcrafting.eregold.domain.transactions;

import org.digitalcrafting.eregold.domain.accounts.CurrencyEnum;
import org.digitalcrafting.eregold.repository.transactions.TransactionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class TransactionsConverterTest {
    private TransactionModel mockTransferRequest = TransactionModel.builder()
            .amount(BigDecimal.TEN)
            .currency(CurrencyEnum.GLD)
            .description("Test transfer")
            .srcAccount("12ERGD12345")
            .dstAccount("12ERGD67890")
            .build();
    private TransactionModel mockDepositRequest = TransactionModel.builder()
            .amount(BigDecimal.TEN)
            .currency(CurrencyEnum.GLD)
            .description("Deposit")
            .dstAccount("12ERGD67890")
            .build();
    private Date currentDate = new Date();

    @Test
    void should_returnNull_when_calledWithNull() {
        // Given
        List<TransactionEntity> entityList = null;

        // When
        List<TransactionHistoryModel> modelList = TransactionsConverter.toModelList(entityList);

        // Then
        Assertions.assertNull(modelList);
    }

    @Test
    void should_returnNull_when_calledWithEmptyList() {
        // Given
        List<TransactionEntity> entityList = Collections.emptyList();

        // When
        List<TransactionHistoryModel> modelList = TransactionsConverter.toModelList(entityList);

        // Then
        Assertions.assertNull(modelList);
    }

    @Test
    void should_returnEmptyList_when_calledWithListOfNull() {
        // Given
        List<TransactionEntity> entityList = new ArrayList<>();
        entityList.add(null);

        // When
        List<TransactionHistoryModel> modelList = TransactionsConverter.toModelList(entityList);

        // Then
        Assertions.assertNotNull(modelList);
        Assertions.assertTrue(modelList.isEmpty());
    }

    @Test
    void should_returnModelList_when_calledWithEntityList() {
        // Given
        Date transactionDate = new Date();
        List<TransactionEntity> entityList = List.of(
                TransactionEntity.builder()
                        .accountNumber("12ERGD12345")
                        .foreignAccountNumber("12ERGD67890")
                        .currency(CurrencyEnum.GLD.name())
                        .amount(BigDecimal.TEN)
                        .date(transactionDate)
                        .id(1l)
                        .type(TransactionTypeEnum.TRANSFER.name())
                        .description("Test")
                        .build()
        );

        // When
        List<TransactionHistoryModel> modelList = TransactionsConverter.toModelList(entityList);

        // Then
        Assertions.assertNotNull(modelList);
        Assertions.assertEquals(1, modelList.size());

        Assertions.assertEquals(1l, modelList.get(0).getId());
        Assertions.assertEquals("12ERGD12345", modelList.get(0).getAccountNumber());
        Assertions.assertEquals("12ERGD67890", modelList.get(0).getForeignAccountNumber());
        Assertions.assertEquals("Test", modelList.get(0).getDescription());
        Assertions.assertEquals(TransactionTypeEnum.TRANSFER, modelList.get(0).getType());
        Assertions.assertEquals(CurrencyEnum.GLD, modelList.get(0).getCurrency());
        Assertions.assertEquals(BigDecimal.TEN, modelList.get(0).getAmount());
        Assertions.assertEquals(transactionDate, modelList.get(0).getDate());

    }

    @Test
    void should_convertToSrcTransferEntity() {
        // When
        TransactionEntity srcTransactionEntity = TransactionsConverter.toSrcTransferEntity(mockTransferRequest, currentDate);

        // Then
        Assertions.assertNotNull(srcTransactionEntity);
        Assertions.assertEquals(currentDate, srcTransactionEntity.getDate());
        Assertions.assertEquals(BigDecimal.TEN.negate(), srcTransactionEntity.getAmount());
        Assertions.assertEquals(CurrencyEnum.GLD.name(), srcTransactionEntity.getCurrency());
        Assertions.assertEquals(TransactionTypeEnum.TRANSFER.name(), srcTransactionEntity.getType());
        Assertions.assertEquals("Test transfer", srcTransactionEntity.getDescription());
        Assertions.assertEquals("12ERGD12345", srcTransactionEntity.getAccountNumber());
        Assertions.assertEquals("12ERGD67890", srcTransactionEntity.getForeignAccountNumber());
    }

    @Test
    void should_convertToDstTransferEntity() {
        // When
        TransactionEntity dstTransactionEntity = TransactionsConverter.toDstTransferEntity(mockTransferRequest, currentDate);

        // Then
        Assertions.assertNotNull(dstTransactionEntity);
        Assertions.assertEquals(currentDate, dstTransactionEntity.getDate());
        Assertions.assertEquals(BigDecimal.TEN, dstTransactionEntity.getAmount());
        Assertions.assertEquals(CurrencyEnum.GLD.name(), dstTransactionEntity.getCurrency());
        Assertions.assertEquals(TransactionTypeEnum.TRANSFER.name(), dstTransactionEntity.getType());
        Assertions.assertEquals("Test transfer", dstTransactionEntity.getDescription());
        Assertions.assertEquals("12ERGD12345", dstTransactionEntity.getForeignAccountNumber());
        Assertions.assertEquals("12ERGD67890", dstTransactionEntity.getAccountNumber());
    }

    @Test
    void should_convertToDepositEntity() {
        // When
        TransactionEntity depositEntity = TransactionsConverter.toDepositEntity(mockDepositRequest, currentDate);

        // Then
        Assertions.assertNotNull(depositEntity);
        Assertions.assertEquals(currentDate, depositEntity.getDate());
        Assertions.assertEquals(BigDecimal.TEN, depositEntity.getAmount());
        Assertions.assertEquals(CurrencyEnum.GLD.name(), depositEntity.getCurrency());
        Assertions.assertEquals(TransactionTypeEnum.DEPOSIT.name(), depositEntity.getType());
        Assertions.assertEquals("Deposit", depositEntity.getDescription());
        Assertions.assertEquals("12ERGD67890", depositEntity.getAccountNumber());
    }
}