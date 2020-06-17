package org.teomant.appointment.notification.persistance.model;

import lombok.Getter;
import lombok.Setter;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column
    private String comment;

    @Column
    private boolean delivered;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private SiteUserEntity user;
}
