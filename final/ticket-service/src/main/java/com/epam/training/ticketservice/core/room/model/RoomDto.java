package com.epam.training.ticketservice.core.room.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class RoomDto {

    private final String name;

    private final Integer rowNumber;

    private final Integer colNumber;

    public Integer getSeatsNumber() {
        return rowNumber * colNumber;
    }

    @Override
    public String toString() {
        return "Room " + name + " with " + getSeatsNumber()
                + " seats, " + rowNumber + " rows and " + colNumber + " columns";
    }
}