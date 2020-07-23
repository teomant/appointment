package org.teomant.appointment.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.teomant.appointment.security.persistance.repository.RoleEntityJpaRepository;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;
import org.teomant.appointment.user.persistance.repository.SiteUserEntityJpaRepository;
import org.teomant.appointment.user.web.dto.UserDto;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final SiteUserEntityJpaRepository siteUserEntityJpaRepository;
    private final RoleEntityJpaRepository roleEntityJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/user/register")
    public ResponseEntity register(@RequestBody UserDto dto) {
        SiteUserEntity userEntity = new SiteUserEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        String roleName = siteUserEntityJpaRepository.count() == 0 ? "ROLE_ADMIN" : "ROLE_USER";
        userEntity.setRoles(Collections.singleton(roleEntityJpaRepository.findByName(roleName)));
        siteUserEntityJpaRepository.save(userEntity);
        return ResponseEntity.ok().build();
    }
}
