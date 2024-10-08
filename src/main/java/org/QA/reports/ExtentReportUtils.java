package org.QA.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.QA.generic.FrameworkGenericUtils;
import org.QA.generic.FrameworkGlobalVar;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class ExtentReportUtils {
    static ExtentTest testCase;
    static ExtentSparkReporter sparkReporter;
    static ExtentReports extentReports;
    public static ExtentTest currentNode;
    public static ExtentTest currentTest;

    static final Logger logger = Logger.getLogger(ExtentReportUtils.class.getName());
    @BeforeMethod
    public static void initializeExtentReport(String testName){

        String reportRunDate = java.time.LocalDate.now().toString();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
       // FrameworkGenericUtils.getCurrentProperty("ReportPath","./Report");
        String reportPath = System.getProperty("user.dir")+ "\\"+ "Reports/ExtentReport_"  +testName + "_" + reportRunDate ;

        if (FrameworkGlobalVar.reportName == null)
            reportPath +=  "\\ExtentReport.html";
        else
            reportPath += "\\" + FrameworkGlobalVar.reportName +".html";

        sparkReporter = new ExtentSparkReporter(reportPath);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        logger.info("Extent Reports initialized at: " + reportPath);
    }

    public static void flush() {
        if (extentReports != null) {
            extentReports.flush(); // Ensure that flush is called to generate the report
            logger.info("Extent Reports flushed successfully.");  // Log when flushing the report
        } else {
            logger.warning("Extent Reports not initialized; cannot flush.");  // Log warning if not initialized
        }
    }
   // @BeforeMethod : Added in Setup method of hooks class
    public static void startTestcase(String methodName) {
        testCase = extentReports.createTest(methodName);  // Set test case as method name
        logger.info("Test case started: " + methodName);
    }

    public static void addTestStep(String stepName, String stepStatus , String stepDescription){

        switch(stepStatus){
            case "pass" , "PASS" , "Pass" , "p":
                testCase.createNode(stepName).generateLog(Status.PASS,stepDescription); break;
            case "fail" , "FAIL" , "Fail" , "f":
                testCase.createNode(stepName).generateLog(Status.FAIL,stepDescription); break;
            case "skip" , "SKIP" , "Skip" , "s":
                testCase.createNode(stepName).generateLog(Status.SKIP,stepDescription); break;
            case "warn" , "WARN" , "WARNING" , "Warning" , "warning" , "w":
                testCase.createNode(stepName).generateLog(Status.WARNING,stepDescription); break;
            default:
                testCase.createNode(stepName).generateLog(Status.INFO,stepDescription);
        }
    }
    public static void logInfo(String message) {
        testCase.info(message);
    }
    // Create a collapsible step (node) within the test case
    public static void startNode(String nodeName) {
        if (testCase == null) {
            throw new IllegalStateException("Test case not started. Call startTestcase() first.");
        }
        currentNode = testCase.createNode(nodeName);
        logger.info("Test node created: " + nodeName);
    }
    // Log JSON Response in a pretty-print format
    public static void logJsonResponsePrettyFormat(String rawJson) {
        if (currentNode != null) {
            // Pretty print the JSON using Gson or any JSON formatter
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(gson.fromJson(rawJson, Object.class));

            currentNode.info("<pre>" + prettyJson + "</pre>");  // Log the formatted JSON in a collapsible node
            logger.info("JSON Response logged in formatted form.");
        } else {
            logger.warning("Cannot log JSON response because currentNode is null.");
        }
    }
    public static void logJsonRequest(String requestBody) {
        if (currentNode != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();  // Use Gson for pretty-printing JSON
            String prettyJson = gson.toJson(new Gson().fromJson(requestBody, Object.class));
            currentNode.info("<details><summary><b>Request Body</b></summary><pre>" + prettyJson + "</pre></details>");
        } else if (currentTest != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(new Gson().fromJson(requestBody, Object.class));
            currentTest.info("<details><summary><b>Request Body</b></summary><pre>" + prettyJson + "</pre></details>");
        }
    }
    // Log status for a node
    public static void logNodeStatus(String status) {
        if ("PASS".equalsIgnoreCase(status)) {
            currentNode.pass("Step Passed");
            logger.info("Step passed: " + currentNode.getModel().getName());
        } else if ("FAIL".equalsIgnoreCase(status)) {
            currentNode.fail("Step Failed");
            logger.warning("Step failed: " + currentNode.getModel().getName());
        }
    }
    public static void setPassTestStep(String stepName,String stepDescription){
        addTestStep(stepName,"PASS",stepDescription);
    }

    public static void setFailTestStep(String stepName,String stepDescription){
        addTestStep(stepName,"FAIL",stepDescription);
    }

    public static void setInfoTestStep(String stepName,String stepDescription){
        addTestStep(stepName,"INFO",stepDescription);
    }

    public static void setWarnTestStep(String stepName,String stepDescription){
        addTestStep(stepName,"WARN",stepDescription);
    }

}
