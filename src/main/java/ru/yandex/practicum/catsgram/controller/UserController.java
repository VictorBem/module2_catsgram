package ru.yandex.practicum.catsgram.controller;

import ch.qos.logback.classic.Level;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.customexception.InvalidEmailException;
import ru.yandex.practicum.catsgram.customexception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    HashMap<String, User> getUsers() {
        log.debug("Количество пользователей в текущий момент {}", userService.getUsers().values().size());
        return userService.getUsers();
    }

    @PostMapping()
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping
    public void changeUser(@RequestBody User user) {
        userService.changeUser(user);
    }
}
