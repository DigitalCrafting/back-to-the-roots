package org.digitalcrafting.eregold.repository.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersEntityManager {
    private final UsersMapper mapper;

    public UserEntity getByUserId(String userId) {
        return mapper.getByUserId(userId);
    }

    public void insert(UserEntity entity) {
        mapper.insert(entity);
    }
}
