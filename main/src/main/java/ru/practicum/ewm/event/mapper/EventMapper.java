package ru.practicum.ewm.event.mapper;

import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventMapper {
    public static EventShortDto toEventShortDto(
            Event event, Category category, Long views) {
        return new EventShortDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(category),
                event.getConfirmedRequests(),
                event.getEventDate().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                views
        );
    }

    public static List<EventShortDto> toEventShortDtoList(
            Map<Event, Long> viewsOfEvents) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Map.Entry<Event, Long> entry : viewsOfEvents.entrySet()) {
            EventShortDto eventShortDto = EventMapper
                    .toEventShortDto(entry.getKey(), entry.getKey().getCategory(), entry.getValue());
            eventShortDtoList.add(eventShortDto);
        }
        return eventShortDtoList;
    }

    public static EventFullDto toEventFullDto(
            Event event, Category category, Long views) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(category),
                event.getConfirmedRequests(),
                event.getCreatedOn().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getDescription(),
                event.getEventDate().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                String.valueOf(event.getPublishedOn()),
                event.getRequestModeration(),
                String.valueOf(event.getState()),
                event.getTitle(),
                views
        );
    }

    public static EventFullDto toPublishedEventFullDto(
            Event event, Category category, Long views) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(category),
                event.getConfirmedRequests(),
                event.getCreatedOn().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getDescription(),
                event.getEventDate().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getRequestModeration(),
                String.valueOf(event.getState()),
                event.getTitle(),
                views
        );
    }

    public static List<EventFullDto> toEventFullDtoList(
            Map<Event, Long> viewsOfEvents) {
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (Map.Entry<Event, Long> entry : viewsOfEvents.entrySet()) {
            EventFullDto eventFullDto = EventMapper
                    .toEventFullDto(entry.getKey(), entry.getKey().getCategory(), entry.getValue());
            eventFullDtoList.add(eventFullDto);
        }
        return eventFullDtoList;
    }

    public static Event toNewEvent(
            NewEventDto newEventDto, Category category, User user, Status state,
            Long confirmedRequests) {
        return new Event(
                newEventDto.getTitle(),
                newEventDto.getAnnotation(),
                category,
                newEventDto.getPaid(),
                LocalDateTime.parse(newEventDto.getEventDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                user,
                confirmedRequests,
                newEventDto.getDescription(),
                newEventDto.getParticipantLimit(),
                state,
                LocalDateTime.parse(newEventDto.getEventDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                null,
                newEventDto.getLocation(),
                newEventDto.getRequestModeration());
    }
}
