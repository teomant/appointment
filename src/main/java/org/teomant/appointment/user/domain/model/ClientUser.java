package org.teomant.appointment.user.domain.model;

import lombok.Data;

@Data
public class ClientUser extends User {
    private Long clientUserId;
    private Client client;
}
