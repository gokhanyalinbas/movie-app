package com.backbase.movie.service.impl;

import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.model.file.RecordModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileServiceImplTest {

    private FileServiceImpl fileService = new FileServiceImpl();

    @Test
    void readFile() throws IOException, URISyntaxException {
        List<RecordModel> recordModelList = fileService.readFile(ApplicationConstant.INPUT_FILEPATH);
        assertEquals(true, recordModelList.size() > 0);
    }
}