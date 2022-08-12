package org.digitalcrafting.eregold.api.transactions;

import com.google.gson.Gson;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import org.digitalcrafting.eregold.domain.accounts.CurrencyEnum;
import org.digitalcrafting.eregold.domain.transactions.TransactionModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionsControllerTest {
    @Autowired
    private TransactionsController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsControllerService service;

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
    private Gson gson = new Gson();

    @Test
    public void should_createTransferController() {
        assertNotNull(controller);
    }

    @Test
    public void should_callTransferMethod_inControllerService() throws Exception {
        this.mockMvc.perform(
                post("/v1/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(mockTransferRequest))
        )
        .andExpect(status().isOk());

        verify(service, never()).deposit(any());
        verify(service, atMostOnce()).transfer(mockTransferRequest);
    }

    @Test
    public void should_callDepositMethod_inControllerService() throws Exception {
        this.mockMvc.perform(
                post("/v1/transactions/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(mockDepositRequest))
        )
        .andExpect(status().isOk());

        verify(service, never()).transfer(any());
        verify(service, atMostOnce()).deposit(mockDepositRequest);
    }
}