package com.backbase.movie.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RateRequestDto {
    @DecimalMax(value = "10", message = "Rate can be maximum 10.")
    @DecimalMin(value = "1", message = "Rate can be minimum 1.")
    private int userRate;
    private String userId;
    private String userName;
    @NotNull
    @NotEmpty
    private String title;
}
