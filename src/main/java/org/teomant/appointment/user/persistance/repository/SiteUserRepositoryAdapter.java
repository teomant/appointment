package org.teomant.appointment.user.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.domain.repository.UserRepository;
import org.teomant.appointment.user.persistance.mapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class SiteUserRepositoryAdapter implements UserRepository {

    private final SiteUserEntityJpaRepository siteUserEntityJpaRepository;
    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserDetails findByUsername(String username) {
        return userMapper.toModel(siteUserEntityJpaRepository.findByUsername(username));
    }
}
