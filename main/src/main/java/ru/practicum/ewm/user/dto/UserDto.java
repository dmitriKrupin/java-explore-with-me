package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {
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
