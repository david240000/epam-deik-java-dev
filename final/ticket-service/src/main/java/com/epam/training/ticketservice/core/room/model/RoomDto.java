package com.epam.training.ticketservice.core.room.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
public class RoomDto {

    private final String name;

    private final Integer rowNumber;

    private final Integer colNumber;

    public Integer getSeatsNumber() {
        return rowNumber * colNumber;
    }
}