package ru.practicum.explore_with_me.request.mapper;

import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.model.Request;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreated().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                String.valueOf(request.getStatus()));
    }

    public static List<ParticipationRequestDto> toParticipationRequestDtoList(List<Request> requests) {
        List<ParticipationRequestDto> participationRequestDtoList = new ArrayList<>();
        for (Request entry : requests) {
            participationRequestDtoList.add(RequestMapper.toParticipationRequestDto(entry));
        }
        return participationRequestDtoList;
    }
}
