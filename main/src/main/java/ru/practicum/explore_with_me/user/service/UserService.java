package ru.practicum.explore_with_me.user.service;

import ru.practicum.explore_with_me.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(List<Long> ids);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);
}
