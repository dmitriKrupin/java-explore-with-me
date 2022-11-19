package ru.practicum.explore_with_me.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.user.dto.UserDto;
import ru.practicum.explore_with_me.user.service.UserService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    private List<UserDto> getAllUsers(
            @RequestParam(required = false, name = "ids") List<Long> ids) {
        log.info("Получаем GET запрос к эндпойнту /admin/users");
        return userService.getAllUsers(ids);
    }

    @PostMapping
    private UserDto addUser(@Validated @RequestBody UserDto userDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/users");
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{userId}")
    private void deleteUser(@Validated @PositiveOrZero @PathVariable Long userId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/users/{}", userId);
        userService.deleteUser(userId);
    }
}
