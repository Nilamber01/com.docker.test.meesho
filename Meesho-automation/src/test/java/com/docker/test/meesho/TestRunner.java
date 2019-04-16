package com.docker.test.meesho;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;


public class TestRunner {

    public static void main(String[] args) throws URISyntaxException {
        final String suitePath = "Suitexml/";
        String targetApp = "Get";
        if (args.length >= 1) {
            targetApp = args[0];
        }
        String suiteFileName = targetApp;
        
        System.out.println("Executing TestNG Suite: " + suiteFileName);
        final URI resource = TestRunner.class.getClassLoader().getResource(suitePath + suiteFileName).toURI();
        final TestNG driver = new TestNG();
        setTestSuites(driver, resource);
        driver.run();
    }
    
    
    private static void setTestSuites(final TestNG driver, final URI ets) {
        if (ets.getScheme().equalsIgnoreCase("jar")) {
            // jar:{url}!/{entry}
            final String[] jarPath = ets.getSchemeSpecificPart().split("!");
            final File jarFile = new File(URI.create(jarPath[0]));
            driver.setTestJar(jarFile.getAbsolutePath());
            driver.setXmlPathInJar(jarPath[1].substring(1));
        } else {
            final List<String> testSuites = new ArrayList<String>();
            final File tngFile = new File(ets);
            if (tngFile.exists()) {
                System.out.printf("Using TestNG config file %s", tngFile.getAbsolutePath());
                testSuites.add(tngFile.getAbsolutePath());
            } else {
                throw new IllegalArgumentException("A valid TestNG config file reference is required.");
            }
            driver.setTestSuites(testSuites);
        }

    }
}
