package org.teomant.appointment.security.domain.model;

import lombok.Data;

import java.util.Collection;

@Data
public class Role {
    private Long id;
    private String name;
    private Collection<Privilege> privileges;
}
