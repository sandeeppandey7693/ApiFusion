package org.QA.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.QA.generic.FrameworkGlobalVar;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.logging.Logger;

public class ExtentReportUtils {
    static ExtentTest testCase;
    static ExtentSparkReporter sparkReporter;
    static ExtentReports extentReports;

    static final Logger logger = Logger.getLogger(ExtentReportUtils.class.getName());
    @BeforeSuite
    public static void initializeExtentReport(){

        String reportRunDate = java.time.LocalDate.now().toString();
        String reportPath = System.getProperty("user.dir")+ "\\"+ FrameworkGlobalVar.ReportPath + "\\" + reportRunDate;

        //String reportPath = "test-output/ExtentReport.html" ;
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

    @BeforeMethod
    public static void startTestcase(String method){
        testCase = extentReports.createTest(method);
        logger.info("Test case started: " + method);
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
