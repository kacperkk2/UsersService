package kacper.klimczuk.usersservice.controllers;

import kacper.klimczuk.usersservice.models.dto.UserDTO;
import kacper.klimczuk.usersservice.services.StatsService;
import kacper.klimczuk.usersservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatsService statsService;

    @GetMapping("/users/{login}")
    public UserDTO getUserInfoByLogin(@PathVariable String login) {
        UserDTO userDTO = userService.getUserInfo(login);
        statsService.upsertStatistics(login);
        return userDTO;
    }
}
