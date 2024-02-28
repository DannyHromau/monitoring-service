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
* MapStruct
* RESTful API
* Sring framework (boot, mvc, security, AOP, test)
* Oauth2 + JWT
* Swagger

# Building:
1. Go to project folder and run command `mvn clean install`
2. create system environment `METERS_FILE_PATH` with `meters.txt` file location
3. go to target folder `cd target` (if you haven't target folder check the correct result of step 1 and 2)

# Admin settings:
For adding the list of meter types create file `meters.txt` in target directory and write types according the example:

heating  
cold_water  
hot_water

# Docker:
`docker-compose` file located in root directory. For starting the container use command ` docker-compose up -d`

# DB settings:
* after successful starting the postgres container start prepare data from file `init.sql`
* connection config located in jdbc.properties files (`db` folder)
* db entities description located in `ddl.sql` file

# Running Intellij Idea:
1. Open project in IntelliJ, it will create an `.idea`.
2. Use *File* > *Project Structure* to confirm Java 17 is used.
3. Create *Edit Configuration* (if not exist Add new *Maven* configuration) or check build and run options(must be specified Java 17 SDK for 'monitoring-service' module).
4. Use the *Maven* tools window to:
   * *Toggle "Skip Tests" Mode* (if You won't to testing the application)
   * *Execute Maven Goal*: `clean install`
   * check the target directory in `monitoring-module` module (You should see an archive there named *monitoring-module-1.0.jar*)
   * Start the app with command *java -jar monitoring-module-1.0.jar*



