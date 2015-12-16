package com.vzt.framework.data_access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Container for one or more test data sheets. Provides APIs to select relevant datasheet based on properties.
 * 
 * @author prokarma
 * @version 1.0
 */
public class DataSheetContainer {

    private final List<DataSheet> dataSheets;

    /**
     * Initialize with the datasheets.
     * 
     * @param dataSheets
     */
    public DataSheetContainer(List<DataSheet> dataSheets) {
        this.dataSheets = dataSheets;
    }

    /**
     * Finds the list of sheets that match by name.
     * @param name
     * @return
     */
    public List<DataSheet> findSheetByName(String name) {
        List<DataSheet> matchingSheets = new ArrayList<DataSheet>();   
        for(DataSheet sheet : dataSheets) {
            if(name.equals(sheet.getName())) {
                matchingSheets.add(sheet);
            }
        }
        return matchingSheets;
    }
    
    /**
     * Find the list of datasheets matching the specified set criteria. The list is sorted by sheets having the maximum matches.
     * @param criteria selection criteria.
     * @return list of data sheets.
     */
    public List<DataSheet> findSheetByCriteria(Map<String, String> criteria) {
        List<Object[]> foundSheets = new ArrayList<Object[]>();
        for (DataSheet sheet : dataSheets) {
            int matchCount = 0;
            Map<String, String> currContext = sheet.getDataSheetContext();
            for (Entry<String, String> matchingCriteria : criteria.entrySet()) {
                if (currContext.containsKey(matchingCriteria.getKey())
                        && matchingCriteria.getValue().equals(currContext.get(matchingCriteria.getKey()))) {
                    matchCount++;
                }
            }
            if (matchCount > 0) {
                foundSheets.add(new Object[] { sheet, matchCount });
            }
        }
        // do sort only if there are more things to match so as provide the segregate ones with most matches.
        if (criteria.size() > 1) {
            // sort by assending
            Collections.sort(foundSheets, new Comparator<Object[]>() {
                public int compare(Object[] o1, Object[] o2) {
                    return ((Integer) o1[1]).compareTo((Integer) o2[1]);
                }
            });
        }
        // make it descending, hence most matching sheets are at the top
        List<DataSheet> dataSheets = new ArrayList<DataSheet>();
        for (Object arr[] : foundSheets) {
            dataSheets.add(0, (DataSheet) arr[0]);
        }
        return dataSheets;
    }

    /**
     * @return the dataSheets
     */
    public List<DataSheet> getDataSheets() {
        return dataSheets;
    }
}
