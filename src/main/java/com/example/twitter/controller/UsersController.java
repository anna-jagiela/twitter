package com.example.twitter.controller;

import com.example.twitter.model.User;
import com.example.twitter.repository.UsersRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(@NonNull final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> listOtherUsers(@RequestHeader(value = "Authorization") String userName) {
        return copyOf(usersRepository.findAll())
                .stream()
                .filter(user -> !user.getName().equals(userName))
                .collect(toList());
    }

}
