[//]: # (Author: Toby Zhu)
[//]: # (README)

# vandy-course-planner-backend

The backend of the Vandy Course Planner.<br/>
Website URL: https://vandy-course-planner.com

## How to Test

### Precondition

Run the main method in `src/main/java/edu/vanderbilt/vandycourseplanner/VandyCoursePlannerApplication.java`.

### Test using Swagger Editor

- Go to the [swagger editor](https://editor.swagger.io/)
- Upload the file `src/main/resources/API.yaml` to the editor.
- Choose the target API and click `Try is out`. Enter the query parameters or request body.
- Click `Execute` to test the REST API.
- Check the `Responses` section below to view the response.

### Test using Postman

Go to each controller class to find the route. The base URL is `localhost:8080/vandy`.

## How to Build

Run the command in terminal: `mvn clean install`. A runtime JAR file and an executable JAR file 
will appear in the `/target` directory.