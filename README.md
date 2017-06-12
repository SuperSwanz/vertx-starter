# vertx-starter
Sample application built in Vertx.

## Getting Started

Git clone the project on your local machine and add it to your workspace.

### Prerequisites

For runnning this, you will need
- Java 1.8
- Gradle support - In Eclipse editor, goto help -> eclipse marketplace -> search for buildship (buildship gradle integration) and install it.

## Brief

This application set-up the RxVertx instance and showcase a sample Rest application.
- **VertxStarterLauncher**       -> The starting point of the application. It is used to deploy the MainVerticle.
- **MainVerticle**               -> Main verticle which sets the routers, HttpServer etc for the RxVertx.
- **MainHandler**                -> Rest Handler which receives the input and returns Json response.

## Running the app

For running the app, (IDE used here is Eclipse)
- Right click on the project("vertx-starter"), <br />select "Run As" -> "Run Configurations". Set:
  * **Main class**: com.vertx.starter.VertxStarterLauncher
  * **Program arguments**: <br />run com.vertx.starter.MainVerticle
After setting the variables, click "Run".
- If app starts successfull, goto **http://localhost:8080/**. Welcome json {"Welcome!":"Ok"} will be served as response.
- To call other handlers, do <br />
* GET http://localhost:8080/get* <br />
* Response would be: <br />
```json
{
    "Get called with id: get": "Ok"
}
```
<br />

* GET http://localhost:8080/fetch* <br />
* Response would be: <br />

```json
{
    "Get called with id: fetch": "Ok"
}
```

## Built With

* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
