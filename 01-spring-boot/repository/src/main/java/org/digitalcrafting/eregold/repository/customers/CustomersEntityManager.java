package org.digitalcrafting.eregold.repository.customers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomersEntityManager {
    private final CustomersMapper mapper;

    public CustomerEntity getByEmail(String email) {
        return mapper.getByEmail(email);
    }

    public CustomerEntity getByCustomerId(String customerId) {
        return mapper.getByCustomerId(customerId);
    }

    public void insert(CustomerEntity entity) {
        mapper.insert(entity);
    }
}
