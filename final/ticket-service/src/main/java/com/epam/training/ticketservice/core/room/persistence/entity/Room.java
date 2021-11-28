package com.epam.training.ticketservice.core.room.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    private Integer rowNumber;

    private Integer colNumber;


    public Room(String name, Integer rowNumber, Integer colNumber) {
        this.name = name;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
    }
}
