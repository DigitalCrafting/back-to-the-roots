package org.digitalcrafting.eregold.repository.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsEntityManager {
    private final TransactionsMapper mapper;

    @Transactional
    public void insert(List<TransactionEntity> transactions) {
        if (transactions != null && !transactions.isEmpty()) {
            Long transactionId = mapper.getNextId();
            transactions.forEach(transaction -> {
                transaction.setId(transactionId);
                this.mapper.insert(transaction);
            });

        }
    }

    @Transactional
    public void insert(TransactionEntity entity) {
        Long transactionId = mapper.getNextId();
        entity.setId(transactionId);
        this.mapper.insert(entity);
    }

    public List<TransactionEntity> getByAccountNumber(String accountNumber) {
        return this.mapper.getByAccountNumber(accountNumber);
    }
}
