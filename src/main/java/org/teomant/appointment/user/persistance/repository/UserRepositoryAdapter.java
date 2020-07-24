package org.teomant.appointment.user.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.domain.repository.UserRepository;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.user.persistance.model.UserEntity;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final SiteUserEntityJpaRepository siteUserEntityJpaRepository;
    private final ClientEntityJpaRepository clientEntityJpaRepository;
    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserDetails findByUsername(String username) {
        UserEntity byUsername = siteUserEntityJpaRepository.findByUsername(username);
        if (byUsername == null) {
            byUsername = clientEntityJpaRepository.findByUsername(username);
        }
        return userMapper.toUserDetails(byUsername);
    }
}
