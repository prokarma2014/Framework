package com.vzt.framework.data_access;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a row of test data. Each row is a collection of cells ordered in a specific manner depending upon the test
 * data source e.g. incase of excel sheets the cell order is the column order in the excel sheet. Empty cells are
 * allowed as long as tagged with EMPTY_CELL marker object. Null values are not allowed.
 * 
 * @author prokarma
 * @version 1.0
 */
public class TestDataRow {
	
	/**
	 * Represents a scenario where the requested cell is not found.
	 */
	public static final Object NO_DATA = new Object();
	
    private final Map<Integer, Object> cellValues;
    private final int rowSize;
    private final Header header;
    private final NativeTypeConvertor convertor;

    /**
     * Initialize the row values.
     * 
     * @param rowValues
     */
    public TestDataRow(Object[] rowValues, Header header, NativeTypeConvertor convertor) {
        rowSize = rowValues.length;
        this.header = header;
        this.convertor = convertor;
        cellValues = new HashMap<Integer, Object>();
        int count = 0;
        for (Object value : rowValues) {
            if (value == null) {
                throw new IllegalArgumentException("Null value provided as part of the cell");
            }
            if (!TableBasedDataAccess.EMPTY_CELL.equals(value)) {
                cellValues.put(count, value);
            }
            count++;
        }
    }

    /**
     * Returns the cell value at index.
     * 
     * @param index
     * @return
     */
    public Object valueAt(int index) {
        return getCellValueAtIdx(index);
    }

    private Object getCellValueAtIdx(int index) {
        Object cellValue = TableBasedDataAccess.EMPTY_CELL;
        if (cellValues.containsKey(index)) {
            cellValue = cellValues.get(index);
        }
        return cellValue;
    }

    /**
     * Returns the value for the given column name.
     * 
     * @param columnName
     * @return
     */
    public Object valueAt(String columnName) {
        return getCellValueAtIdx(header.getColumnIndexFor(columnName));
    }

    /**
     * Returns the list of non-empty indices for the row.
     * 
     * @return
     */
    public Set<Integer> getNonEmptyIndices() {
        return cellValues.keySet();
    }

    /**
     * @return the header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * @return the rowSize
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * @return the convertor
     */
    public NativeTypeConvertor getConvertor() {
        return convertor;
    }
}
