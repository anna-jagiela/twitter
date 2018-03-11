package com.example.twitter.service;

import com.example.twitter.model.Subscription;
import com.example.twitter.model.User;
import com.example.twitter.repository.SubscriptionsRepository;
import com.example.twitter.repository.UsersRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionsService {

    private final SubscriptionsRepository subscriptionsRepository;

    private final UsersRepository usersRepository;

    @Autowired
    public SubscriptionsService(@NonNull final SubscriptionsRepository subscriptionsRepository,
                                @NonNull final UsersRepository usersRepository) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.usersRepository = usersRepository;
    }

    public List<Subscription> subscriptionsForUser(final long userId) {
        return subscriptionsRepository.findByFollowerId(userId);
    }

    public Optional<Subscription> createSubscription(@NonNull final String followerName,
                                                     @NonNull final String followeeName) {
        Optional<User> follower = usersRepository.findByName(followerName);
        Optional<User> followee = usersRepository.findByName(followeeName);

        if (follower.isPresent() && followee.isPresent()) {
            return Optional.of(getOrCreateSubscription(follower.get().getId(),
                                                       followee.get().getId()));
        }
        return Optional.empty();
    }

    private Subscription getOrCreateSubscription(long followerId, long followeeId) {
        return subscriptionsRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                                      .orElse(subscriptionsRepository.save(new Subscription(followerId, followeeId)));
    }

}
