package com.example.twitter

import com.example.twitter.model.Tweet
import com.example.twitter.model.User
import com.example.twitter.repository.TweetsRepository
import com.example.twitter.repository.UsersRepository
import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.beans.factory.annotation.Autowired

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TwitterSpec extends TwitterBaseSpec {

    @Autowired
    UsersRepository usersRepository

    @Autowired
    TweetsRepository tweetsRepository

    def "users should be able to post tweets"() {
        when:
        def postResult = postToWall("user1", "First tweet of user1")
        def firstTweet = dejsonize(content(postResult), Tweet)

        then:
        firstTweet.user.id == 1
        firstTweet.user.name == "user1"
        firstTweet.message == "First tweet of user1"

        and:
        usersRepository.count() == 1
        tweetsRepository.count() == 1

        when:
        def anotherPostResult = postToWall("user2" , "First tweet of user2")
        def secondTweet = dejsonize(content(anotherPostResult), Tweet)

        then:
        secondTweet.user.id == 2
        secondTweet.user.name == "user2"
        secondTweet.message == "First tweet of user2"

        and:
        usersRepository.count() == 2
        tweetsRepository.count() == 2
    }

    def "user should be able to see the tweets that were posted in reverse chronological order"() {
        given:
        postToWall("userA", "First tweet of userA")
        postToWall("userB", "First tweet of userB")
        postToWall("userA", "Second tweet of userA")

        when:
        def tweets = dejsonize(content(readFromWall("userA")), new TypeReference<List<Tweet>>(){})

        then:
        tweets.size() == 2
        tweets.get(0).message == "Second tweet of userA"
        tweets.get(1).message == "First tweet of userA"
    }

    def "should allow users to follow other users and see their tweets"() {
        given:
        postToWall("user1", "First tweet of user1")
        postToWall("user2", "First tweet of user2")
        postToWall("user1", "Second tweet of user1")

        when:
        def otherUsers = dejsonize(content(listOtherUsers("user2")), new TypeReference<List<User>>() {})

        then:
        otherUsers.size() == 1

        when:
        followUser("user2", otherUsers.first().name)
        def timeLineMessages = dejsonize(content(readFromTimeLine("user2")), new TypeReference<List<Tweet>>() {})

        then:
        timeLineMessages.size() == 2
        timeLineMessages.get(0).message == "Second tweet of user1"
        timeLineMessages.get(1).message == "First tweet of user1"
    }

    def "user should not be able to post tweets longer than 140 characters"() {
        expect:
        postToWall("user", randomDescription(141), status().isUnprocessableEntity())
    }

    def "user should not be able to follow non-existing user"() {
        given:
        postToWall("user1", "First tweet of user1")

        expect:
        followUser("user1", "user2", status().isNotFound())
    }

    def "user should not be able to follow himself"() {
        given:
        postToWall("user1", "First tweet of user1")

        expect:
        followUser("user1", "user1", status().isConflict())
    }
    
}