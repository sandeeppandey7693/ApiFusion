package org.QA.APIConnection;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class OauthTokenFormat {

    private static boolean voidToken = false;
    private static boolean argToken = false;
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    private static  int timeOut = 180;
    public static void setTimeOut(int seconds){
        timeOut=seconds;
    }

    public static void setOauthToken(){

    }
}
