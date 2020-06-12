package org.teomant.appointment.vote.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.teomant.appointment.vote.domain.service.VoteService;
import org.teomant.appointment.vote.web.dto.VoteDto;
import org.teomant.appointment.vote.web.dto.VoteRequestDto;
import org.teomant.appointment.vote.web.mapping.VoteDtoMapper;

@RestController
@RequestMapping("/rest/appointment")
@RequiredArgsConstructor
public class VoteRestController {

    private final VoteService voteService;
    private final VoteDtoMapper voteDtoMapper = new VoteDtoMapper();

    @PostMapping("/vote")
    public VoteDto createVote(@RequestBody VoteRequestDto dto) {
        return voteDtoMapper.fromModelToDto(voteService.create(voteDtoMapper.fromCreateToModel(dto)));
    }

    @PostMapping("/deleteVote/{id}")
    public void deleteVote(@PathVariable Long id) {

        voteService.delete(voteDtoMapper.fromDeleteToModel(id));
    }

}
