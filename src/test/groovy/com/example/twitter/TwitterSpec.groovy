package com.example.twitter

import com.example.twitter.repository.TweetsRepository
import com.example.twitter.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TwitterSpec extends TwitterBaseSpec {

    @Autowired
    UsersRepository usersRepository

    @Autowired
    TweetsRepository tweetsRepository

    def "users should be able to post tweets"() {
        when:
        def firstTweetResult = content(postToWall("user1", "First tweet of user1"))

        then:
        firstTweetResult.user.id == 1
        firstTweetResult.user.name == "user1"
        firstTweetResult.message == "First tweet of user1"

        and:
        usersRepository.count() == 1
        tweetsRepository.count() == 1

        when:
        def secondTweetResult = content(postToWall("user2" , "First tweet of user2"))

        then:
        secondTweetResult.user.id == 2
        secondTweetResult.user.name == "user2"
        secondTweetResult.message == "First tweet of user2"

        and:
        usersRepository.count() == 2
        tweetsRepository.count() == 2
    }

    def "user should not be able to post tweets longer than 140 characters"() {
        expect:
        mvc.perform(post("/wall").header("Authorization", "user")
                                            .content(randomDescription(141)))
                .andExpect(status().isUnprocessableEntity())
    }

    def "user should be able to see the tweets that were posted in reverse chronological order"() {
        given:
        postToWall("userA", "First tweet of userA")
        postToWall("userB", "First tweet of userB")
        postToWall("userA", "Second tweet of userA")

        when:
        def getResult = content(readFromWall("userA"))

        then:
        getResult.size() == 2
        getResult.get(0).message == "Second tweet of userA"
        getResult.get(1).message == "First tweet of userA"
    }

    def "should allow users to follow other users and see their tweets"() {
        given:
        postToWall("user1", "First tweet of user1")
        postToWall("user2", "First tweet of user2")
        postToWall("user1", "Second tweet of user1")

        when:
        followUser("user2", "user1")
        def timeLineResult = content(readFromTimeLine("user2"))

        then:
        timeLineResult.size() == 2
        timeLineResult.get(0).message == "Second tweet of user1"
        timeLineResult.get(1).message == "First tweet of user1"
    }
    
}