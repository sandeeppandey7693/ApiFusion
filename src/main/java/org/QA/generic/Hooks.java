package org.QA.generic;

import io.restassured.RestAssured;
import org.QA.reports.ExtentReportUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class Hooks {

    // Create a logger instance using java.util.logging
    protected static final Logger logger = Logger.getLogger(Hooks.class.getName());

    @BeforeMethod
    public void setup(Method method) throws FileNotFoundException {
        // Set the base URI for the API (e.g., https://jsonplaceholder.typicode.com)
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        logger.info("Base URI set to: " + RestAssured.baseURI);

       // Resolve later : FrameworkGenericUtils.initializeGlobalVariables();
        ExtentReportUtils.initializeExtentReport(method.getName());
        ExtentReportUtils.startTestcase(method.getName());
    }
    @AfterClass
    public void tearDown() {
        ExtentReportUtils.flush();  // Flush the reports here
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
