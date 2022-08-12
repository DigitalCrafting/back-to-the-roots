package org.digitalcrafting.eregold.domain.accounts;

import org.digitalcrafting.eregold.repository.accounts.AccountEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class AccountsConverter {
    private AccountsConverter() {}

    public static List<AccountModel> toModelList(List<AccountEntity> entityList) {
        List<AccountModel> accountModelList = null;

        if (entityList != null && !entityList.isEmpty()) {
            accountModelList = entityList.stream()
                    .map(AccountsConverter::toModel)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return accountModelList;
    }

    public static AccountModel toModel(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return AccountModel.builder()
                .accountNumber(entity.getAccountNumber())
                .accountName(entity.getAccountName())
                .currency(CurrencyEnum.valueOf(entity.getCurrency()))
                .type(AccountTypeEnum.valueOf(entity.getType()))
                .currentBalance(entity.getCurrentBalance())
                .build();
    }

    public static AccountDetailsModel toDetailsModel(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        return AccountDetailsModel.builder()
                .accountNumber(entity.getAccountNumber())
                .accountName(entity.getAccountName())
                .currentBalance(entity.getCurrentBalance())
                .currency(CurrencyEnum.valueOf(entity.getCurrency()))
                .type(AccountTypeEnum.valueOf(entity.getType()))
                .build();
    }
}
