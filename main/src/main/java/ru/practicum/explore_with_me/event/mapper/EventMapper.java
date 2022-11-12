package ru.practicum.explore_with_me.event.mapper;

import ru.practicum.explore_with_me.category.mapper.CategoryMapper;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.dto.NewEventDto;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.user.mapper.UserMapper;
import ru.practicum.explore_with_me.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static EventShortDto toEventShortDto(Event event, Category category) {
        return new EventShortDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(category),
                event.getConfirmedRequests(),
                String.valueOf(event.getEventDate()),
                event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<EventShortDto> toEventShortDtoList(
            List<Event> events) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Event entry : events) {
            eventShortDtoList.add(EventMapper.toEventShortDto(entry, entry.getCategory()));
        }
        return eventShortDtoList;
    }

    public static EventFullDto toEventFullDto(Event event, Category category) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(category),
                event.getConfirmedRequests(),
                String.valueOf(event.getCreatedOn()),
                event.getDescription(),
                String.valueOf(event.getEventDate()),
                event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                String.valueOf(event.getPublishedOn()),
                event.getRequestModeration(),
                String.valueOf(event.getState()),
                event.getTitle(),
                event.getViews()
        );
    }

    public static List<EventFullDto> toEventFullDtoList(List<Event> events) {
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (Event entry : events) {
            eventFullDtoList.add(EventMapper.toEventFullDto(entry, entry.getCategory()));
        }
        return eventFullDtoList;
    }

    public static Event toNewEvent(
            NewEventDto newEventDto, Category category, User user) {
        return new Event(
                newEventDto.getTitle(),
                newEventDto.getAnnotation(),
                category,
                newEventDto.getPaid(),
                LocalDateTime.parse(newEventDto.getEventDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                user,
                null,
                null,
                newEventDto.getDescription(),
                newEventDto.getParticipantLimit(),
                null,
                LocalDateTime.now(),
                null,
                newEventDto.getLocation(),
                newEventDto.getRequestModeration());
    }
}