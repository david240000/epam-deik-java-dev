package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class MovieCommand {

    private final MovieService movieService;
    private final AvailabilityCheck availabilityCheck;

    public MovieCommand(MovieService movieService, AvailabilityCheck availabilityCheck) {
        this.movieService = movieService;
        this.availabilityCheck = availabilityCheck;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Create a movie")
    public String create(String title, String genre, int length) {
        MovieDto movie = new MovieDto(title, genre, length);
        movieService.createMovie(movie);
        return "Created movie: " + movie.toString();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Update a movie")
    public String update(String title, String genre, int length) {
        MovieDto movie = new MovieDto(title, genre, length);
        movieService.updateMovie(movie);
        return "Updated movie: " + movie.toString();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Delete a movie")
    public String delete(String title) {
        movieService.deleteMovie(title);
        return "Deleted movie: " + title;
    }

    @ShellMethod(key = "list movies", value = "Lists all movies")
    public String list() {
        List<MovieDto> movieDtoList = movieService.getMovieList();
        if (movieDtoList.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movieDtoList
                .stream()
                .map(movieDto -> movieDto.toString())
                .collect(Collectors.joining("\n"));
    }

    private Availability isAvailable() {
        return availabilityCheck.isAvailable();
    }
}
