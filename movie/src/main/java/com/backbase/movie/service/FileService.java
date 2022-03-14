package com.backbase.movie.service;


import com.backbase.movie.model.file.RecordModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface FileService {
    List<RecordModel> readFile(String fileName) throws IOException, URISyntaxException;


}
