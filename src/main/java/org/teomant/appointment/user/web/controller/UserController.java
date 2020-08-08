package org.teomant.appointment.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.teomant.appointment.exception.AppointmentException;
import org.teomant.appointment.security.jwt.JwtUtils;
import org.teomant.appointment.security.persistance.repository.RoleEntityJpaRepository;
import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.user.domain.model.UserDetailImpl;
import org.teomant.appointment.user.persistance.model.ClientEntity;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;
import org.teomant.appointment.user.persistance.repository.ClientEntityJpaRepository;
import org.teomant.appointment.user.persistance.repository.SiteUserEntityJpaRepository;
import org.teomant.appointment.user.web.dto.UserDto;
import org.teomant.appointment.user.web.dto.UserInfoDto;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final SiteUserEntityJpaRepository siteUserEntityJpaRepository;
    private final ClientEntityJpaRepository clientEntityJpaRepository;
    private final RoleEntityJpaRepository roleEntityJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

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

    @GetMapping("/user/current")
    public UserInfoDto getCurrentUser() {
        UserInfoDto userInfoDto = new UserInfoDto();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SiteUser) {
            userInfoDto.setUsername(((SiteUser) principal).getUsername());
            userInfoDto.setId(((SiteUser) principal).getId());
        }

        return userInfoDto;
    }

    @PostMapping("/client/register_client")
    public ResponseEntity registerClient(@RequestBody UserDto dto) {
        ClientEntity userEntity = new ClientEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        userEntity.setRoles(Collections.singleton(roleEntityJpaRepository.findByName("ROLE_CLIENT")));
        clientEntityJpaRepository.save(userEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/client/login_client")
    public ResponseEntity loginClient(@RequestBody UserDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        String jwt = JwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
        if (userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch(role -> role.equals("ROLE_CLIENT"))) {
            throw new AppointmentException("No client role");
        }

        return ResponseEntity.ok(jwt);
    }
}
