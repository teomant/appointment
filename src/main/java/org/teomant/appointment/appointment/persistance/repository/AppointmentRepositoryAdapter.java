package org.teomant.appointment.appointment.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.appointment.persistance.mapping.AppointmentMapper;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AppointmentRepositoryAdapter implements AppointmentRepository {

    private final AppointmentEntityJpaRepository appointmentEntityJpaRepository;
    private final OptionEntityJpaRepository optionEntityJpaRepository;
    private final AppointmentMapper appointmentMapper = new AppointmentMapper();


    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entityToSave = appointmentMapper.toEntity(appointment);

        Set<Long> receivedOptions = appointment.getOptions().stream()
                .filter(option -> option.getId() != null)
                .map(Option::getId)
                .peek(id -> {
                    if (!optionEntityJpaRepository.findById(id)
                            .orElseThrow(IllegalArgumentException::new)
                            .getAppointment().getId().equals(appointment.getId())) {
                        throw new IllegalArgumentException();
                    }
                })
                .collect(Collectors.toSet());

        if (entityToSave.getId() != null) {
            findById(entityToSave.getId()).getOptions().forEach(optionEntity -> {
                if (!receivedOptions.contains(optionEntity.getId())) {
                    optionEntity.setAppointment(null);
                    optionEntityJpaRepository.save(optionEntity);
                }
            });
        }

        return appointmentMapper.toModel(
                appointmentEntityJpaRepository.save(entityToSave)
        );
    }

    @Override
    public Appointment get(Long id) {
        return appointmentMapper.toModel(findById(id));
    }

    private AppointmentEntity findById(Long id) {
        return appointmentEntityJpaRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
