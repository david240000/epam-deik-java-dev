package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.reposytory.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public List<RoomDto> getRoomList() {
        return roomRepository.findAll().stream().map(this::convertEntityToRoomDto).collect(Collectors.toList());
    }

    @Override
    public void createRoom(RoomDto room) {
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(room.getName(), "Room name cannot be null");
        Objects.requireNonNull(room.getRowNumber(), "Room row number cannot be null");
        Objects.requireNonNull(room.getColNumber(), "Room col number cannot be null");
        Room newRoom =  new Room(room.getName(), room.getRowNumber(), room.getColNumber());
        roomRepository.save((newRoom));
    }

    @Override
    public void updateRoom(RoomDto room) {
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(room.getName(), "Room name cannot be null");
        Objects.requireNonNull(room.getRowNumber(), "Room row number cannot be null");
        Objects.requireNonNull(room.getColNumber(), "Room col number cannot be null");
        Optional<Room> updatedRoom = roomRepository.findByName(room.getName());
        updatedRoom.get().setRowNumber(room.getRowNumber());
        updatedRoom.get().setColNumber(room.getColNumber());
        roomRepository.save(updatedRoom.get());
    }

    @Override
    public void deleteRoom(String roomName) {
        Objects.requireNonNull(roomName, "Room name cannot be null");
        Optional<Room> deletedRoom = roomRepository.findByName(roomName);
        roomRepository.delete(deletedRoom.get());
    }


    private RoomDto convertEntityToRoomDto(Room room) {
        return RoomDto.builder()
                .name(room.getName())
                .rowNumber(room.getRowNumber())
                .colNumber(room.getColNumber())
                .build();
    }
}
