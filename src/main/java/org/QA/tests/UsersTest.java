package org.QA.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.QA.APIConnection.RequestConnect;
import org.QA.generic.Hooks;
import org.QA.reports.ExtentReportUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UsersTest extends Hooks {


    /*
    * GET /users: List all users.
     ○ GET /users/{id}: Retrieve a specific user.
     ○ POST /users: Create a new user.
     ○ PUT /users/{id}: Update an existing user.
     ○ DELETE /users/{id}: Delete a user.

    * */
    @Test
    public void getListOfAllUsers(Method method) {
        ExtentReportUtils.startTestcase(method.getName());

        ExtentReportUtils.startNode("Send GET Request with "+"<a href='"+"URL :"+ RestAssured.baseURI+"/users"+"' style='color:blue;'>");
        Response response = RequestConnect.getRequest("/users");
        // Validate the response
        response.then().statusCode(200);
        ExtentReportUtils.logJsonResponsePrettyFormat(response.asString());
       // ExtentReportUtils.logNodeStatus("PASS");
        ExtentReportUtils.addTestStep("Fetch Response of GET","PASS","Response fetched Successfully ");  // Log status as PASS
    }

    @Test
    public void getUserDetailsById(Method method){
        ExtentReportUtils.startTestcase(method.getName());
        int id = 1;
        ExtentReportUtils.startNode("Send GET Request with "+"<a href='"+"URL :"+ RestAssured.baseURI+"/users"+"' style='color:blue;'>");
        Response response = RequestConnect.getRequest("/users",id);
        response.then().statusCode(200);
        ExtentReportUtils.logJsonResponsePrettyFormat(response.asString());
        ExtentReportUtils.addTestStep("Fetch Response of GET","PASS","Response fetched Successfully ");  // Log status as PASS

    }

    @Test
    public void createUser(Method method){
        String endpoint = "/users";
        ExtentReportUtils.startTestcase(method.getName());
        ExtentReportUtils.startNode("Send POST Request with "+"<a href='"+"URL :"+ RestAssured.baseURI+"/users/"+"' style='color:blue;'>");
        String requestBody = """
            {
              "name": "John Doe",
              "username": "johnny",
              "email": "john.doe@example.com",
              "phone": "123-456-7890",
              "website": "johndoe.com",
              "address": {
                "street": "123 Main St",
                "suite": "Apt. 1",
                "city": "Anytown",
                "zipcode": "12345"
              },
              "company": {
                "name": "John's Company",
                "catchPhrase": "Making things happen",
                "bs": "synergize scalable solutions"
              }
            }
        """;
        ExtentReportUtils.logJsonRequest(requestBody);
        ExtentReportUtils.logNodeStatus("PASS");
    }
    @Test
    public void testUpdateUser(){
        String userId = "2";
        ExtentReportUtils.startTestcase(String.valueOf(new Object(){}.getClass().getEnclosingMethod()));
        ExtentReportUtils.startNode("Send PUT Request with "+"<a href='"+"URL :"+ RestAssured.baseURI+"/users/"+userId+"' style='color:blue;'>");
        String endpoint = "/users/" + userId;
        // Create headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        // body
        String requestBody = "{\n" +
                "  \"name\": \"John Updated\",\n" +
                "  \"email\": \"john.updated@example.com\",\n" +
                "  \"phone\": \"123-456-7890\",\n" +
                "  \"website\": \"johnupdated.com\"\n" +
                "}";
        ExtentReportUtils.logJsonRequest(requestBody);
        Response response = RequestConnect.makeRequest("PUT", endpoint, requestBody, headers, null);

        //ExtentReportUtils.currentNode = ExtentReportUtils.currentTest.createNode("Fetch Response of PUT");
        String responseBody = response.getBody().asString();
        ExtentReportUtils.logJsonResponsePrettyFormat(responseBody);
        int statusCode = response.getStatusCode();
        if (statusCode == 200) {
            ExtentReportUtils.addTestStep("Validate Status Code","PASS","User updated successfully with status code: " + statusCode);
        } else {
            ExtentReportUtils.addTestStep("Validate Status Code","FAIL","User updated successfully with status code: " + statusCode);
           // ExtentReportUtils.currentNode.fail("Failed to update user. Status code: " + statusCode);
        }
    }

    @Test
    public void testDeleteUser() {
        ExtentReportUtils.startTestcase(String.valueOf(new Object(){}.getClass().getEnclosingMethod()));
        String userId = "2";
        String endpoint = "/users/" + userId;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        //ExtentReportUtils.currentNode = ExtentReportUtils.currentTest.createNode("Send DELETE Request to remove user");
        ExtentReportUtils.startNode("Send DELETE Request with "+"<a href='"+"URL :"+ RestAssured.baseURI+"/users/"+userId+"' style='color:blue;'>");
        Response response = RequestConnect.makeRequest("DELETE", endpoint, null, headers, null);

       // ExtentReportUtils.currentNode = ExtentReportUtils.currentTest.createNode("Fetch Response of DELETE");
        String responseBody = response.getBody().asString();
        ExtentReportUtils.logJsonResponsePrettyFormat(responseBody);

        // Assert the status code to verify success (200 OK, 204 No Content, etc.)
        int statusCode = response.getStatusCode();
        if (statusCode == 200 ) {
                ExtentReportUtils.addTestStep("Validate Status Code","PASS","User updated successfully with status code: " + statusCode);
            } else {
                ExtentReportUtils.addTestStep("Validate Status Code","FAIL","User updated successfully with status code: " + statusCode);
                // ExtentReportUtils.currentNode.fail("Failed to update user. Status code: " + statusCode);
            }

    }

}
