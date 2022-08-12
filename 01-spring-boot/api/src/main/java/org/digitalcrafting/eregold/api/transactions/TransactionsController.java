package org.digitalcrafting.eregold.api.transactions;

import lombok.RequiredArgsConstructor;
import org.digitalcrafting.eregold.domain.transactions.TransactionModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/transactions")
public class TransactionsController {
    private final TransactionsControllerService service;

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransactionModel request) {
        service.transfer(request);
    }

    @PostMapping("/deposit")
    public void deposit(@RequestBody TransactionModel request) {
        service.deposit(request);
    }
}
