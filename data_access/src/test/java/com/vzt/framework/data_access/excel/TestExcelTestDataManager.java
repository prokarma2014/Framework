package com.vzt.framework.data_access.excel;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;

import org.junit.Test;
import com.vzt.framework.data_access.DataAccessException;
import com.vzt.framework.data_access.excel.ExcelTestDataFactory;

/**
 * Tests for excel based test data manager.
 * 
 * @author smahalingam@prokarma.com
 * @version 1.0
 */
public class TestExcelTestDataManager {

    /**
     * Verifies in the actual file system the excel files are found
     */
    @Test
    public void itShouldPassIf_3_FilesFound() {
        File file = new File(this.getClass().getClassLoader().getResource("common").getFile());
        ExcelTestDataFactory manager = new ExcelTestDataFactory(file.getAbsolutePath());
        Collection<File> files = manager.getFoundFiles();
        assertNotNull(files);
        assertEquals("Found Excel mismatch", 3, files.size());
    }

    /**
     * Verifies in the actual file system the excel files are found
     */
    @Test
    public void itShouldThrowExceptionIfNoFilesFound() {
        File file = new File(this.getClass().getClassLoader().getResource("common-na").getFile());
        try {
            new ExcelTestDataFactory(file.getAbsolutePath());
            fail("it should not reach here as exception should be thrown");
        } catch (DataAccessException dae) {
            assertEquals("No Excel sheet", dae.getMessage());
        }

    }
}
