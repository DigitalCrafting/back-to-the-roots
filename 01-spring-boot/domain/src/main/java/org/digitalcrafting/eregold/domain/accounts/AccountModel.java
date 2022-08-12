package org.digitalcrafting.eregold.domain.accounts;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountModel {
    private String accountNumber;
    private String accountName;
    private BigDecimal currentBalance;
    private CurrencyEnum currency;
    private AccountTypeEnum type;
}
