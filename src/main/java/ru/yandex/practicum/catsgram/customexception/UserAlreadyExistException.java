package ru.yandex.practicum.catsgram.customexception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(final String message) {
        super(message);
    }
}
