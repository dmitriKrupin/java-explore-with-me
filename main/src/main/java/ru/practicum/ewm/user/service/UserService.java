package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(List<Long> ids);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);
}
