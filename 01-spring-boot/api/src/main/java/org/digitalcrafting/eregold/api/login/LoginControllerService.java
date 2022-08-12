package org.digitalcrafting.eregold.api.login;

import lombok.RequiredArgsConstructor;
import org.digitalcrafting.eregold.authentication.EregoldSessionContext;
import org.digitalcrafting.eregold.repository.customers.CustomerEntity;
import org.digitalcrafting.eregold.repository.customers.CustomersEntityManager;
import org.digitalcrafting.eregold.repository.users.UserEntity;
import org.digitalcrafting.eregold.repository.users.UsersEntityManager;
import org.digitalcrafting.eregold.utils.EregoldJWTUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class LoginControllerService {
    private final UsersEntityManager usersEntityManager;
    private final CustomersEntityManager customersEntityManager;
    private final EregoldJWTUtils jwtUtils;
    private final EregoldSessionContext sessionContext;

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        UserEntity userEntity = usersEntityManager.getByUserId(request.getUserId());
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] passwordb = Charset.forName("UTF-8").encode(CharBuffer.wrap(request.getPassword())).array();
        if (BCrypt.checkpw(passwordb, userEntity.getPasswordHash())) {
            String token = jwtUtils.generateAccessToken(userEntity.getUserId());
            sessionContext.setToken(token);
            sessionContext.setUserId(request.getUserId());

            CustomerEntity customerEntity = customersEntityManager.getByEmail(request.getUserId());
            sessionContext.setCustomerId(customerEntity.getCustomerId());
            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.badRequest().build();
    }
}
