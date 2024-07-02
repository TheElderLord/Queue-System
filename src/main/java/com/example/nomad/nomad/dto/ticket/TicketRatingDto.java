package com.example.nomad.nomad.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRatingDto {
    private Long id;
    private int rating;
    private String comment;
}
