package com.example.twitter.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FollowMessage {

    private final String followee;

    @JsonCreator
    public FollowMessage(@JsonProperty("followee") final String followee) {
        this.followee = followee;
    }

    public String getFollowee() {
        return followee;
    }

}
