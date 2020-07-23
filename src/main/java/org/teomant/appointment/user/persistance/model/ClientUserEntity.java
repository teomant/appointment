package org.teomant.appointment.user.persistance.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "client_users")
public class ClientUserEntity extends UserEntity {

    @Column(name = "client_user_id")
    private Long clientUserId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity clientEntity;

    //Тут можно было бы добавить переменную для isAccountNonLocked. Возможно, когда-нибудь он она появится.
}
