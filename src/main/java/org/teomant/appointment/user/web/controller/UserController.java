package org.teomant.appointment.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.teomant.appointment.security.persistance.repository.RoleEntityJpaRepository;
import org.teomant.appointment.user.persistance.model.UserEntity;
import org.teomant.appointment.user.persistance.repository.UserEntityJpaRepository;
import org.teomant.appointment.user.web.dto.UserDto;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final RoleEntityJpaRepository roleEntityJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/user/register")
    public ResponseEntity register(@RequestBody UserDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        userEntity.setRoles(Collections.singleton(roleEntityJpaRepository.findByName("ROLE_USER")));
        userEntityJpaRepository.save(userEntity);
        return ResponseEntity.ok().build();
    }
}
