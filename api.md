# Timeline API Spec

### Authentication Header:

`Authorization: Bearer jwt.token.here`

## JSON Objects returned by API:

Make sure the right content type like `Content-Type: application/json; charset=utf-8` is correctly returned.

### Users for authentication

```JSON
{
  "user": {
    "email": "infiniteXyy@gmail.com",
    "token": "jwt.token.here",
    "username": "infiniteXyy",
    "image": null
  }
}
```

### Profile

```JSON
{
  "profile": {
    "username": "pikachu",
    "image": "http://cdn.infinitex.cn/images/pikachu.jpg",
  }
}
```

### Single Message

```JSON
{
  "message":{
    "id": "sdfwer134234ew",
    "body": "It was fun.",
    "createdAt": "2018-12-11T11:04:34.359Z",
    "updatedAt": "2018-12-12T11:04:34.359Z",
    "author": {
      "username": "pikachu",
      "image": "http://cdn.infinitex.cn/images/pikachu.jpg",
    }
  }
}
```

### Multiple Messages

```JSON
{
  "message":[{
    "id": "sdfwer134234ew",
    "body": "It was fun.",
    "createdAt": "2018-12-11T11:04:34.359Z",
    "updatedAt": "2018-12-12T11:04:34.359Z",
    "author": {
      "username": "pikachu",
      "image": "http://cdn.infinitex.cn/images/pikachu.jpg",
    }
  }],
  "messagesCount": 1
}
```

### Errors and Status Codes

If a request fails any validations, expect a **422** and errors in the following format:

```JSON
{
  "errors":{
    "body": [
      "can't be empty"
    ]
  }
}
```

#### Other status codes:

**401** for Unauthorized requests, when a request requires authentication but it isn't provided

**403** for Forbidden requests, when a request may be valid but the user doesn't have permissions to perform the action

**404** for Not found requests, when a resource can't be found to fulfill the request


## Endpoints:

### Authentication:

`POST /api/users/login`

Example request body:
```JSON
{
  "user":{
    "email": "infiniteXyy@gmail.com",
    "password": "123"
  }
}
```

No authentication required, returns a [User](#users-for-authentication)

Required fields: `email`, `password`


### Registration:

`POST /api/users`

Example request body:
```JSON
{
  "user":{
    "username": "Qzy",
    "email": "eternalq@infinitex.cn",
    "password": "qzyqzy"
  }
}
```

No authentication required, returns a [User](#users-for-authentication)

Required fields: `email`, `username`, `password`

### Get Current User

`GET /api/user`

Authentication required, returns a [User](#users-for-authentication) that's the current user

### Update User

`PUT /api/user`

Example request body:
```JSON
{
  "user":{
    "email": "infiniteXyy@gmail.com",
    "image": "http://cdn.infinitex.cn/images/newpikachu.jpg"
  }
}
```

Authentication required, returns the [User](#users-for-authentication)


Accepted fields: `email`, `username`, `password`, `image`, `bio`

### Get Profile

`GET /api/profiles/:username`

Authentication optional, returns a [Profile](#profile)

### List Messages

`GET /api/messages`

Returns most recent massages globally by default

**Query Parameters:**

Limit number of messages (default is 20):

`?limit=20`

Offset/skip number of messages (default is 0):

`?offset=0`

Authentication optional, will return [multiple messages](#multiple-messages), ordered by most recent first

### Create Message

`POST /api/messages`

Example request body:

```JSON
{
  "message": {
    "body": "I am so FAT!",
  }
}
```

Authentication required, will return an [Message](#single-message)

Required fields: `body`

### Update Message

`PUT /api/messages/:id`

Example request body:

```JSON
{
  "message": {
    "body": "I am not fat at all"
  }
}
```

Authentication required, returns the updated [Message](#single-message)

Optional fields: `body`


### Delete Message

`DELETE /api/messages/:id`

Authentication required
