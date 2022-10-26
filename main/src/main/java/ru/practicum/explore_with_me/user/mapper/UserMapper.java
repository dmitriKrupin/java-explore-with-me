package ru.practicum.explore_with_me.user.mapper;

import ru.practicum.explore_with_me.user.dto.UserDto;
import ru.practicum.explore_with_me.user.dto.UserShortDto;
import ru.practicum.explore_with_me.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static List<UserDto> toUserDtoOutList(List<User> userList) {
        List<UserDto> userDtoOutList = new ArrayList<>();
        for (User entry : userList) {
            userDtoOutList.add(UserMapper.toUserDto(entry));
        }
        return userDtoOutList;
    }
}
