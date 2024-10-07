package org.QA.tests;

import io.restassured.response.Response;
import org.QA.APIConnection.RequestConnect;
import org.QA.generic.Hooks;
import org.QA.reports.ExtentReportUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ApiTest extends Hooks {

    @Test
    public void testGetRequest() {
        ExtentReportUtils.startTestcase("FirstTestcase");

        Response response = RequestConnect.makeRequest(
                "GET",
                "/users",  // Endpoint
                null,      // No body for GET request
                null,      // No custom headers
                null       // No query values
        );

        // Validate the response
        response.then().statusCode(200);
        ExtentReportUtils.logInfo("GET Response Body: " + response.asString());
        ExtentReportUtils.addTestStep("Fetch Response of GET","PASS","Response fetched Succesfully : "+response);  // Log status as PASS
    }
    private Object getCurrentMethod() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }

}
