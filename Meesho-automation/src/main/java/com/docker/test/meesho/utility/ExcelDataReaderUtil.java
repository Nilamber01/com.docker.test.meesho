package com.docker.test.meesho.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.docker.test.meesho.Constant;


public class ExcelDataReaderUtil {
    
    private static final Logger LOGGER = LogManager.getLogger(ExcelDataReaderUtil.class);
    
    private static XSSFWorkbook wb;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static FileInputStream fis;    
    
    public static Object[][] getAllData() throws IOException{
        
        fis = new FileInputStream(Constant.DATA_FILEPATH);
        wb = new XSSFWorkbook(fis);
        sheet = wb.getSheet("Sheet1");
        row = sheet.getRow(0);
        int lastRow = sheet.getLastRowNum();
        int columns = row.getLastCellNum();
        Object[][] data = new Object[lastRow][columns];

        
        for(int i=1;i<lastRow;i++) {
            for(int j=0;j<columns;j++) {
            cell = sheet.getRow(i).getCell(j);
            data[i-1][j]=cell.toString();
            }
        }
        return data;
    }
    

    public static Object[][] getSelectiveRowsData(String rowNumbers) throws IOException{
        wb = new XSSFWorkbook(fis);
        sheet = wb.getSheet("Sheet0");
        row = sheet.getRow(0);
        int totalColumns = row.getLastCellNum();
        
        boolean isDigit=false;
        String[] arrayOfIndex = rowNumbers.split(",");
        int [] arrayOfRowIndexes = new int[arrayOfIndex.length];
        Object[][] data = new Object[arrayOfIndex.length][totalColumns];
        
        for(int i=0;i<arrayOfIndex.length;i++) {
            isDigit = arrayOfIndex[i].matches("[0-9]");
            try {
            arrayOfRowIndexes[i] = Integer.parseInt(arrayOfIndex[i]);
            }catch(NumberFormatException e) {
                LOGGER.error("Specified row numbers is incorrect : "+arrayOfIndex[i]);
            }
        }
        if(!isDigit)
            throw new IllegalArgumentException("Argument passed to row numbers is incorrect");
        else {    
            for(int i=0;i<arrayOfRowIndexes.length;i++) {
                for(int j=0;j<totalColumns;j++) {
                    cell = sheet.getRow(arrayOfRowIndexes[i]).getCell(j);
                    data[i][j] = cell.toString();
                }
            }
            return data;
        }
        
    }

}
