package org.teomant.appointment.notification.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.teomant.appointment.notification.domain.service.NotificationService;
import org.teomant.appointment.notification.web.dto.NotificationDto;
import org.teomant.appointment.notification.web.mapping.NotificationDtoMapper;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.domain.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/notification")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;
    private final NotificationDtoMapper notificationDtoMapper = new NotificationDtoMapper();
    private final UserRepository userRepository;

    @GetMapping("/get")
    public List<NotificationDto> getNotifications(@RequestParam(required = false) Boolean delivered) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return notificationService.findByUser((User) principal, delivered).stream().map(notificationDtoMapper::fromModelToDto).collect(Collectors.toList());
    }

    @PostMapping("/delivered/{notificationId}")
    public ResponseEntity markDelivered(@PathVariable Long notificationId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = null;
        if (principal instanceof User) {
            currentUser = (User) principal;
        }

        notificationService.markDelivered(notificationId, currentUser);
        return ResponseEntity.ok().build();
    }

}
