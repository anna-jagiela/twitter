package com.example.twitter.service;

import com.example.twitter.model.Subscription;
import com.example.twitter.model.Tweet;
import com.example.twitter.repository.TweetsRepository;
import com.example.twitter.repository.UsersRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;

@Service
public class TimeLineService {

    private final UsersRepository usersRepository;

    private final TweetsRepository tweetsRepository;

    private final SubscriptionsService subscriptionsService;

    @Autowired
    public TimeLineService(@NonNull final UsersRepository usersRepository,
                           @NonNull final TweetsRepository tweetsRepository,
                           @NonNull final SubscriptionsService subscriptionsService) {
        this.usersRepository = usersRepository;
        this.tweetsRepository = tweetsRepository;
        this.subscriptionsService = subscriptionsService;
    }

    public List<Tweet> tweetsForUser(@NonNull final String userName) {
        List<Subscription> subscriptions = usersRepository.findByName(userName)
                                                          .map(user -> subscriptionsService.subscriptionsForUser(user.getId()))
                                                          .orElse(emptyList());

        return subscriptions.stream()
                            .map(subscription -> tweetsOfTheUser(subscription.getFolloweeId()))
                            .flatMap(List::stream)
                            .sorted(reverseOrder(Comparator.comparing(Tweet::getCreatedAt)))
                            .collect(Collectors.toList());
    }

    private List<Tweet> tweetsOfTheUser(Long userId) {
        return usersRepository.findById(userId)
                              .map(tweetsRepository::findByUser)
                              .orElse(emptyList());
    }

}
