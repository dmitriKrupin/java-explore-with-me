package ru.practicum.explore_with_me.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserShortDto {
    //Пользователь (краткая информация)
    private Long id;
    private String name;
}
