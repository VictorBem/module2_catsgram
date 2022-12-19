package ru.yandex.practicum.catsgram.controller;

import ch.qos.logback.classic.Level;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll() {
        log.debug("Текущее количество постов: {}", postService.findAll().size());
        return postService.findAll();
    }

    @PostMapping("/post")
    public void create(@RequestBody Post post) {
        log.debug("Текущий объект {}", post);
        postService.create(post);
    }

    @GetMapping("/posts/{postId}")
    public Optional<Post> findById(@PathVariable int postId) {
        return postService.findById(postId);
    }
}