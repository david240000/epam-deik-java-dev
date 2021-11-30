package com.epam.training.ticketservice.core.movie.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class MovieDto {

    private final String title;

    private final String genre;

    private final Integer length;


    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + " minutes)";
    }

}
