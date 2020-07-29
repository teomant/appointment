package org.teomant.appointment.user.persistance.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class ClientEntity extends UserEntity {

    @Column
    private String password;

    @Column
    private String username;

    //Тут можно было бы добавить переменную для isAccountNonLocked. Возможно, когда-нибудь он она появится.
}
