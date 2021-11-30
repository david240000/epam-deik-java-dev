package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class UserCommand {

    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(key = "sign in privileged", value = "Admin sign in")
    public String signin(String username, String password) {
        Optional<UserDto> user = userService.login(username, password);
        if (((Optional<?>) user).isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return  "Successfully logged in!";
    }

    @ShellMethod(key = "sign out", value = "Admin sign out")
    public String signout() {
        Optional<UserDto> user = userService.logout();
        if (user.isEmpty()) {
            return "You need to login first!";
        }
        return "Successfully logged out!";
    }

    @ShellMethod(key = "describe account", value = "Get account information")
    public String printLoggedInUser() {
        Optional<UserDto> userDto = userService.getLoggedInUser();
        if (userDto.isEmpty()) {
            return "You are not signed in";
        }
        return "Signed in with privileged account '" + userDto.get().getUsername() + "'";

    }
}
