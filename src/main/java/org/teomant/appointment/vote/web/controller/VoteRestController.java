package org.teomant.appointment.vote.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.vote.domain.service.VoteService;
import org.teomant.appointment.vote.web.dto.VoteDto;
import org.teomant.appointment.vote.web.dto.VoteRequestDto;
import org.teomant.appointment.vote.web.mapping.VoteDtoMapper;

@RestController
@RequestMapping("/rest/appointment")
@RequiredArgsConstructor
public class VoteRestController {

    private final VoteService voteService;
    private final VoteDtoMapper voteDtoMapper;

    @PostMapping("/vote")
    public VoteDto createVote(@RequestBody VoteRequestDto dto) {
        return voteDtoMapper.fromModelToDto(voteService.create(voteDtoMapper.fromCreateToModel(dto)));
    }

    @PostMapping("/deleteVote/{id}")
    public void deleteVote(@PathVariable Long id) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SiteUser currentSiteUser = null;
        if (principal instanceof SiteUser) {
            currentSiteUser = (SiteUser) principal;
        }

        voteService.delete(voteDtoMapper.fromDeleteToModel(id), currentSiteUser);
    }

}
