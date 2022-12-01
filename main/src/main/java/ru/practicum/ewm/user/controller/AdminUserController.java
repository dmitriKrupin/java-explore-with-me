package ru.practicum.ewm.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestParam(required = false, name = "ids") List<Long> ids) {
        log.info("Получаем GET запрос к эндпойнту /admin/users");
        return userService.getAllUsers(ids);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/users");
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@Validated @Positive @PathVariable Long userId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/users/{}", userId);
        userService.deleteUser(userId);
    }
}
