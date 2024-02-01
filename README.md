# monitoring-service

# Tools
* Java 17
* Maven
* JUnit 5
* Log4J
* Liquibase
* JDBC
* PostgreSQL
* Docker
* Lombok

# Building:
1. Go to project folder and run command `mvn clean install`
2. run command `mvn clean compile assembly:single`
3. create system environment `METERS_FILE_PATH` with `meters.txt` file location 
4. go to target folder `cd target` (if you haven't target folder check the correct result of step 1 and 2)
5. run the command `java -jar monitoring-service-1.0-SNAPSHOT-jar-with-dependencies.jar`(in this version application can't start without connection to database, if you want to use in-memory go to console branch)

# Admin settings:
For adding the list of meter types create file `meters.txt` in target directory and write types according the example:

Heating  
Cold water  
Hot water  

# Docker:
`docker-compose` file located in root directory. For starting the container use command ` docker-compose up -d`

# DB settings: 
* after successful starting the postgres container start prepare data from file `init.sql`
* connection config located in jdbc.properties files (`db` folder)
* db entities description located in `ddl.sql` file
