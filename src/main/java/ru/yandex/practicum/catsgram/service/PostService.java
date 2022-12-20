package ru.yandex.practicum.catsgram.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.customexception.PostNotFoundException;
import ru.yandex.practicum.catsgram.customexception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private int currentPostId = 0;

    PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAllByUserEmail(String email, Integer size, String sort) {
        return posts.stream().filter(p -> email.equals(p.getAuthor())).sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if(sort.equals("desc")){
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).limit(size).collect(Collectors.toList());
    }

    public List<Post> findAll(int size, String sort, int from) {
        List<Post> currentList = null;
        log.debug("Текущее количество постов: {}", posts.size());

        if(posts.size() == 0) {
            return null;
        }

        if(sort.equals("asc")) {
            posts.sort((o1, o2) -> {
                if (o1.getCreationDate().equals(o2.getCreationDate())) {
                    return 0;
                } else {
                    return o1.getCreationDate().isBefore(o2.getCreationDate()) ? -1 : 1;
                }
            });
        } else if(sort.equals("desc")) {
            posts.sort((o1, o2) -> {
                if (o1.getCreationDate().equals(o2.getCreationDate())) {
                    return 0;
                } else {
                    return o1.getCreationDate().isBefore(o2.getCreationDate()) ? 1 : -1;
                }
            });
        }
        if (from + size - 1 > posts.size() ) {
            currentList = posts.subList(from, posts.size());
        } else {
            currentList = posts.subList(from, from + size);
        }
        return currentList;
    }

    public void create(Post post) {
        try {
            if (userService.findUserByEmail(post.getAuthor()).isEmpty()) {
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
        return Optional.of(posts.stream()
                .filter(p -> p.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId))));
    }
}
