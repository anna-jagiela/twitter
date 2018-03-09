package com.example.twitter

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
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

    def postToWall(String userName, String message) {
        mvc.perform(post("/wall").header("Authorization", userName)
                                            .content(message))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
    }

    def readFromWall(String userName) {
        mvc.perform(get("/wall").header("Authorization", userName))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
    }

    def followUser(String follower, String followee) {
        mvc.perform(post("/subscriptions").header("Authorization", follower)
                                                     .content(followee))
                .andExpect(status().is2xxSuccessful())
    }

    def readFromTimeLine(String userName) {
        mvc.perform(get("/timeline").header("Authorization", userName))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
    }

    def content(MvcResult result) {
        new JsonSlurper().parseText(result.getResponse().getContentAsString())
    }

    def randomDescription(int length) {
        randomAlphabetic(length)
    }
    
}