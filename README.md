# Social Networking Application (a.k.a. Twitter)

## Description
Social Networking Application allows users to:
 - post and see messages on the wall (maximum size is 140 characters, messages are displayed in reverse chronological order)
 - follow other users
 - see the messages posted by all the people they follow (in reverse chronological order)

## Usage

In order to build the application (and run tests) run: ./gradlew clean build
In order to start the application run: ./gradlew bootRun

IMPORTANT: All requests require Authorization header with the user name value.
Example request to retrieve users tweets on the wall: curl -H "Authorization: user1" http://localhost:8080/wall

### Posting messages

A user can post a 140 character message.
In order to post messages send HTTP POST request to wall endpoint with message in the request body.

### Wall

A user can see a list of the messages they've posted, in reverse chronological order.
In order to get messages send HTTP GET request to wall endpoint.

### Following

A user can follow another user.
In order to follow another user send HTTP POST to subscriptions endpoint with user name in the request body.

### Timeline

Users can see a list of the messages posted by all the people they follow, in reverse chronological order.
In order to get messages on the timeline send HTTP GET to timeline endpoint.
