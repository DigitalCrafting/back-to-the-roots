package org.digitalcrafting.eregold.http.api.accounts;

import lombok.Data;
import org.digitalcrafting.eregold.http.domain.accounts.AccountTypeEnum;
import org.digitalcrafting.eregold.http.domain.accounts.CurrencyEnum;

@Data
public class CreateAccountRequest {
    private String accountName;
    private AccountTypeEnum accountType;
    private CurrencyEnum currency;
}
