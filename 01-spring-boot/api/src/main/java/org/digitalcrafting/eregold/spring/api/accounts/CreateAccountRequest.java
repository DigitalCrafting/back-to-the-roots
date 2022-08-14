package org.digitalcrafting.eregold.spring.api.accounts;

import lombok.Data;
import org.digitalcrafting.eregold.spring.domain.accounts.AccountTypeEnum;
import org.digitalcrafting.eregold.spring.domain.accounts.CurrencyEnum;

@Data
public class CreateAccountRequest {
    private String accountName;
    private AccountTypeEnum accountType;
    private CurrencyEnum currency;
}
