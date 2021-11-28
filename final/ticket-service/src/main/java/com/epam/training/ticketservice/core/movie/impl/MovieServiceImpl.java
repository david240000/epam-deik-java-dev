package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.reposytory.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll().stream().map(this::convertEntityToMovieDto).collect(Collectors.toList());
    }

    @Override
    public void createMovie(MovieDto movie) {
        Objects.requireNonNull(movie, "Movie cannot be null");
        Objects.requireNonNull(movie.getTitle(), "Movie title cannot be null");
        Objects.requireNonNull(movie.getGenre(), "Movie genre cannot be null");
        Objects.requireNonNull(movie.getLength(), "Movie length cannot be null");
        Movie newMovie =  new Movie(movie.getTitle(), movie.getGenre(), movie.getLength());
        movieRepository.save((newMovie));
    }

    @Override
    public void updateMovie(MovieDto movie) {
        Objects.requireNonNull(movie, "Movie cannot be null");
        Objects.requireNonNull(movie.getTitle(), "Movie title cannot be null");
        Objects.requireNonNull(movie.getGenre(), "Movie genre cannot be null");
        Objects.requireNonNull(movie.getLength(), "Movie length cannot be null");
        Optional<Movie> updatedMovie = movieRepository.findByTitle(movie.getTitle());
        updatedMovie.get().setGenre(movie.getGenre());
        updatedMovie.get().setLength(movie.getLength());
        movieRepository.save(updatedMovie.get());

    }

    @Override
    public void deleteMovie(String movieTitle) {
        Objects.requireNonNull(movieTitle, "Movie title cannot be null");
        Optional<Movie> deletedMovie = movieRepository.findByTitle(movieTitle);
        movieRepository.delete(deletedMovie.get());

    }

    private MovieDto convertEntityToMovieDto(Movie movie) {
        return MovieDto.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .length(movie.getLength())
                .build();
    }

    private Optional<MovieDto> convertEntityToMovieDto(Optional<Movie> movie) {
        return movie.isEmpty() ? Optional.empty() : Optional.of(convertEntityToMovieDto(movie.get()));
    }
}
