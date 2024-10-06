package org.QA.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.QA.generic.FrameworkGlobalVar;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.lang.reflect.Method;

public class ExtentReportUtils {
    static ExtentTest testCase;
    static ExtentSparkReporter sparkReporter;
    static ExtentReports extentReports;

    @BeforeSuite
    public static void initializeExtentReport(){
        String reportRunDate = java.time.LocalDate.now().toString();
        String reportPath = System.getProperty("user.dir")+ "\\"+ FrameworkGlobalVar.ReportPath + "\\" + reportRunDate;

        if (FrameworkGlobalVar.reportName == null)
            reportPath +=  "\\ExtentReport.html";
        else
            reportPath += "\\" + FrameworkGlobalVar.reportName +".html";

        sparkReporter = new ExtentSparkReporter(reportPath);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    @BeforeMethod
    public static void addTestCase(Method method){
        testCase = extentReports.createTest(method.getName());
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
