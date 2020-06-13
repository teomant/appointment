package org.teomant.appointment.security.domain.model;

import lombok.Data;

@Data
public class Privilege {
    private Long id;
    private EntityNameEnum entity;
    private ActionNameEnum action;
}
