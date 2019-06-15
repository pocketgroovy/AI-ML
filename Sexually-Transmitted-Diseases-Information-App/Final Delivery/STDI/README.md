# CS6440 Group Project
## Team: Across The Board Champion
## App Name: STDI

#### How to run the initial code locally

1. import the STDI to IntelliJ from STDI as maven project.
2. repeat Next, make sure Java 1.8 is selected on the way.
3. if not set yet, open Run > Edit Configuration
4. On the left column, select STDIApplication
5. set Main class to STDIApplication if not already set, everything else should be as default.
6. Hit the play button or Run > Run STDIApplication to start the project
7. go to your browser and access localhost:8080 </br>


#### Command Line Operation
alternative ways to build app and image <br>

- Only building application <br>
    ~~~~
    ./mvnw -Dmaven.test.failure.ignore=true clean install
    ~~~~
- build app and create docker image for local use <br>
    ~~~~
    ./mvnw install dockerfile:build<br>
    ~~~~
Docker image name will be abc/stdi:0.0.1-SNAPSHOT



#### How to run the app with docker compose
- first the app source code needs to be compiled by running the command below
    ~~~~
    docker-compose up -d
    ~~~~
