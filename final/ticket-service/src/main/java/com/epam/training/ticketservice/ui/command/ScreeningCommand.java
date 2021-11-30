package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ScreeningCommand {
    private final AvailabilityCheck availabilityCheck;
    private final ScreeningService screeningService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ScreeningCommand(AvailabilityCheck availabilityCheck, ScreeningService screeningService) {
        this.availabilityCheck = availabilityCheck;
        this.screeningService = screeningService;
    }


    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Creates a screening")
    public String create(String movieTitle, String roomName, String date) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return "Parse fail YYYY-MM-DD hh:mm";
        }
        try {
            screeningService.createScreening(movieTitle, roomName, localDateTime);
            return "Created screening: " + movieTitle + roomName + localDateTime;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Deletes a screening")
    public String delete(String movieTitle, String roomName, String date) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return "Parse fail YYYY-MM-DD hh:mm";
        }
        screeningService.deleteScreening(movieTitle, roomName, localDateTime);
        return "Deleted screening: " + movieTitle + roomName + date;
    }

    @ShellMethod(key = "list screenings", value = "Lists all screenings")
    public String list() {
        List<ScreeningDto> screeningDtoList = screeningService.getScreeningList();
        if (screeningDtoList.isEmpty()) {
            return "There are no screenings";
        }
        return screeningDtoList
                .stream()
                .map(screeningDto -> screeningDto.toString())
                .collect(Collectors.joining("\n"));
    }

    private Availability isAvailable() {
        return availabilityCheck.isAvailable();
    }
}