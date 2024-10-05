package org.QA.generic;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FrameworkGlobalVar {
    public volatile static String bearerToken;
    public static String projectName;
    public static String environment;

    public static String nodeURL;
    public static String baseEndpoint;
    public static String mailTo;
    public static String reportName;
    public static String ReportName;
    public static List<String> testCasesToRun;
    public static Map<String,Object> projectData;
    public static Map<String,Object> envData;

    public static String ReportPath;
    public static String ReportEmailFlag;

    public static String generateS3Report;
    protected static String dataProviderThreadCount;
    protected static String tags;
    public static int reRunIteration;
    public static String overrideRunReport;
    public static Properties dotEnvProperties;

}
