package com.docker.test.meesho.Impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetRequestImpl {
    
    private static final Logger LOGGER = LogManager.getLogger(GetRequestImpl.class);
    
    Response response;
    
    public Response sendGETcall(String path) {
        
        RequestSpecification request = RestAssured.given();
        try {
            response = request.get(path);
        }catch(Exception e) {
            LOGGER.error("Test Case Failed");
            Assert.fail("Test Case Failed", e);
        }
        
        LOGGER.info(response.getBody().asString());
        return response;
    }

}
