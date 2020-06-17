package org.teomant.appointment.vote.persistance.model;

import lombok.Getter;
import lombok.Setter;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;
import org.teomant.appointment.vote.domain.model.VoteEnum;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "vote")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column
    private VoteEnum type;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private OptionEntity option;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SiteUserEntity user;
}
