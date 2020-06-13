package org.teomant.appointment.user.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.teomant.appointment.security.domain.model.Role;

import java.util.Collection;

@Data
@EqualsAndHashCode(of = "id")
public class User {

    private Long id;
    private String username;
    private Collection<Role> roles;
}
