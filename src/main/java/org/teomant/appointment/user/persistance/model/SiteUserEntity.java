package org.teomant.appointment.user.persistance.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "site_users")
public class SiteUserEntity extends UserEntity {

    @Column
    private String password;

    @Column
    private String username;

}
