package org.teomant.appointment.notification.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.teomant.appointment.notification.domain.service.NotificationService;
import org.teomant.appointment.notification.web.dto.NotificationDto;
import org.teomant.appointment.notification.web.mapping.NotificationDtoMapper;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.user.persistance.model.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/notification")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;
    private final NotificationDtoMapper notificationDtoMapper = new NotificationDtoMapper();
    private final UserMapper userMapper = new UserMapper();

    @GetMapping("/{userId}")
    public List<NotificationDto> getNotifications(@PathVariable Long userId, @RequestParam boolean delivered) {
        return notificationService.findByUser(userId, delivered).stream().map(notificationDtoMapper::fromModelToDto).collect(Collectors.toList());
    }

    @PostMapping("/delivered/{notificationId}")
    public ResponseEntity markDelivered(@PathVariable Long notificationId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        notificationService.markDelivered(notificationId, userMapper.toModel((UserEntity) principal));
        return ResponseEntity.ok().build();
    }

}
