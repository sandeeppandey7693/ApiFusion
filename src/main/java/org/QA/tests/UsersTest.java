package org.QA.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.QA.APIConnection.RequestConnect;
import org.QA.generic.Hooks;
import org.QA.reports.ExtentReportUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

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
    private Object getCurrentMethod() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }

}
