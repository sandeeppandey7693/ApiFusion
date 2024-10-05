package org.QA.APITest;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class OauthTokenUpdate {

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
