package org.teomant.appointment.appointment.persistance.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    private List<VoteEntity> votes = new ArrayList<>();
}
