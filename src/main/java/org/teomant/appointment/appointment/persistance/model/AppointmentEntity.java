package org.teomant.appointment.appointment.persistance.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column
    private String comment;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
    private OffsetDateTime created;

    @Column
    private OffsetDateTime till;

    @Column
    private boolean deleted;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Set<OptionEntity> options = new HashSet<>();
}
