package ru.practicum.explore_with_me.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.exception.ConflictException;
import ru.practicum.explore_with_me.exception.NotFoundException;
import ru.practicum.explore_with_me.user.dto.UserDto;
import ru.practicum.explore_with_me.user.mapper.UserMapper;
import ru.practicum.explore_with_me.user.model.User;
import ru.practicum.explore_with_me.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers(List<Long> ids) {
        List<User> allUsersList = userRepository.findAll();
        List<User> allUsersByIds = new ArrayList<>();
        for (User entry : allUsersList) {
            for (Long id : ids) {
                if (entry.getId().equals(id)) {
                    allUsersByIds.add(entry);
                }
            }
        }
        if (allUsersByIds.size() > 0 && ids.size() > 0) {
            return UserMapper.toUserDtoOutList(allUsersByIds);
        } else if (allUsersByIds.size() <= 0 && ids.size() > 0) {
            return UserMapper.toUserDtoOutList(allUsersByIds);
        } else {
            return UserMapper.toUserDtoOutList(allUsersList);
        }
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        List<User> users = userRepository.findAll();
        for (User entry : users) {
            if (entry.getName().equals(userDto.getName())) {
                throw new ConflictException("Пользователь с таким именем " +
                        userDto.getName() + " уже есть в базе!");
            }
        }
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "Такого пользователя c id " + userId + " нет"));
        userRepository.delete(user);
    }

}
