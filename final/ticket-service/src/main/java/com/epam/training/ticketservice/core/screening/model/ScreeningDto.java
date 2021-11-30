package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class ScreeningDto {

    private Movie movie;

    private Room room;

    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    private LocalDateTime date;

    @Override
    public String toString() {
        return movie.getTitle() + " (" + movie.getGenre() + ", " +  movie.getLength() + " minutes), screened in room "
                + room.getName() + ", at " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    }
}
