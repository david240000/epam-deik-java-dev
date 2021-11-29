package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScreeningService {

    List<ScreeningDto> getScreeningList();

    void createScreening(String movieTitle, String roomName, LocalDateTime date);

    void deleteScreening(String movieTitle, String roomName, LocalDateTime date);
}
