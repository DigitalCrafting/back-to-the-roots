package org.digitalcrafting.eregold.api.accounts;

import lombok.RequiredArgsConstructor;
import org.digitalcrafting.eregold.domain.accounts.AccountDetailsModel;
import org.digitalcrafting.eregold.domain.accounts.AccountModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
public class AccountsController {

    private final AccountsControllerService service;

    @GetMapping
    @ResponseBody
    public List<AccountModel> getAccounts() {
        return service.getAccounts();
    }

    @GetMapping("/{accountNumber}")
    @ResponseBody
    public AccountDetailsModel getAccountDetails(@PathVariable String accountNumber) {
        return service.getAccountDetails(accountNumber);
    }

    @PostMapping
    public void createAccount(@RequestBody CreateAccountRequest request) {
        service.createAccount(request);
    }
}
