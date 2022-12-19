package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.customexception.InvalidEmailException;
import ru.yandex.practicum.catsgram.customexception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashMap;

@Service
@Slf4j
public class UserService {
    private HashMap<String, User> users = new HashMap<>();

    public HashMap<String, User> getUsers() {
        log.debug("Количество пользователей в текущий момент {}", users.values().size());
        return users;
    }

    public void createUser(User user) {
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
        } catch (UserAlreadyExistException | InvalidEmailException e) {
            System.out.printf(e.getMessage());
        }
    }

    public void changeUser(User user) {
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

    public User findUserByEmail(String email) {
        return users.get(email);
    }

}
