package org.teomant.appointment.user.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class User {
    private Long id;
    private String username;
}
