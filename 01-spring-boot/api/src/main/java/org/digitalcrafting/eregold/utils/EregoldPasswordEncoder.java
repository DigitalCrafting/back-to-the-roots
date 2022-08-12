package org.digitalcrafting.eregold.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Optional;

@Slf4j
public final class EregoldPasswordEncoder {
    private EregoldPasswordEncoder() {
    }

    public static Optional<String> encodePassword(char[] pass, String userId) {
        try {
            byte[] passwordb = Charset.forName("UTF-8").encode(CharBuffer.wrap(pass)).array();
            String passHash = BCrypt.hashpw(passwordb, BCrypt.gensalt());
            return Optional.of(passHash);
        } catch (Exception exception) {
            log.info("Failed to hash password for user with email {}", userId, exception);
            return Optional.empty();
        }
    }
}
