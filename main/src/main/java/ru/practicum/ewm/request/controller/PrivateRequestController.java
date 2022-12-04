package ru.practicum.ewm.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {
    private final RequestService requestService;

    @Autowired
    public PrivateRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<ParticipationRequestDto> getAllRequestsByUserInAnotherEvents(
            @Valid @Positive @PathVariable Long userId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/requests", userId);
        return requestService.getAllRequestsByUserInAnotherEvents(userId);
    }

    @PostMapping
    public ParticipationRequestDto addRequestByUserInAnotherEvent(
            @Valid @Positive @PathVariable Long userId,
            @Valid @Positive @RequestParam Long eventId) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/requests", userId);
        return requestService.addRequestByUserInAnotherEvent(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto canceledOwnRequestByUser(
            @Valid @Positive @PathVariable Long userId,
            @Valid @Positive @PathVariable Long requestId) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/requests/{}/cancel", userId, requestId);
        return requestService.canceledOwnRequestByUser(userId, requestId);
    }
}
