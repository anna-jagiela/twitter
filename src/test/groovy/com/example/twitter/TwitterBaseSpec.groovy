package com.example.twitter

import com.example.twitter.controller.model.FollowMessage
import com.example.twitter.controller.model.TweetMessage
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class TwitterBaseSpec extends Specification {

    @Autowired
    WebApplicationContext context

    MockMvc mvc

    private static ObjectMapper objectMapper = new ObjectMapper()

    private Connection connection

    def setup() {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build()
    }

    def cleanup() {
        connection.prepareCall('DROP ALL OBJECTS').execute()
        connection.close()
    }

    def postToWall(String userName, String message, ResultMatcher matcher = status().isCreated()) {
        mvc.perform(post("/wall").header("Authorization", userName)
                                            .contentType(APPLICATION_JSON_UTF8)
                                            .content(tweet(message)))
                .andExpect(matcher)
                .andReturn()
    }

    def readFromWall(String userName) {
        mvc.perform(get("/wall").header("Authorization", userName))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
    }

    def listOtherUsers(String userName) {
        mvc.perform(get("/users").header("Authorization", userName))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
    }

    def followUser(String follower, String followee, ResultMatcher matcher = status().isCreated()) {
        mvc.perform(post("/subscriptions").header("Authorization", follower)
                                                     .contentType(APPLICATION_JSON_UTF8)
                                                     .content(subscription(followee)))
                .andExpect(matcher)
    }

    def readFromTimeLine(String userName) {
        mvc.perform(get("/timeline").header("Authorization", userName))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
    }

    def tweet(String message) {
        jsonize(new TweetMessage(message))
    }

    def subscription(String followee) {
        jsonize(new FollowMessage(followee))
    }

    def jsonize(Object o) {
        try {
            return objectMapper.writeValueAsString(o)
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize object to JSON", e)
        }
    }

    def <T> T dejsonize(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz)
        } catch (IOException e) {
            throw new RuntimeException("Unable to deserialize object from JSON", e)
        }
    }

    def <T> T dejsonize(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference)
        } catch (IOException e) {
            throw new RuntimeException("Unable to deserialize object from JSON", e)
        }
    }

    def content(MvcResult result) {
        result.getResponse().getContentAsString()
    }

    def randomDescription(int length) {
        randomAlphabetic(length)
    }
    
}