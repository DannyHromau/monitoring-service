# monitoring-service

# Tools
 * Java 17
 * Maven
 * JUnit 5
 * Log4J

# Building:
1. Go to project folder and run command `mvn clean install`
2. run command `mvn clean compile assembly:single`
3. go to target folder `cd target` (if you haven't target folder check the correct result of step 1 and 2)
4. run the command `java -jar monitoring-service-1.0-SNAPSHOT-jar-with-dependencies.jar`

# Admin settings:
For adding the list of meter types create file meters.txt in target directory and write types according the example:
 
 Heating  
 Cold water  
 Hot water  
