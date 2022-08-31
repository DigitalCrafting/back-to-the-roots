package org.digitalcrafting.eregold.http.api.registration;

import org.digitalcrafting.eregold.http.repository.customers.CustomersEntityManager;
import org.digitalcrafting.eregold.http.repository.users.UsersEntityManager;

public class RegistrationService {
    private final UsersEntityManager usersEntityManager = new UsersEntityManager();
    private final CustomersEntityManager customersEntityManager = new CustomersEntityManager();
}
