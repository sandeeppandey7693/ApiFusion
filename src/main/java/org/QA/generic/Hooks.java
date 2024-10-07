package org.QA.generic;

import io.restassured.RestAssured;
import org.QA.reports.ExtentReportUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.logging.Logger;

public class Hooks {

    // Create a logger instance using java.util.logging
    protected static final Logger logger = Logger.getLogger(Hooks.class.getName());

    @BeforeClass
    public void setup() {
        // Set the base URI for the API (e.g., https://jsonplaceholder.typicode.com)
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        logger.info("Base URI set to: " + RestAssured.baseURI);

        ExtentReportUtils.initializeExtentReport();
    }
    @BeforeMethod
    public void beforeSuite() {
        try{
            FrameworkGenericUtils.initializeGlobalVariables();
        }catch(Exception e){
            e.getMessage();
        }
    }
}
