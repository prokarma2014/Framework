package com.vzt.framework.data_access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a header element as part of the test data which provide column names for the test data provided. The
 * header can be used to query the column names.
 * 
 * @author prokarma
 * @version 1.0
 */
public class Header {

    private final Map<String, Integer> nameToIdxMapping = new HashMap<String, Integer>();
    private final int columnSize;

    /**
     * Initialize with a column names ordered as per index.
     * 
     * @param columnNames
     *            list of columns
     * @param dataSheet
     *            TODO
     */
    public Header(List<String> columnNames) {
        this.columnSize = columnNames.size();
        for (int count = 0; count < columnNames.size(); count++) {
            nameToIdxMapping.put(columnNames.get(count), count);
        }
    }

    /**
     * Returns the column name at the corresponding index.
     * 
     * @param index
     * @return
     */
    public String getColumnNameAt(int index) {
        if (index < 0 || index > columnSize) {
            throw new IllegalArgumentException("Invalid index based retrieval " + index);
        }
        String columnName = null;
        for (Entry<String, Integer> entry : nameToIdxMapping.entrySet()) {
            if (entry.getValue().equals(index)) {
                columnName = entry.getKey();
                break;
            }
        }
        return columnName;
    }

    /**
     * Returns the column name at the corresponding index.
     * 
     * @param columnName
     *            name of the column
     * @return
     */
    public int getColumnIndexFor(String columnName) {
        Integer index = nameToIdxMapping.get(columnName);
        if (index == null) {
            throw new IllegalArgumentException("Column index is not available for name " + columnName);
        }
        return index;
    }
    
    
    /**
     * Returns true if the column name is found in the datasheet.
     * @param columnName
     * @return
     */
    public boolean containsColumnName(String columnName) {
        return nameToIdxMapping.containsKey(columnName);
    }
    
    /**
     * Returns the list of all string names.
     * @return returns column names.
     */
    public String[] getColumnNames() {
        return nameToIdxMapping.entrySet().toArray(new String[nameToIdxMapping.size()]);
    }
}