package org.teomant.appointment.user.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.domain.repository.UserRepository;
import org.teomant.appointment.user.persistance.mapper.SiteUserMapper;

@Repository
@RequiredArgsConstructor
public class SiteUserRepositoryAdapter implements UserRepository {

    private final SiteUserEntityJpaRepository siteUserEntityJpaRepository;
    private final SiteUserMapper siteUserMapper = new SiteUserMapper();

    @Override
    public User findByUsername(String username) {
        return siteUserMapper.toModel(siteUserEntityJpaRepository.findByUsername(username));
    }
}
