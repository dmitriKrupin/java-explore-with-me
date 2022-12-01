package ru.practicum.ewm.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipationRequestDto {
    //Заявка на участие в событии
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;
}
