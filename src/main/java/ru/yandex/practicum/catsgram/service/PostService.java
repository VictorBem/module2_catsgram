package ru.yandex.practicum.catsgram.service;


import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.customexception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(ru.yandex.practicum.catsgram.controller.PostController.class);
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private int currentPostId = 0;

    PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll() {
        ((ch.qos.logback.classic.Logger) log).setLevel(Level.DEBUG);
        log.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }

    public void create(Post post) {
        try {
            if (userService.findUserByEmail(post.getAuthor()) == null) {
                throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
            }
            else {
                post.setId(++currentPostId);
                log.debug("Текущий объект {}", post);
                posts.add(post);
            }
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public Optional<Post> findById(int postId) {
        return posts.stream().filter(x -> x.getId() == postId).findFirst();
    }
}
