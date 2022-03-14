package com.backbase.movie.model.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordModel {
    private String year;
    private String category;
    private String nominee;
    private String additionalInfo;
    private boolean won;
}
