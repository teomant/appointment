package org.teomant.appointment.vote.web.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.domain.repository.UserRepository;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.model.VoteEnum;
import org.teomant.appointment.vote.web.dto.VoteDto;
import org.teomant.appointment.vote.web.dto.VoteRequestDto;

@Component
@RequiredArgsConstructor
public class VoteDtoMapper {

    private final UserMapper userMapper = new UserMapper();
    private final UserRepository userRepository;

    public Vote fromCreateToModel(VoteRequestDto dto) {
        Vote model = new Vote();
        model.setComment(dto.getComment());
        model.setType(VoteEnum.valueOf(dto.getType()));
        Option option = new Option();
        option.setId(dto.getOptionId());
        model.setOptionId(option.getId());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            model.setUser((User) principal);
        }

        return model;
    }

    public Vote fromDeleteToModel(Long id) {
        Vote model = new Vote();
        model.setId(id);

        return model;
    }

    public VoteDto fromModelToDto(Vote model) {
        VoteDto dto = new VoteDto();
        dto.setId(model.getId());
        dto.setComment(model.getComment());
        dto.setType(model.getType().name());

        dto.setVoterName(model.getUser() != null ? model.getUser().getUsername() : "");

        return dto;
    }
}
