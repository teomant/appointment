package org.teomant.appointment.appointment.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.appointment.persistance.mapping.AppointmentMapper;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;
import org.teomant.appointment.exception.AppointmentException;
import org.teomant.appointment.vote.persistance.mapping.VoteMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AppointmentRepositoryAdapter implements AppointmentRepository {

    private final AppointmentEntityJpaRepository appointmentEntityJpaRepository;
    private final OptionEntityJpaRepository optionEntityJpaRepository;

    private final AppointmentMapper appointmentMapper = new AppointmentMapper();
    private final VoteMapper voteMapper = new VoteMapper();


    @Override
    public Appointment save(Appointment appointment) {

        Set<Long> receivedOptions = appointment.getOptions().stream()
                .filter(option -> option.getId() != null)
                .peek(option -> {
                    OptionEntity optionEntity = optionEntityJpaRepository.findById(option.getId())
                            .orElseThrow(() -> new AppointmentException("Can`t find option"));

                    AppointmentEntity storedOptionAppointment = optionEntity.getAppointment();

                    if (storedOptionAppointment == null || !storedOptionAppointment.getId().equals(appointment.getId())) {
                        throw new AppointmentException("Trying to move option from one appointment to another");
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

    @Override
    @Transactional
    public List<Appointment> getUndoneAppointmentsTill(OffsetDateTime till) {
        return appointmentEntityJpaRepository.findByTillBeforeAndDoneFalse(till).stream()
                .map(appointmentMapper::toModel)
                .collect(Collectors.toList());
    }

    public AppointmentEntity findById(Long id) {
        return appointmentEntityJpaRepository.findById(id).orElseThrow(() -> new AppointmentException("Can`t find appointment"));
    }
}
