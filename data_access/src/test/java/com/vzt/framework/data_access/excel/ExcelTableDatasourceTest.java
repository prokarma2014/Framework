package com.vzt.framework.data_access.excel;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import com.vzt.framework.data_access.TableBasedDataAccess;
import com.vzt.framework.data_access.excel.ExcelNativeType;
import com.vzt.framework.data_access.excel.ExcelTableDatasource;

/**
 * Validates the key functionalities of the excel table datasource.
 * 
 * @author smahalingam@prokarma.com
 * @version 1.0
 */
public class ExcelTableDatasourceTest {

    @Test
    public void itShouldLoadDataFromExcelSheetOfAllSupportedTypes() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(this.getClass().getClassLoader()
                .getResourceAsStream("single/sample_test_data1.xlsx")));
        ExcelTableDatasource datasource = new ExcelTableDatasource(workbook.getSheetAt(0));
        Object[] arr = datasource.getAt(0);
        assertEquals("abcd", arr[0]);
        assertEquals(1212.01, new ExcelNativeType().nativeType(arr[1]).asDouble(),0.001);
        assertEquals(100.5, new ExcelNativeType().nativeType(arr[2]).asDouble(),0.001);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2015, 10, 12);
        assertEquals(cal.getTime(), new ExcelNativeType().nativeType(arr[3]).asCalendar("DD-MM-YYYY").getTime());
        cal.clear();
        cal.set(2016, 2, 12);
        assertEquals(cal.getTime(), new ExcelNativeType().nativeType(arr[4]).asCalendar("DD-MMM-YYYY").getTime());
        assertEquals("http://www.google.com", new ExcelNativeType().nativeType(arr[5]).asString());
    }
    
    
    @Test
    public void itShouldLoadDataFromExcelSheetWithEmptyCells() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(this.getClass().getClassLoader()
                .getResourceAsStream("single/sample_test_data1.xlsx")));
        ExcelTableDatasource datasource = new ExcelTableDatasource(workbook.getSheetAt(0));
        Object[] arr = datasource.getAt(1);
        //validate cell before empty
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2015, 10, 12);
        assertEquals(cal.getTime(), new ExcelNativeType().nativeType(arr[3]).asCalendar("DD-MM-YYYY").getTime());
        assertEquals(TableBasedDataAccess.EMPTY_CELL, arr[4]);
        assertEquals(TableBasedDataAccess.EMPTY_CELL, arr[5]);
        cal.clear();
        cal.set(2016, 2, 12);
        assertEquals(cal.getTime(), new ExcelNativeType().nativeType(arr[6]).asCalendar("DD-MMM-YYYY").getTime());
        assertEquals("http://www.google.com", new ExcelNativeType().nativeType(arr[7]).asString());
    }
}
