package org.digitalcrafting.eregold.repository.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntity {
    private String userId;
    private String passwordHash;

    public String getPasswordHash() {
        // TODO check why the password has whitespaces
        return passwordHash.trim();
    }
}
