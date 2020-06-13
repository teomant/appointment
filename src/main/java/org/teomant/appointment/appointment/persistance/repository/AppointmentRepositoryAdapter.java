package org.teomant.appointment.appointment.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.appointment.persistance.mapping.AppointmentMapper;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.persistance.mapping.VoteMapper;
import org.teomant.appointment.vote.persistance.repository.VoteEntityJpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AppointmentRepositoryAdapter implements AppointmentRepository {

    private final AppointmentEntityJpaRepository appointmentEntityJpaRepository;
    private final OptionEntityJpaRepository optionEntityJpaRepository;
    private final VoteEntityJpaRepository voteEntityJpaRepository;

    private final AppointmentMapper appointmentMapper = new AppointmentMapper();
    private final VoteMapper voteMapper = new VoteMapper();


    @Override
    public Appointment save(Appointment appointment) {

        Set<Long> receivedOptions = appointment.getOptions().stream()
                .filter(option -> option.getId() != null)
                .peek(option -> {
                    OptionEntity optionEntity = optionEntityJpaRepository.findById(option.getId())
                            .orElseThrow(IllegalArgumentException::new);

                    AppointmentEntity storedOptionAppointment = optionEntity.getAppointment();

                    if (storedOptionAppointment == null || !storedOptionAppointment.getId().equals(appointment.getId())) {
                        throw new IllegalArgumentException();
                    }
                    option.setVotes(optionEntity.getVotes().stream().map(voteMapper::toModel).collect(Collectors.toList()));
                })
                .map(Option::getId)
                .collect(Collectors.toSet());

        AppointmentEntity entityToSave = appointmentMapper.toEntity(appointment);

        if (entityToSave.getId() != null) {
            findById(entityToSave.getId()).getOptions().forEach(optionEntity -> {
                if (!receivedOptions.contains(optionEntity.getId())) {
                    optionEntity.setAppointment(null);
                    optionEntityJpaRepository.save(optionEntity);
                }
            });
        }

        return appointmentMapper.toModel(appointmentEntityJpaRepository.save(entityToSave));
    }

    @Override
    public Appointment get(Long id) {
        return appointmentMapper.toModel(findById(id));
    }

    //Hibernate sucks
    @Override
    public List<Appointment> getUndoneAppointmentsTill(OffsetDateTime till) {
        return appointmentEntityJpaRepository.findByTillBeforeAndDoneFalse(till).stream()
                .map(appointmentEntity -> {
                    Appointment appointment = new Appointment();
                    appointment.setId(appointmentEntity.getId());
                    appointment.setTill(appointmentEntity.getTill());

                    if (appointmentEntity.getUser() != null) {
                        User user = new User();
                        user.setId(appointmentEntity.getUser().getId());
                        appointment.setUser(user);
                    }

                    List<Option> options = optionEntityJpaRepository.findByAppointment(appointmentEntity).stream()
                            .map(optionEntity -> {
                                Option option = new Option();

                                List<Vote> votes = voteEntityJpaRepository.findByOption(optionEntity).stream()
                                        .filter(voteEntity -> voteEntity.getUser() != null)
                                        .map(voteEntity -> {
                                            Vote vote = new Vote();

                                            User voteUser = new User();
                                            voteUser.setId(voteEntity.getUser().getId());
                                            vote.setUser(voteUser);

                                            return vote;
                                        }).collect(Collectors.toList());
                                option.setVotes(votes);

                                return option;
                            })
                            .collect(Collectors.toList());
                    appointment.setOptions(options);

                    return appointment;
                })
                .collect(Collectors.toList());
    }

    public AppointmentEntity findById(Long id) {
        return appointmentEntityJpaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
