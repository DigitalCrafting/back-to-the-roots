package org.digitalcrafting.eregold.http.api.registration;

import org.digitalcrafting.eregold.http.core.security.DCPasswordEncoder;
import org.digitalcrafting.eregold.http.repository.customers.CustomerEntity;
import org.digitalcrafting.eregold.http.repository.customers.CustomersEntityManager;
import org.digitalcrafting.eregold.http.repository.users.UserEntity;
import org.digitalcrafting.eregold.http.repository.users.UsersEntityManager;

import java.time.Instant;
import java.util.Optional;

public class RegistrationService {
    private final UsersEntityManager usersEntityManager = new UsersEntityManager();
    private final CustomersEntityManager customersEntityManager = new CustomersEntityManager();

    public RegisterResponse register(RegisterRequest request) {
        if (usersEntityManager.getByUserId(request.getEmail()) != null) {
            return new RegisterResponse(RegistrationStatusEnum.ALREADY_EXISTS);
        }

        Optional<String> customerId = createCustomer(request);
        if (customerId.isPresent()) {
            return new RegisterResponse(customerId.get());
        } else {
            return new RegisterResponse(RegistrationStatusEnum.CREATION_FAILED);
        }
    }

    public Optional<String> createCustomer(RegisterRequest request) {
        Optional<String> encodedPassword = DCPasswordEncoder.encodePassword(request.getPassword(), request.getEmail());
        if (encodedPassword.isEmpty()) {
            return Optional.empty();
        }

        UserEntity userEntity = UserEntity.builder()
                .userId(request.getEmail())
                .passwordHash(encodedPassword.get())
                .build();
        usersEntityManager.insert(userEntity);

        String customerId = generateCustomerId();

        CustomerEntity customerEntity = CustomerEntity.builder()
                .customerId(customerId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        customersEntityManager.insert(customerEntity);
        return Optional.of(customerId);
    }

    private String generateCustomerId() {
        long dateSeconds = Instant.now().toEpochMilli();
        String customerId = String.valueOf(dateSeconds).substring(0, 8);
        while (customersEntityManager.getByCustomerId(customerId) != null) {
            dateSeconds = Instant.now().toEpochMilli();
            customerId = String.valueOf(dateSeconds).substring(0, 8);
        }
        return customerId;
    }
}
