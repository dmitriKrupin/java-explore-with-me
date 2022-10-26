package ru.practicum.explore_with_me.request.mapper;

import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.model.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreated(),
                request.getEventId(),
                request.getId(),
                request.getRequester(),
                request.getStatus());
    }

    public static List<ParticipationRequestDto> toParticipationRequestDtoList(List<Request> requests) {
        List<ParticipationRequestDto> participationRequestDtoList = new ArrayList<>();
        for (Request entry : requests) {
            participationRequestDtoList.add(RequestMapper.toParticipationRequestDto(entry));
        }
        return participationRequestDtoList;
    }
}
