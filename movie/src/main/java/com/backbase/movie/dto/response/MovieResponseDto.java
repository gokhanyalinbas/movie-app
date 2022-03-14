package com.backbase.movie.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseDto {

    private String id;
    private String title;
    private String additionalInfo;
    private String year;
    private double rate;
    private String boxOffice;

}
