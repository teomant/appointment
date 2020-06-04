package org.teomant.appointment.appointment.persistance.model;

import lombok.Getter;
import lombok.Setter;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    private Set<VoteEntity> votes = new HashSet<>();
}
