package com.docker.test.meesho.Impl;


import org.json.JSONObject;

import com.docker.test.meesho.Constant;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostRequestImpl {
    
    Response response;
    
    public Response sendPOSTCall(String name,String job) {
        
        RequestSpecification request = RestAssured.given();
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("job", job);  
        request.header("Content-Type", "application/json");
        request.body(json.toString());
        response = request.post(Constant.POSTURI);
        
        return response;
        
    }

}
