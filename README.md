# athena-spring-boot
Spring Boot Application to access AWS Athena 

# Objective of POC
 - submit athena query using REST service
 - query status of submitted athena query using queryId
 - get result by query id in JSON

# Perquisites
 - create a database/table in Athena
 - update application properties with athena connection information and table name
 - update the sql in class/file AthenaService.java [method submitQuery()]
 
# Run
 - Build the project using MAVEN 
  ```
    mvn clean install
  ```
 - Run following command from target folder
  ````
    java -jar athena-poc.jar
  ````
# SWAGGER UI
   Test api using UI - **http://localhost:8080/swagger-ui.html#/**

# API Sample
- Operation to submit athena query 
    ```
    curl -X POST --header 'Content-Type: application/json' --header 'Accept: text/plain' 'http://localhost:8080/query'
    ```
  Response 
  ```
  3df83858-4f24-46a6-b20b-f793d55cdcde
  ```
- Operation to query status of athena query
    ```
    curl -X GET --header 'Accept: text/plain' 'http://localhost:8080/query/3df83858-4f24-46a6-b20b-f793d55cdcde/status'
    ```
    Response
    ```
    SUCCEEDED
    ```
- Operation to get result 
    ```
    curl -X GET --header 'Accept: application/json' 'http://localhost:8080/query/3df83858-4f24-46a6-b20b-f793d55cdcde'
    ```
  Response
   ```
    [
      {
        "eventtime": "2019-01-14T00:27:32Z",
        "eventsource": "ecr.amazonaws.com",
        "awsregion": "us-east-1",
        "eventid": "#################################",
        "eventname": "BatchGetImage"
      },
      {
        "eventtime": "2019-01-14T00:27:30Z",
        "eventsource": "ecr.amazonaws.com",
        "awsregion": "us-east-1",
        "eventid": "######################################",
        "eventname": "BatchGetImage"
      }
      ]
   ```

<hr/>
- with :heart: - H@R$H@L
 