package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.reposytory.MovieRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    private final MovieRepository movieRepository = mock(MovieRepository.class);

    @Test
    void getMovieListShouldReturnOptionalMovieDtoListWhenMovieExist() {
        // Given
        MovieService underTest = new MovieServiceImpl(movieRepository);
        MovieDto movieDto = new MovieDto("title","genre", 123);
        List<MovieDto> expected = List.of(movieDto);
        Movie movie = new Movie("title","genre", 123);
        when(movieRepository.findAll()).thenReturn(List.of(movie));
        // When
        List<MovieDto> actual = underTest.getMovieList();
        // Then
        assertEquals(expected, actual);
        verify(movieRepository).findAll();
    }

    @Test
    void createMovieShouldCreateMovieWhenCreateMovieCalled() {
        MovieService underTest = new MovieServiceImpl(movieRepository);
        MovieDto movieDto = new MovieDto("title","genre", 123);
        Movie movie = new Movie("title","genre", 123);
        // When
        underTest.createMovie(movieDto);
        // Then
        verify(movieRepository).save(movie);
    }

    @Test
    void updateMovieShouldUpdateMovieWhenUpdateMovieCalled() {
        MovieService underTest = new MovieServiceImpl(movieRepository);
        MovieDto movieDto = new MovieDto("title","genre", 123);
        Movie movie = new Movie("title","genre", 123);
        when(movieRepository.findByTitle(movieDto.getTitle())).thenReturn(Optional.of(movie));
        // When
        underTest.updateMovie(movieDto);
        // Then
        verify(movieRepository).findByTitle(movieDto.getTitle());
        verify(movieRepository).save(movie);
    }

    @Test
    void deleteMovieShouldDeleteMovieWhenDeleteMovieCalled() {
        MovieService underTest = new MovieServiceImpl(movieRepository);
        String title = "title";
        Movie movie = new Movie("title","genre", 123);
        when(movieRepository.findByTitle(title)).thenReturn(Optional.of(movie));
        // When
        underTest.deleteMovie(title);
        // Then
        verify(movieRepository).findByTitle(title);
        verify(movieRepository).delete(movie);
    }
}