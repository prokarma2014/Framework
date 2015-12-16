package com.vzt.framework.data_access;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Datasheet contains test data and has associated context e.g. name of the file the sheet was located in, path of the
 * excel file, sheet name. These context information can be used to filter sheets based on relevant criteria. Each
 * datasheet in excel should have a header so as enable lookup of cell values by column names along with row index. The
 * first row in excel is considered as header.
 * 
 * @author prokarma
 * @version 1.0
 */
public class DataSheet implements Iterable<TestDataRow> {
    private final Header header;
    private final Map<String, String> dataSheetContext;
    private final TableBasedDataAccess dataAccess;
    private final String name;
    private final NativeTypeConvertor convertor;

    /**
     * Initialize the data sheet with context and table data access to access the data from data source.
     * 
     * @param dataSheetContext
     * @param dataAccess
     */
    public DataSheet(String name,Map<String, String> dataSheetContext, TableBasedDataAccess dataAccess,NativeTypeConvertor convertor) {
        this.name = name;
        this.dataSheetContext = dataSheetContext;
        this.dataAccess = dataAccess;
        Object[] headerColumnNames = this.dataAccess.getAt(0);
        List<String> headerColumns = new ArrayList<String>();
        for (Object columnName : headerColumnNames) {
            headerColumns.add(columnName.toString());
        }
        this.header = new Header(headerColumns);
        this.convertor = convertor;
    }

    /**
     * Retuns the test data row at the specified row number.
     * 
     * @param rowNum
     *            row number
     * @return
     */
    public TestDataRow getAt(int rowNum) {
        return new TestDataRow(dataAccess.getAt(rowNum + 1), header,convertor);
    }

    /**
     * Search the existing data sheet and return view containing all rows matching specific column with a specific
     * value.
     * 
     * @param column
     *            column index.
     * @param key
     *            seach key.
     * @return table view.
     */
    public List<TestDataRow> searchColumnMatching(int column, String key) {
        List<TestDataRow> rows = new ArrayList<TestDataRow>();
        for (Object[] arr : dataAccess.searchColumnMatching(column, key)) {
            rows.add(new TestDataRow(arr, header,convertor));
        }
        return rows;
    }

    /**
     * @return the header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * @return the dataSheetContext
     */
    public Map<String, String> getDataSheetContext() {
        return dataSheetContext;
    }

    /**
     * Returns the iterator for getting the list of test data items.
     * 
     * @return iterator.
     */
    public Iterator<TestDataRow> iterator() {
        return new Iterator<TestDataRow>() {

            private int currRow = 0;

            /**
             * Return if more elements are available as part table.
             */
            public boolean hasNext() {
                //as the first element is always the header element which should not be returned.
                return currRow < DataSheet.this.dataAccess.getTableSize() - 1;
            }

            /**
             * Next in the row
             */
            public TestDataRow next() {
                TestDataRow row = DataSheet.this.getAt(currRow);
                currRow++;
                return row;
            }

	     public void remove() {
	     		throw new UnsupportedOperationException("Remove operation called on a read-only datasheets");
		}	
        };
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the convertor
     */
    public NativeTypeConvertor getConvertor() {
        return convertor;
    }
}