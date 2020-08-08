package org.teomant.appointment.api.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.teomant.appointment.api.web.model.CreateClientUserDto;
import org.teomant.appointment.api.web.model.CreateClientUserVoteDto;
import org.teomant.appointment.appointment.domain.service.AppointmentService;
import org.teomant.appointment.appointment.web.mapping.AppointmentDtoMapper;
import org.teomant.appointment.notification.domain.service.NotificationService;
import org.teomant.appointment.notification.web.dto.NotificationDto;
import org.teomant.appointment.notification.web.mapping.NotificationDtoMapper;
import org.teomant.appointment.security.persistance.repository.RoleEntityJpaRepository;
import org.teomant.appointment.user.domain.model.Client;
import org.teomant.appointment.user.domain.model.ClientUser;
import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.user.persistance.model.ClientEntity;
import org.teomant.appointment.user.persistance.model.ClientUserEntity;
import org.teomant.appointment.user.persistance.repository.ClientUserEntityJpaRepository;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.model.VoteEnum;
import org.teomant.appointment.vote.domain.service.VoteService;
import org.teomant.appointment.vote.web.dto.VoteDto;
import org.teomant.appointment.vote.web.mapping.VoteDtoMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final RoleEntityJpaRepository roleEntityJpaRepository;
    private final ClientUserEntityJpaRepository clientUserEntityJpaRepository;
    private final AppointmentService appointmentService;
    private final AppointmentDtoMapper appointmentDtoMapper;
    private final VoteService voteService;
    private final VoteDtoMapper voteDtoMapper;
    private final NotificationService notificationService;
    private final UserMapper userMapper = new UserMapper();
    private final NotificationDtoMapper notificationDtoMapper = new NotificationDtoMapper();

    @GetMapping("/test")
    public ResponseEntity getAppointment() {
        log.warn("YEP");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-user")
    public ResponseEntity addUser(@RequestBody CreateClientUserDto userDto) {

        ClientUserEntity clientUserEntity = new ClientUserEntity();
        clientUserEntity.setClientUserId(userDto.getClientId());
        clientUserEntity.setRoles(Collections.singleton(roleEntityJpaRepository.findByName("ROLE_USER")));

        ClientEntity clientEntity = new ClientEntity();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = null;
        if (principal instanceof Client) {
            client = (Client) principal;
        }
        clientEntity.setId(client == null ? null : client.getId());
        clientUserEntity.setClientEntity(clientEntity);

        clientUserEntity.setUsername(client.getUsername() + ": " + userDto.getUsername());

        if (clientUserEntityJpaRepository.findByClientUserIdAndClientEntity(userDto.getClientId(), clientEntity) == null) {
            clientUserEntityJpaRepository.save(clientUserEntity);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/vote")
    public VoteDto createVote(@RequestBody CreateClientUserVoteDto dto) {

        Vote vote = new Vote();
        vote.setComment(dto.getComment());
        vote.setOptionId(dto.getOptionId());
        vote.setType(VoteEnum.valueOf(dto.getType()));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = null;
        if (principal instanceof Client) {
            client = (Client) principal;
        }
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(client == null ? null : client.getId());

        vote.setUser(
                userMapper.toModel(
                        clientUserEntityJpaRepository.findByClientUserIdAndClientEntity(dto.getClientId(), clientEntity)
                )
        );

        return voteDtoMapper.fromModelToDto(voteService.create(vote));
    }

    @PostMapping("/deleteVote/{id}")
    public void deleteVote(@PathVariable Long id) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SiteUser currentSiteUser = null;
        if (principal instanceof SiteUser) {
            currentSiteUser = (SiteUser) principal;
        }

        voteService.delete(voteDtoMapper.fromDeleteToModel(id), currentSiteUser);
    }

    @GetMapping("/notification")
    public List<NotificationDto> getNotifications(@RequestParam(required = false) Boolean delivered, @RequestParam Long userId) {
        ClientEntity clientEntity = new ClientEntity();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = null;
        if (principal instanceof Client) {
            client = (Client) principal;
        }
        clientEntity.setId(client == null ? null : client.getId());


        return notificationService.findByUser(
                userMapper.toModel(
                        clientUserEntityJpaRepository.findByClientUserIdAndClientEntity(userId, clientEntity)
                ),
                delivered
        ).stream()
                .map(notificationDtoMapper::fromModelToDto)
                .collect(Collectors.toList()
                );
    }

    @PostMapping("notification/delivered/{notificationId}")
    public ResponseEntity markDelivered(@PathVariable Long notificationId, @RequestParam Long userId) {

        ClientEntity clientEntity = new ClientEntity();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = null;
        if (principal instanceof Client) {
            client = (Client) principal;
        }
        clientEntity.setId(client == null ? null : client.getId());

        ClientUser clientUser = userMapper.toModel(
                clientUserEntityJpaRepository.findByClientUserIdAndClientEntity(userId, clientEntity)
        );

        notificationService.markDelivered(notificationId, clientUser);
        return ResponseEntity.ok().build();
    }
}
