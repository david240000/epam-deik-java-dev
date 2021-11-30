package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;
    private final AvailabilityCheck availabilityCheck;

    public RoomCommand(RoomService roomService, AvailabilityCheck availabilityCheck) {
        this.roomService = roomService;
        this.availabilityCheck = availabilityCheck;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Create a room")
    public String create(String name, int rowNumber, int colNumber) {
        RoomDto room = new RoomDto(name, rowNumber, colNumber);
        roomService.createRoom(room);
        return "Created room: " + room.toString();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Update a room")
    public String update(String name, int rowNumber, int colNumber) {
        RoomDto room = new RoomDto(name, rowNumber, colNumber);
        roomService.updateRoom(room);
        return "Updated room: " + room.toString();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Delete a room")
    public String delete(String name) {
        roomService.deleteRoom(name);
        return "Deleted room: " + name;
    }

    @ShellMethod(key = "list rooms", value = "Lists all rooms")
    public String list() {
        List<RoomDto> roomDtoList = roomService.getRoomList();
        if (roomDtoList.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return roomDtoList
                .stream()
                .map(roomDto -> roomDto.toString())
                .collect(Collectors.joining("\n"));
    }

    private Availability isAvailable() {
        return availabilityCheck.isAvailable();
    }
}
