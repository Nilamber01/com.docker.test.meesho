package com.docker.test.meesho.Tests;

import org.testng.annotations.Test;

import com.docker.test.meesho.BaseTest;
import com.docker.test.meesho.Impl.PostRequestImpl;
import com.docker.test.meesho.utility.DataProviderUtil;

import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class POSTTest extends BaseTest{
 
   @Test(dataProvider="getAllDataForPOST", dataProviderClass=DataProviderUtil.class,invocationCount=5)
   public void createPOSTTest(String name, String job) {
        
        PostRequestImpl postImpl = new PostRequestImpl();
        Response res= postImpl.sendPOSTCall(name, job);
        JsonPath path = res.getBody().jsonPath();
        String resName = path.get("name");
        String resJob = path.get("job");
        Assert.assertNotNull(res);
        Assert.assertEquals(resName, name);
        Assert.assertEquals(resJob, resJob);

        System.out.println(res.getBody().asString());
        
  }
}
