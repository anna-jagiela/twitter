# Social Networking Application (similar to Twitter)

## Description
Social Networking Application allows users to:
 - post messages (maximum size is 140 characters)
 - see the user's messages on the wall (messages are displayed in reverse chronological order)
 - list other users (to see who can be followed)
 - follow other users
 - see the messages posted by all the people they follow (followees) (in reverse chronological order)

## Usage

In order to build the application (and run tests) run:
```
./gradlew clean build
```
In order to start the application run:
```
./gradlew bootRun
```

### REST API

**IMPORTANT!** All requests require `Authorization` header with the user name value.

Example request to retrieve user's tweets on the wall:
```
curl -H "Authorization: user1" http://localhost:8080/wall
```

#### Posting messages

A user can post a 140 character message to the wall.
In order to post a message send `HTTP POST` request to `wall` endpoint with message in the request body.

Example request:
```
POST /wall HTTP/1.1
Content-Type: application/json
Accept: application/json
Authorization: user1
{
	"message": "My first tweet!"
}
```

Example response:
```
201 Created
{
    "id": 1,
    "user": {
        "id": 1,
        "name": "user1"
    },
    "createdAt": "2018-03-11T12:32:52.233+0000",
    "message": "My first tweet!"
}

```

#### Wall

A user can see a list of the messages they've posted, in reverse chronological order.
In order to get the messages send `HTTP GET` request to `wall` endpoint.

Example request:
```
GET /wall HTTP/1.1
Content-Type: application/json
Accept: application/json
Authorization: user1
```

Example response:
```
200 OK
{
    "id": 1,
    "user": {
        "id": 1,
        "name": "user1"
    },
    "createdAt": "2018-03-11T12:32:52.233+0000",
    "message": "My first tweet!"
}

```

#### Listing other users

A user can list other user to see who can be followed.
In order to list other users send `HTTP GET` to `users` endpoint.

Example request:
```
GET http://localhost:8080/users
Content-Type: application/json
Accept: application/json
Authorization: user1
```

Example response:
```
200 OK
[
    {
        "id": 2,
        "name": "user2"
    }
]
```

#### Following

A user can follow another user.
In order to follow another user send `HTTP POST` to `subscriptions` endpoint with user name in the request body.

Example request:
```
POST http://localhost:8080/subscriptions
Content-Type: application/json
Accept: application/json
Authorization: user1
{
	"followee": "user2"
}
```

Example response:
```
201 Created
{
    "followerId": 1,
    "followeeId": 2
}
```

#### Timeline

Users can see a list of the messages posted by all the people they follow, in reverse chronological order.
In order to get messages on the timeline send `HTTP GET` to `timeline` endpoint.

Example request:
```
GET http://localhost:8080/timeline
Content-Type: application/json
Accept: application/json
Authorization: user1
```

Example response:
```
200 OK
[
    {
        "id": 2,
        "user": {
            "id": 2,
            "name": "user2"
        },
        "createdAt": "2018-03-11T12:35:46.731+0000",
        "message": "Another tweet..."
    }
]
```
