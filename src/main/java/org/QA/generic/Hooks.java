package org.QA.generic;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class Hooks {
    @BeforeMethod
    public void beforeSuite() {
        try{
            FrameworkGenericUtils.initializeGlobalVariables();
        }catch(Exception e){
            e.getMessage();
        }
    }
}
