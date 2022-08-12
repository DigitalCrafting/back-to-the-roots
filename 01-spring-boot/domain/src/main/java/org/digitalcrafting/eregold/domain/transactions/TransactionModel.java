package org.digitalcrafting.eregold.domain.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digitalcrafting.eregold.domain.accounts.CurrencyEnum;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {
    private String srcAccount;
    private String dstAccount;
    private String description;
    private BigDecimal amount;
    private CurrencyEnum currency;
}
