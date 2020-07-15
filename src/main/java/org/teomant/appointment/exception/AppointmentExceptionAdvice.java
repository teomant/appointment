package org.teomant.appointment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class AppointmentExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AppointmentException.class})
    public void handle(AppointmentException e) {
        log.error(e.getMessage(), e);
    }
}
