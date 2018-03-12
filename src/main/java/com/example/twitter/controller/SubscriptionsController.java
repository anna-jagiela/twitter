package com.example.twitter.controller;

import com.example.twitter.controller.exception.ConflictException;
import com.example.twitter.controller.exception.NotFoundException;
import com.example.twitter.controller.model.FollowMessage;
import com.example.twitter.model.Subscription;
import com.example.twitter.service.SubscriptionsService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {

    private final SubscriptionsService subscriptionsService;

    @Autowired
    public SubscriptionsController(@NonNull final SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public Subscription postSubscription(@RequestBody FollowMessage followMessage,
                                         @RequestHeader(value = "Authorization") String followerName) {
        final String followeeName = followMessage.getFollowee();
        verifySubscription(followerName, followeeName);
        return subscriptionsService.createSubscription(followerName, followeeName)
                                   .orElseThrow(() -> new NotFoundException("Follower or Followee does not exists"));
    }

    private void verifySubscription(@NonNull final String followerName,
                                    @NonNull final String followeeName) {
        if(followerName.equals(followeeName)) {
            throw new ConflictException("Followee name should be different than follower name");
        }
    }

}
