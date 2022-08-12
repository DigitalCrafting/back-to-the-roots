package org.digitalcrafting.eregold.domain.transactions;

import org.digitalcrafting.eregold.domain.accounts.CurrencyEnum;
import org.digitalcrafting.eregold.repository.transactions.TransactionEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TransactionsConverter {
    private TransactionsConverter() {
    }

    public static List<TransactionHistoryModel> toModelList(List<TransactionEntity> entityList) {
        List<TransactionHistoryModel> modelList = null;

        if (entityList != null && !entityList.isEmpty()) {
            modelList = entityList.stream()
                    .map(TransactionsConverter::toModel)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return modelList;
    }

    public static TransactionHistoryModel toModel(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        return TransactionHistoryModel.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .foreignAccountNumber(entity.getForeignAccountNumber())
                .description(entity.getDescription())
                .type(TransactionTypeEnum.valueOf(entity.getType()))
                .currency(CurrencyEnum.valueOf(entity.getCurrency()))
                .amount(entity.getAmount())
                .date(entity.getDate())
                .build();
    }

    public static TransactionEntity toSrcTransferEntity(TransactionModel transfer, Date date) {
        TransactionEntity entity = new TransactionEntity();

        entity.setAccountNumber(transfer.getSrcAccount());
        entity.setForeignAccountNumber(transfer.getDstAccount());
        entity.setDescription(transfer.getDescription());
        entity.setType(TransactionTypeEnum.TRANSFER.name());
        entity.setCurrency(transfer.getCurrency().name());
        entity.setAmount(transfer.getAmount().negate());
        entity.setDate(date);

        return entity;
    }

    public static TransactionEntity toDstTransferEntity(TransactionModel transfer, Date date) {
        TransactionEntity entity = new TransactionEntity();

        entity.setAccountNumber(transfer.getDstAccount());
        entity.setForeignAccountNumber(transfer.getSrcAccount());
        entity.setDescription(transfer.getDescription());
        entity.setType(TransactionTypeEnum.TRANSFER.name());
        entity.setCurrency(transfer.getCurrency().name());
        entity.setAmount(transfer.getAmount());
        entity.setDate(date);

        return entity;
    }


    public static TransactionEntity toDepositEntity(TransactionModel transfer, Date date) {
        TransactionEntity entity = new TransactionEntity();

        entity.setAccountNumber(transfer.getDstAccount());
        entity.setDescription(transfer.getDescription());
        entity.setType(TransactionTypeEnum.DEPOSIT.name());
        entity.setCurrency(transfer.getCurrency().name());
        entity.setAmount(transfer.getAmount());
        entity.setDate(date);

        return entity;
    }
}
