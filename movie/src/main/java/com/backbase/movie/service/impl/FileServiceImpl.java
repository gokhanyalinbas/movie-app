package com.backbase.movie.service.impl;

import com.backbase.movie.constant.ApplicationConstant;
import com.backbase.movie.model.file.RecordModel;
import com.backbase.movie.service.FileService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<RecordModel> readFile(String fileName) throws IOException, URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(new File(resource.toURI())))
                .withSkipLines(1)// Skip the  first line - header
                .build();
        return csvReader.readAll().stream().map(this::getModel)
                .collect(Collectors.toList());
    }

    private RecordModel getModel(final String[] data) {
        return RecordModel.builder()
                .year(data[0].substring(0, 4))
                .category(data[1])
                .nominee(data[2])
                .additionalInfo(data[3])
                .won(data[4].equals(ApplicationConstant.YES))
                .build();
    }


}
