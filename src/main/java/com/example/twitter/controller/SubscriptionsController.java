package com.example.twitter.controller;

import com.example.twitter.controller.exception.NotFoundException;
import com.example.twitter.model.Subscription;
import com.example.twitter.service.SubscriptionsService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {

    private final SubscriptionsService subscriptionsService;

    @Autowired
    public SubscriptionsController(@NonNull final SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public Subscription postSubscription(@RequestBody String followeeName,
                                         @RequestHeader(value = "Authorization") String followerName) {
        return subscriptionsService.createSubscription(followerName, followeeName)
                                   .orElseThrow(NotFoundException::new);
    }

}
