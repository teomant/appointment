package org.teomant.appointment.api.web.model;

import lombok.Data;

@Data
public class CreateClientUserVoteDto {
    private Long clientId;
    private String comment;
    private String type;
    private Long optionId;
}
