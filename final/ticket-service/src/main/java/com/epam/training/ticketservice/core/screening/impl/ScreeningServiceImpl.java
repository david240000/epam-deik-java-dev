package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.reposytory.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.reposytory.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository,
                                MovieRepository movieRepository,
                                RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<ScreeningDto> getScreeningList() {
        return screeningRepository
                .findAll()
                .stream()
                .map(this::convertEntityToScreeningDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createScreening(String movieTitle, String roomName, LocalDateTime date) {
        Objects.requireNonNull(movieTitle, "Move name cannot be null");
        Objects.requireNonNull(roomName, "Room name cannot be null");
        Objects.requireNonNull(date, "Date cannot be null");
        Movie movie = movieRepository.findByTitle(movieTitle).get();
        Room room = roomRepository.findByName(roomName).get();
        Screening newScreening = new Screening(movie, room, date);
        List<Screening> screeningsInRoom = screeningRepository.findByRoom(room);
        screeningsInRoom.forEach(screening -> checkRoomIsFree(screening,newScreening));
        screeningRepository.save(newScreening);

    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, LocalDateTime date) {
        Objects.requireNonNull(movieTitle, "Move name cannot be null");
        Objects.requireNonNull(roomName, "Room name cannot be null");
        Objects.requireNonNull(date, "Date cannot be null");
        Movie movie = movieRepository.findByTitle(movieTitle).get();
        Room room = roomRepository.findByName(roomName).get();
        screeningRepository.findByMovieAndRoomAndDate(movie, room, date);

    }

    private void checkRoomIsFree(Screening existScreening, Screening newScreening) {
        final Integer pause = 10;
        if (existScreening.getDate().isBefore(newScreening.getDate()))  {
            if (existScreening.getDate().plusMinutes(existScreening.getMovie().getLength())
                    .isAfter(newScreening.getDate())) {
                throw new IllegalArgumentException("There is an overlapping screening");
            }
            if (existScreening.getDate().plusMinutes(existScreening.getMovie().getLength()).plusMinutes(pause)
                    .isAfter(newScreening.getDate())) {
                throw new IllegalArgumentException("This would start in the break"
                       + " period after another screening in this room");
            }
        }
    }


    private ScreeningDto convertEntityToScreeningDto(Screening screening) {
        return ScreeningDto.builder()
                .movie(screening.getMovie())
                .room(screening.getRoom())
                .date(screening.getDate())
                .build();
    }

    private Optional<ScreeningDto> convertEntityToScreeningDto(Optional<Screening> screening) {
        return screening.isEmpty() ? Optional.empty() : Optional.of(convertEntityToScreeningDto(screening.get()));
    }
}
