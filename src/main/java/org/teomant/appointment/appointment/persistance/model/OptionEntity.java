package org.teomant.appointment.appointment.persistance.model;

import lombok.Getter;
import lombok.Setter;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "options")
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column
    private String comment;

    @Column(name = "datetime")
    private OffsetDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;
}
