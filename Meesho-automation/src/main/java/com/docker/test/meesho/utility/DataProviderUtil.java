package com.docker.test.meesho.utility;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class DataProviderUtil extends ExcelDataReaderUtil{
    
    @DataProvider(name="getAllDataForPOST",parallel=true)
    public static Object[][] getAllRows() throws IOException{
        Object[][] data = getAllData();
        return data;
    }

    
    @DataProvider(name="getSelectedRowsForPOST")
    public static Object[][] getSelectedData(ITestContext context) throws IOException{
        Object[][] data = getSelectiveRowsData(context.getCurrentXmlTest().getParameter("ExcelRows"));
        return data;
    }
}
