package ru.practicum.explore_with_me.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.user.dto.UserDto;
import ru.practicum.explore_with_me.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Получаем GET запрос к эндпойнту /admin/users");
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/users");
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/users/{}", userId);
        userService.deleteUser(userId);
    }
}
