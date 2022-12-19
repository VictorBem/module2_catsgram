package ru.yandex.practicum.catsgram.service;


import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.customexception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(ru.yandex.practicum.catsgram.controller.PostController.class);
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

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
                log.debug("Текущий объект {}", post);
                posts.add(post);
            }
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
        }
    }
}