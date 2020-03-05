# Person API

This is an application providing REST API for Person data management.

## Set up the app
Make sure you have Java and Maven installed locally.

    git clone https://github.com/jenny-magno/person-api.git
    cd person-api

Update the configuration file

## Run the app

    mvn spring-boot:run

The application should now be running on [localhost:8080](localhost:8080). Currently, the application stores data in-memory. Future work on this application includes integration with a MySQL database.

## Run the tests
Use the following command to run a series of basic tests on the Person API.

    mvn test

## API Authentication 
The API is secured with Basic authentication. A default in-memory `user`/`password` credential has been created for demonstration purposes. This feature is planned to be fully implemented in future development work.

## Future Work
Developments planned for this application include:

- Secure with OAuth 2.0
- Integrate with MySQL database
- A more verbose logging system

# API Documentation

The REST API for the Persons app is described below.

## Persons
  - [GET /persons](#get-persons)
  - [GET /persons/{id}](#get-personsid)
  - [POST /persons](#post-persons)
  - [PUT /persons/{id}](#delete-personsid)
  - [DELETE /persons/{id}](#delete-personsid)

---

## Persons

### GET /persons
- Returns a list of all Persons

### Sample Request 

    curl -X GET -H 'Accept:application/json' http://localhost:8080/persons -H 'Authorization: Basic {auth}'

### Sample Response

`HTTP/1.1 200 OK`

    [
      {
          "id": 1,
          "firstName": "Jane",
          "lastName": "Doe",
          "age": 27,
          "hobby": [
              "reading",
              "cooking"
          ]
      },
      {
        "id": 2,
        "firstName": "Adam",
        "lastName": "Smith",
        "age": 30,
        "hobby": [
            "cars",
            "fishing"
        ]
      }
      ...
    ]
    
## GET /persons/{id}
- Returns a Person with the ID specified

### Path Parameters

Field | Description | Example
--- | --- | ---
id | ID of the Person to be retrieved | `1`

### Sample Request

    curl -X GET -H 'Accept:application/json' http://localhost:8080/persons/1 -H 'Authorization: Basic {auth}'

### Sample Response for Existing Person

`HTTP/1.1 200 OK`

    {
        "id": 1,
        "firstName": "Jane",
        "lastName": "Doe",
        "age": 27,
        "hobby": [
            "reading",
            "cooking"
        ]
    }

### Sample Response for Non-existing Person

`HTTP/1.1 404 Not Found`

    {
      "timestamp": "2020-03-05T04:24:07.078+0000",
      "status": 404,
      "error": "Not Found",
      "message": "Person [3] cannot be found",
      "path": "/api/v1/persons/3"
    }

## POST /persons
- Creates a new Person

### Body Parameters 

Field | Description | Example
---  | --- | ---
firstName | Required.<br>String value for Person's first name. | `Melissa`
lastName | Required.<br>String value for Person's last name. | `Smith`
age | Required.<br>Integer value for Person's age. | `24`
hobby | Required.<br>Array of string values for Person's hobbies. | `["reading", "swimming"]`

### Sample Request
If you're using Windows Command Prompt, make sure to use double quotes for the request options and escape the special characters in the request body.

    curl -X POST -H 'Content-Type:application/json' -H 'Authorization: Basic {auth}' http://localhost:8080/persons \
    -d '{
      "firstName": "Melissa",
      "lastName": "Smith",
      "age": 24,
      "hobby": [
        "reading",
        "swimming"
      ]
    }'

### Response
- The newly-created Person

`HTTP/1.1 201 Created`

    {
      "id": 3,
      "firstName": "Melissa",
      "lastName": "Smith",
      "age": 24,
      "hobby": [
        "reading",
        "swimming"
      ]
    }

## PUT /persons/{id}
- Updates a single Person record

### Query Parameters

Field | Description | Example
--- | --- | ---
id | ID of the Person to be updated | `1`

### Body Parameters

Field | Description | Example
---  | --- | ---
firstName | Required.<br>String value for Person's first name. | `Jane`
lastName | Required.<br>String value for Person's last name. | `Smith`
age | Required.<br>Integer value for Person's age. | `28`
hobby | Required.<br>Array of string values for Person's hobbies. | `["cooking", "sewing"]`

### Sample Request

    curl -X PUT -H 'Accept:application/json' -H 'Authorization: Basic {auth}' http://localhost:8080/persons/1  \
    -d '{
      "firstName": "Jane",
      "lastName": "Smith",
      "age": 28,
      "hobby": [
        "cooking",
        "sewing"
      ]
    }'

### Response
- The newly-updated Person

### Sample Response for Existing Person

`HTTP/1.1 200 OK`

    {
        "id": 1,
        "firstName": "Jane",
        "lastName": "Smith",
        "age": 28,
        "hobby": [
            "cooking",
            "sewing"
        ]
    }

### Sample Response for Non-existing Person

`HTTP/1.1 404 Not Found`

    {
      "timestamp": "2020-03-05T04:33:59.581+0000",
      "status": 404,
      "error": "Not Found",
      "message": "Person [1] cannot be found",
      "path": "/api/v1/persons/1"
    }


## DELETE /persons/{id}
- Deletes a single Person record

### Query Parameters

Field | Description | Example
--- | --- | ---
id | ID of the Person to be deleted | `2`

### Sample Request

    curl -X DELETE -H 'Accept:application/json' http://localhost:8080/persons/2 -H 'Authorization: Basic {auth}'

### Response
- Deletion confirmation

### Sample Response for Existing Person

`HTTP/1.1 200 OK`

    {
      "deleted": true
    }

### Sample Response for Non-existing Person

`HTTP/1.1 404 Not Found`

    {
      "timestamp": "2020-03-05T04:35:13.593+0000",
      "status": 404,
      "error": "Not Found",
      "message": "Person [2] cannot be found",
      "path": "/api/v1/persons/2"
    }