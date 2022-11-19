package ru.practicum.explore_with_me.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {
    //Пользователь
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
}
