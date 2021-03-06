package org.teomant.appointment.appointment.persistance.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUserEntity user;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<OptionEntity> options = new ArrayList<>();
}
