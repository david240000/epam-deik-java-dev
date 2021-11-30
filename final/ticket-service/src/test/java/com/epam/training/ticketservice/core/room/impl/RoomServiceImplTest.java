package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.reposytory.RoomRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class RoomServiceImplTest {

    private final RoomRepository roomRepository = mock(RoomRepository.class);

    @Test
    void getRoomListShouldReturnOptionalRoomDtoListWhenRoomExist() {
        // Given
        RoomService underTest = new RoomServiceImpl(roomRepository);
        RoomDto roomDto = new RoomDto("name", 10, 10);
        List<RoomDto> expected = List.of(roomDto);
        Room room = new Room("name", 10, 10);
        when(roomRepository.findAll()).thenReturn(List.of(room));
        // When
        List<RoomDto> actual = underTest.getRoomList();
        // Then
        assertEquals(expected, actual);
        verify(roomRepository).findAll();
    }

    @Test
    void createRoomShouldCreateRoomWhenCreateRoomCalled() {
        RoomService underTest = new RoomServiceImpl(roomRepository);
        RoomDto roomDto = new RoomDto("title",10, 10);
        Room room = new Room("title",10, 10);
        // When
        underTest.createRoom(roomDto);
        // Then
        verify(roomRepository).save(room);
    }

    @Test
    void updateRoomShouldUpdateRoomWhenUpdateRoomCalled() {
        RoomService underTest = new RoomServiceImpl(roomRepository);
        RoomDto roomDto = new RoomDto("title",10, 10);
        Room room = new Room("title",10, 10);
        when(roomRepository.findByName(roomDto.getName())).thenReturn(Optional.of(room));
        // When
        underTest.updateRoom(roomDto);
        // Then
        verify(roomRepository).findByName(roomDto.getName());
        verify(roomRepository).save(room);
    }

    @Test
    void deleteRoomShouldDeleteRoomWhenDeleteRoomCalled() {
        RoomService underTest = new RoomServiceImpl(roomRepository);
        String name = "name";
        Room room = new Room("title",10, 10);
        when(roomRepository.findByName(name)).thenReturn(Optional.of(room));
        // When
        underTest.deleteRoom(name);
        // Then
        verify(roomRepository).findByName(name);
        verify(roomRepository).delete(room);
    }
}