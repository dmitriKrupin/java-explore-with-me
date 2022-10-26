package ru.practicum.explore_with_me.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {
    @Autowired
    private RequestService requestService;

    @GetMapping
    private List<ParticipationRequestDto> getAllRequestsByUserInAnotherEvents(
            @PathVariable Long userId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/requests", userId);
        return requestService.getAllRequestsByUserInAnotherEvents(userId);
    }

    @PostMapping
    private ParticipationRequestDto addRequestByUserInAnotherEvent(
            @PathVariable Long userId,
            @RequestParam Long eventId) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/requests", userId);
        return requestService.addRequestByUserInAnotherEvent(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    private ParticipationRequestDto canceledOwnRequestByUser(
            @PathVariable Long userId,
            @PathVariable Long requestId) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/requests/{}/cancel", userId, requestId);
        return requestService.canceledOwnRequestByUser(userId, requestId);
    }
}
