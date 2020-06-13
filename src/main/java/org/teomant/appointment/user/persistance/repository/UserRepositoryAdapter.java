package org.teomant.appointment.user.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.domain.repository.UserRepository;
import org.teomant.appointment.user.persistance.mapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final UserMapper userMapper = new UserMapper();

    @Override
    public User findByUsername(String username) {
        return userMapper.toModel(userEntityJpaRepository.findByUsername(username));
    }
}
