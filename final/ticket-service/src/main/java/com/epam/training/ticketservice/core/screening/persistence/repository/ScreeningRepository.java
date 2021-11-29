package com.epam.training.ticketservice.core.screening.persistence.repository;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    Optional<Screening> findByMovieAndRoomAndDate(Movie movie, Room rorm, LocalDateTime date);

    List<Screening> findByRoom(Room room);

}
