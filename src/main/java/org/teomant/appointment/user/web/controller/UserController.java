package org.teomant.appointment.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.teomant.appointment.user.persistance.model.UserEntity;
import org.teomant.appointment.user.persistance.repository.UserEntityJpaRepository;
import org.teomant.appointment.user.web.dto.UserDto;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/user/register")
    public ResponseEntity createAppointment(@RequestBody UserDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        userEntityJpaRepository.save(userEntity);
        return ResponseEntity.ok().build();
    }
}
