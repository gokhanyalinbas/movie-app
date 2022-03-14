package com.backbase.movie.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebUserDto {
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;

}
