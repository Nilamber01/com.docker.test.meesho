package com.docker.test.meesho.Tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.docker.test.meesho.BaseTest;
import com.docker.test.meesho.Constant;
import com.docker.test.meesho.Impl.GetRequestImpl;
import com.docker.test.meesho.Mapper.GetResponseMapper;
import com.docker.test.meesho.Mapper.GetWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GETTest extends BaseTest{
    
    private static final Logger LOGGER = LogManager.getLogger(GETTest.class);
    
    @SuppressWarnings({ "unchecked", "static-access" })
    @Test()
  public void getMethodTest() throws JsonParseException, JsonMappingException, IOException {
        
        LOGGER.info("--------Starting GET Call Test----------");
        GetRequestImpl getRequest = new GetRequestImpl();
        Response response = getRequest.sendGETcall(Constant.GETURI);
       
        JsonPath path = response.jsonPath();
       // HashMap<Object,Object> map= response.getBody().as(HashMap.class);       // can be used as such
        
        List<JSONObject> object = path.getList("data");
        System.out.println(object.size());
        
       // JSONObject jsn = new JSONObject(map);       //can be used as such
            
        GetWrapper wrapper = response.getBody().as(GetWrapper.class);
        List<GetResponseMapper> mapper = wrapper.getData();
        
        for(GetResponseMapper m:mapper) { 
            System.out.print(m.getFirst_name()+" ");
            System.out.println(m.getLast_name());
            System.out.print(m.getId() + " ->");
            System.out.println(m.getAvatar());
        }
   
        assertNotNull(response, "Null Response");       
        
  }
    
    public static void printJSONObject(JSONObject map) {
        
        String data = "data";
        String[] firstName = new String[10];
        String fName = "first_name";
        String keyStr="";
        Object v = null;
        int i=0;
        for(Object entry: map.keySet()) {
            if(entry.equals(data)) {
                keyStr = (String) entry;
                v = map.get(keyStr);
                System.out.println(map.get(keyStr).toString());
            }
            if (v instanceof JSONObject)
                printJSONObject((JSONObject)v);
        }
        System.out.println("List of FirstNames"+Arrays.toString(firstName));
    }
}
