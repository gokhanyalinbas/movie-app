package com.backbase.movie.util;

import com.backbase.movie.entity.Movie;
import com.backbase.movie.model.file.RecordModel;
import com.backbase.movie.service.impl.FileServiceImpl;
import com.backbase.movie.service.impl.MovieDaoServiceImpl;
import com.backbase.movie.service.impl.MovieTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseUtilTest {

    @Mock
    private MovieDaoServiceImpl movieDaoService;
    @Mock
    private FileServiceImpl fileService;
    private DatabaseUtil databaseUtil;
    private RecordModel recordModel;

    @BeforeEach
    void setUp() {
        databaseUtil = new DatabaseUtil(movieDaoService, fileService);
        recordModel = RecordModel.builder()
                .nominee("Title")
                .additionalInfo("Test")
                .category("Best Picture")
                .won(true)
                .year("2010")
                .build();
    }

    @Test
    void dbInit() throws IOException, URISyntaxException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Movie movie = MovieTestUtil.generateMovieByIdAndRate("1", 2);
        when(movieDaoService.createMovie(any(Movie.class))).thenReturn(movie);
        when(movieDaoService.getRecordCount()).thenReturn(0L);
        when(fileService.readFile(any(String.class))).thenReturn(Arrays.asList(recordModel, recordModel, recordModel));
        Method postConstruct = DatabaseUtil.class.getDeclaredMethod("initDb"); // methodName,parameters
        postConstruct.setAccessible(true);
        postConstruct.invoke(databaseUtil);


    }
}