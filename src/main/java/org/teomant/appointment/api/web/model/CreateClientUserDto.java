package org.teomant.appointment.api.web.model;

import lombok.Data;

@Data
public class CreateClientUserDto {
    private String username;
    private Long clientId;
}
