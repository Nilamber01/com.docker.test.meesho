package com.docker.test.meesho;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;

public class BaseTest {
    
    
    @BeforeSuite
    public void setup() throws IOException {
        final Properties props = new Properties();
        final InputStream logFile = this.getClass().getClassLoader().getResourceAsStream("log4j.properties");
        props.load(logFile);
        PropertyConfigurator.configure(props);
        RestAssured.baseURI = "https://reqres.in/api/";
        
    }

}
