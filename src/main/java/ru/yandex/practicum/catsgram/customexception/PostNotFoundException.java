package ru.yandex.practicum.catsgram.customexception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(final String message) {
        super(message);
    }
}
