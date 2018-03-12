package com.example.twitter.controller;

import com.example.twitter.model.Tweet;
import com.example.twitter.service.TimeLineService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@RestController
@RequestMapping("/timeline")
public class TimeLineController {

    private final TimeLineService timeLineService;

    @Autowired
    public TimeLineController(@NonNull final TimeLineService timeLineService) {
        this.timeLineService = timeLineService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Tweet> listMessages(@RequestHeader(value = "Authorization") String userName) {
        return timeLineService.tweetsForUser(userName);
    }

}
