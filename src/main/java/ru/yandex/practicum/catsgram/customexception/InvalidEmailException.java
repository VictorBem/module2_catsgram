package ru.yandex.practicum.catsgram.customexception;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(final String message) {
        super(message);
    }
}
