package org.teomant.appointment.security.persistance.model;

import lombok.Getter;
import lombok.Setter;
import org.teomant.appointment.security.domain.model.ActionNameEnum;
import org.teomant.appointment.security.domain.model.EntityNameEnum;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "privileges")
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private EntityNameEnum entity;

    @Column
    @Enumerated(EnumType.STRING)
    private ActionNameEnum action;
}
