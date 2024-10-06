package org.QA.generic;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class FrameworkGenericUtils {
    static Logger logger = Logger.getLogger(FrameworkGenericUtils.class.getName());

    public static void initializeGlobalVariables() throws FileNotFoundException {
        InputStream readProjectFile = ClassLoader.getSystemResourceAsStream("project.yml");
        if(readProjectFile==null)
            throw new FileNotFoundException("project.yml file is not exist in your project directory.");
        else{
            Yaml yaml = new Yaml();

            FrameworkGlobalVar.projectData = yaml.load(readProjectFile);
            System.out.println((String) yaml.load(readProjectFile));
            FrameworkGlobalVar.environment = getProjectEnvironment();
            FrameworkGlobalVar.dotEnvProperties = getDotEnvProperties();
            FrameworkGlobalVar.projectName = getProjectName();
//            FrameworkGlobalVar.mailTo =

        }
    }

    public static String getProjectEnvironment(){
        String propertyName = "environment";
        String PropertyValue = "";
        if(FrameworkGlobalVar.dotEnvProperties!=null && !FrameworkGlobalVar.dotEnvProperties.isEmpty()
           && FrameworkGlobalVar.dotEnvProperties.size()>0 && getPropertyIgnoreCase(propertyName,"dotEnv")!=null) {
            PropertyValue = getPropertyIgnoreCase(propertyName, "dotEv");
        }
        else if(getPropertyIgnoreCase(propertyName,"System")!=null) {
            PropertyValue = getPropertyIgnoreCase(propertyName,"system");
        }
        else if(getEnvVariableIgnoreCase(propertyName)!=null){
            propertyName = getEnvVariableIgnoreCase(propertyName);
        }
        else if(containsKeyIgnoreCase(FrameworkGlobalVar.projectData,propertyName)){
            PropertyValue = getIgnoreCase(FrameworkGlobalVar.projectData,propertyName).toString();
        }
        else {
            throw new RuntimeException("Environment name is missing.");
        }

        return PropertyValue;
    }
    public static String getProjectName(){
        String propertyName = "projectName";
        String PropertyValue = "";
        if(FrameworkGlobalVar.dotEnvProperties!=null && !FrameworkGlobalVar.dotEnvProperties.isEmpty()
                && FrameworkGlobalVar.dotEnvProperties.size()>0 && getPropertyIgnoreCase(propertyName,"dotEnv")!=null) {
            PropertyValue = getPropertyIgnoreCase(propertyName, "dotEv");
        }
        else if(getPropertyIgnoreCase(propertyName,"System")!=null) {
            PropertyValue = getPropertyIgnoreCase(propertyName,"system");
        }
        else if(getEnvVariableIgnoreCase(propertyName)!=null){
            propertyName = getEnvVariableIgnoreCase(propertyName);
        }
        else if(containsKeyIgnoreCase(FrameworkGlobalVar.projectData,propertyName)){
            PropertyValue = getIgnoreCase(FrameworkGlobalVar.projectData,propertyName).toString();
        }
        else {
            throw new RuntimeException("project name is missing.");
        }

        return PropertyValue;
    }
    private static String getPropertyIgnoreCase(String propertyName, String propertyType){
        Properties properties = new Properties();
        switch (propertyType){
            case "System" : {
                for(String key : properties.stringPropertyNames()){
                    if(key.equalsIgnoreCase(propertyName)){
                        return System.getProperty(key);
                    }
                }
                return null;
            }
            case "dotEnv":{
                for(String key : FrameworkGlobalVar.dotEnvProperties.stringPropertyNames()){
                    if(key.equalsIgnoreCase(propertyName))
                        return FrameworkGlobalVar.dotEnvProperties.getProperty(key);
                }
            }
            return null;
        }
        return null;
    }

    private static String getEnvVariableIgnoreCase(String property){
        Map<String,String> envVars = System.getenv();
        for(String key : envVars.keySet()){
            if(key.equalsIgnoreCase(property)){
                return envVars.get(key);
            }
        }
        return null;
    }
    private static boolean containsKeyIgnoreCase(Map<String,Object>map, String key){
        return map.keySet().stream().anyMatch(k -> k.equalsIgnoreCase(key));
    }
    private static Object getIgnoreCase(Map<String,Object>map,String key){
        return map.entrySet().stream().filter(entry->entry.getKey().equalsIgnoreCase(key)).map(Map.Entry::getValue).findFirst().orElse(null);
    }
    private static Properties getDotEnvProperties(){
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream(".env"));
        } catch (IOException e) {
            throw new RuntimeException("Env File not present in project directory");
        }
        return properties;
    }
    public static String getCurrentProperty(String propertyName, String defaultPropertyValue){
        String PropertyValue = "";
        if(FrameworkGlobalVar.dotEnvProperties!=null && !FrameworkGlobalVar.dotEnvProperties.isEmpty()
                && FrameworkGlobalVar.dotEnvProperties.size()>0 && getPropertyIgnoreCase(propertyName,"dotEnv")!=null) {
            PropertyValue = getPropertyIgnoreCase(propertyName, "dotEv");
        }
        else if(getPropertyIgnoreCase(propertyName,"System")!=null) {
            PropertyValue = getPropertyIgnoreCase(propertyName,"system");
        }
        else if(getEnvVariableIgnoreCase(propertyName)!=null){
            propertyName = getEnvVariableIgnoreCase(propertyName);
        }
        else if(containsKeyIgnoreCase(FrameworkGlobalVar.projectData,propertyName)){
            PropertyValue = getIgnoreCase(FrameworkGlobalVar.projectData,propertyName).toString();
        }
        else {
            throw new RuntimeException(propertyName+" name is missing.");
        }

        return PropertyValue;
    }
}
