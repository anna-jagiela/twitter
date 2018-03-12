package com.example.twitter.controller;

import com.example.twitter.controller.exception.UnprocessableEntityException;
import com.example.twitter.controller.model.TweetMessage;
import com.example.twitter.model.Tweet;
import com.example.twitter.service.WallService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/wall")
public class WallController {

    private final WallService wallService;

    private final static int MAX_TWEET_SIZE = 140;

    @Autowired
    public WallController(@NonNull final WallService wallService) {
        this.wallService = wallService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8_VALUE)
    public Tweet postMessage(@RequestBody TweetMessage tweet,
                             @RequestHeader(value = "Authorization") String userName) {
        verifyMessageLength(tweet.getMessage());
        return wallService.createTweet(userName, tweet.getMessage());
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Tweet> listMessages(@RequestHeader(value = "Authorization") String userName) {
        return wallService.tweetsOnTheWall(userName);
    }

    private void verifyMessageLength(String message) {
        if (message.length() > MAX_TWEET_SIZE) {
            throw new UnprocessableEntityException("Tweet maximum size is 140 characters");
        }
    }

}
