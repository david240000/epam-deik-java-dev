package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.reposytory.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.reposytory.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ScreeningServiceImplTest {

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);

    @Test
    void getScreeningListShouldReturnOptionalScreeningDtoListWhenScreeningExist() {
        // Given
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        List<ScreeningDto> expected = List.of(screeningDto);
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(screeningRepository.findAll()).thenReturn(List.of(screening));
        // When
        List<ScreeningDto> actual = underTest.getScreeningList();
        // Then
        assertEquals(expected, actual);
        verify(screeningRepository).findAll();
    }

    @Test
    void createScreeningShouldCreateScreeningWhenCreateScreeningCalled() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(screeningRepository.findByRoom(room)).thenReturn(Collections.emptyList());
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        // When
        underTest.createScreening(screening.getMovie().getTitle(),screening.getRoom().getName(), screening.getDate());
        // Then
        verify(screeningRepository).save(screening);
        verify(movieRepository).findByTitle(screening.getMovie().getTitle());
        verify(roomRepository).findByName(screening.getRoom().getName());
        verify(screeningRepository).findByRoom(room);
    }

    @Test
    void createScreeningShouldThrowExceptionWhenMovieNotExist() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(IllegalArgumentException.class, ()->underTest.createScreening(screening.getMovie().getTitle(),screening.getRoom().getName(), screening.getDate()));
        verify(movieRepository).findByTitle(movie.getTitle());
    }

    @Test
    void createScreeningShouldThrowExceptionWhenRoomNotExist() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(IllegalArgumentException.class, ()->underTest.createScreening(screening.getMovie().getTitle(),screening.getRoom().getName(), screening.getDate()));
        verify(movieRepository).findByTitle(movie.getTitle());
        verify(roomRepository).findByName(room.getName());
    }

    @Test
    void createScreeningShouldThrowExceptionWhenTheRoomIsBusy() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findByRoom(room)).thenReturn(List.of(screening));
        // When
        // Then
        assertThrows(IllegalArgumentException.class, ()->underTest.createScreening(screening.getMovie().getTitle(),screening.getRoom().getName(), screening.getDate()));
        verify(movieRepository).findByTitle(movie.getTitle());
        verify(roomRepository).findByName(room.getName());
        verify(screeningRepository).findByRoom(room);
    }

    @Test
    void createScreeningShouldThrowExceptionWhenPause() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 10);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findByRoom(room)).thenReturn(List.of(screening));
        // When
        // Then
        assertThrows(IllegalArgumentException.class, ()->underTest.createScreening("title", room.getName(), LocalDateTime.of(2000,1,1,11,23)));
        verify(movieRepository).findByTitle(movie.getTitle());
        verify(roomRepository).findByName(room.getName());
        verify(screeningRepository).findByRoom(room);
    }

    @Test
    void deleteScreeningShouldDeleteScreeningWhenDeleteScreeningCalled() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.of(room));
        when(screeningRepository.findByMovieAndRoomAndDate(movie, room, screening.getDate())).thenReturn(Optional.of(screening));
        // When
        underTest.deleteScreening(movie.getTitle(), room.getName(), screening.getDate()) ;
        // Then
        verify(movieRepository).findByTitle(movie.getTitle());
        verify(screeningRepository).delete(screening);
        verify(roomRepository).findByName(room.getName());
        verify(screeningRepository).findByMovieAndRoomAndDate(movie, room, screening.getDate());
    }

    @Test
    void deleteScreeningShouldThrowExceptionWhenMovieNotExist() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(IllegalArgumentException.class, ()->underTest.deleteScreening(screening.getMovie().getTitle(),screening.getRoom().getName(), screening.getDate()));
        verify(movieRepository).findByTitle(movie.getTitle());
    }

    @Test
    void deleteScreeningShouldThrowExceptionWhenRoomNotExist() {
        ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie movie = new Movie("title", "genre", 123);
        Room room = new Room("name", 10, 10);
        ScreeningDto screeningDto = new ScreeningDto(movie,room, LocalDateTime.of(2000,1,1,11,11));
        Screening screening = new Screening(movie,room, LocalDateTime.of(2000,1,1,11,11));
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));
        when(roomRepository.findByName(room.getName())).thenReturn(Optional.empty());
        // When
        // Then
        assertThrows(IllegalArgumentException.class, ()->underTest.deleteScreening(screening.getMovie().getTitle(),screening.getRoom().getName(), screening.getDate()));
        verify(movieRepository).findByTitle(movie.getTitle());
        verify(roomRepository).findByName(room.getName());
    }
}