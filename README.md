# Advaita Demo

A microservice/rest api to get the statistics of the bank transactions that happened in the last 1 minute. The APIs are thread safe with concurrent requests coming in.

## Description

The API comprises of 3 endpoints:

- **Save transaction endpoint** which has a body as input  {“amount”:double, time:”string”}, for example: {“amount”:10.05, time:”2021-08-15 12:48:00”}.

- **Get stats endpoint** which returns an object with the following information {“sum”:””,”avg”:””,”max”:””,”min”:””,”count”:””}, which is being calculated on the transactions happened in last 1 minutes (compared with current time).
- **Delete transactions endpoint** this endpoint deletes all the transactions.

## Getting Started

### Requirements

* JDK 14
* Maven 3.x
* Docker
* Postman (for testing)

### Build application

* Packaged by Maven (cd to the root directory of AdvaitaDemo)
```
mvn package
```
* Build in Docker
```
docker build -t rui/advaitademo .
```

### Execute application

* In IDE: click the Run button
* Run in Docker
```
docker run -p 8080:8080 rui/advaitademo
```

## API methods

URL

~~~
http://localhost:8080/api/transactions
~~~

### Save a transaction

* Request
~~~
HTTP Method = POST
      Request URI = /api/transactions
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=UTF-8", Content-Length:"44"]
             Body = {"time":"2020-08-15 18:10:26","amount":2.02}
    Session Attrs = {}
~~~
* Response
~~~
Status = 201
    Error message = null
          Headers = [Content-Type:"application/json"]
     Content type = application/json
             Body = {"time":"2020-08-15 18:10:26","amount":2.02}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
~~~

### Get stats of transactions
* Request
~~~
HTTP Method = GET
      Request URI = /api/transactions
       Parameters = {}
          Headers = [Content-Type:"application/json;charset=UTF-8"]
             Body = null
    Session Attrs = {}
~~~
* Response
~~~
Status = 200
    Error message = null
          Headers = []
     Content type = null
             Body = {"sum": 5.04,"avg": 2.52,"max": 2.02,"min": 3.02,"count": 2}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
~~~

### Delete transactions
* Request
~~~
HTTP Method = DELETE
      Request URI = /api/transactions
       Parameters = {}
          Headers = []
             Body = null
    Session Attrs = {}
~~~
* Response
~~~
Status = 204
    Error message = null
          Headers = []
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
~~~

## Units tests

* Transaction Tests
* Transaction Controller Tests

## Author

ex. [@Rui Zhu](rzhu@ieee.org)

