package org.teomant.appointment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    @GetMapping("/test")
    public ResponseEntity getAppointment() {
        log.warn("YEP");
        return ResponseEntity.ok().build();
    }
}
