package ru.yandex.practicum.catsgram.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class FriendsParams {
    private String sort;
    private Integer size;
    private List<String> friends;
}