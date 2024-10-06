package org.QA.generic;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FrameworkGlobalVar {
    public volatile static String bearerToken;
    public static String projectName;
    public static String environment;
    public static String baseEndpoint;
    public static String mailTo;
    public static String reportName;
    public static List<String> testCasesToRun;
    public static Map<String,Object> projectData;
    public static String ReportPath;
    public static String ReportEmailFlag;
    protected static String dataProviderThreadCount;
    public static int reRunIteration;
    public static String overrideRunReport;
    public static Properties dotEnvProperties;

}
