package com.example.twitter.service;

import com.example.twitter.model.Tweet;
import com.example.twitter.model.User;
import com.example.twitter.repository.TweetsRepository;
import com.example.twitter.repository.UsersRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

@Service
public class WallService {

    private final UsersRepository usersRepository;

    private final TweetsRepository tweetsRepository;

    @Autowired
    public WallService(@NonNull final UsersRepository usersRepository,
                       @NonNull final TweetsRepository tweetsRepository) {
        this.usersRepository = usersRepository;
        this.tweetsRepository = tweetsRepository;
    }

    public Tweet createTweet(@NonNull final String userName,
                             @NonNull final String message) {
        User user = usersRepository.findByName(userName)
                                   .orElseGet(()-> usersRepository.save(new User(userName)));
        return tweetsRepository.save(new Tweet(user, message));
    }

    public List<Tweet> tweetsOnTheWall(@NonNull final String userName) {
        List<Tweet> tweets =  usersRepository.findByName(userName)
                                             .map(tweetsRepository::findByUser)
                                             .orElse(emptyList());
        return tweets
                .stream()
                .sorted(reverseOrder(comparing(Tweet::getCreatedAt)))
                .collect(Collectors.toList());
    }

}
