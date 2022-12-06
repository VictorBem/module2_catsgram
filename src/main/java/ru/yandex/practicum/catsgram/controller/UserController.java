package ru.yandex.practicum.catsgram.controller;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.customexception.InvalidEmailException;
import ru.yandex.practicum.catsgram.customexception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private HashMap<String, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController() {
       // ((ch.qos.logback.classic.Logger) log).setLevel(Level.DEBUG);
    }

    @GetMapping()
    HashMap<String, User> getUsers() {
        log.debug("Количество пользователей в текущий момент {}", users.values().size());
        return users;
    }

    @PostMapping()
    public void createUser(@RequestBody User user) {
        try {
            for (User currentUser : users.values()) {
                if (currentUser.getEmail().equals(user.getEmail())) {
                    throw new UserAlreadyExistException("Пользователь уже существует.");
                }
                if(currentUser.getEmail() == null || currentUser.getEmail().isEmpty()) {
                    throw new InvalidEmailException("Некорректный адрес электронной почты.");
                }
            }
            users.put(user.getEmail(), user);
            log.debug("Сохраняется объект: {}", user);
            //return user;
        } catch (UserAlreadyExistException | InvalidEmailException e) {
            System.out.printf(e.getMessage());
            //return null;
        }
    }

    @PutMapping
    public void changeUser(@RequestBody User user) {
        try {
            if(user.getEmail() == null || user.getEmail().isEmpty()) {
                throw new InvalidEmailException("Некорректный адрес электронной почты.");
            }
        } catch (InvalidEmailException e) {
            System.out.printf(e.getMessage());
            //return null;
        }

        users.put(user.getEmail(), user);
        log.debug("Сохраняется объект: {}", user);
        //return user;
    }
}
