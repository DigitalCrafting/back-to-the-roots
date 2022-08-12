package org.digitalcrafting.eregold.api.accounts;

import org.digitalcrafting.eregold.domain.accounts.AccountModel;
import org.digitalcrafting.eregold.domain.accounts.AccountTypeEnum;
import org.digitalcrafting.eregold.domain.accounts.CurrencyEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountsControllerTest {
    @Autowired
    private AccountsController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsControllerService service;

    @Test
    public void should_createAccountsController() {
        assertNotNull(controller);
    }

    @Test
    public void should_correctlyReturnAccountsList() throws Exception {
        List<AccountModel> modelList = List.of(
                AccountModel.builder()
                        .type(AccountTypeEnum.DEBIT)
                        .currentBalance(BigDecimal.ZERO)
                        .currency(CurrencyEnum.GLD)
                        .accountNumber("12ERGD123456")
                        .accountName("Test account")
                        .build()
        );
        when(service.getAccounts()).thenReturn(modelList);

        this.mockMvc.perform(get("/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"accountNumber\":\"12ERGD123456\"," +
                                "\"accountName\":\"Test account\"," +
                                "\"currentBalance\":0," +
                                "\"currency\":\"GLD\"," +
                                "\"type\":\"DEBIT\"}]"
                )));
    }
}