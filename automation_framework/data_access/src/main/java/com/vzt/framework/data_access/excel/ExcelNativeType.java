/**
 * 
 */
package com.vzt.framework.data_access.excel;

import java.util.Calendar;
import java.util.StringTokenizer;

import com.vzt.framework.data_access.GenericNativeTypeConvertor;

/**
 * Excel specific native type convertor.
 * @author prokarma 
 * @version 1.0
 */
public class ExcelNativeType extends GenericNativeTypeConvertor {
    
    
    /**
     * Override to handle specific format as required by excel.  Excel follow a special non standard date notation.
     */
    protected java.util.Calendar convertToCalendar(String format) {
        StringTokenizer tokenizer = new StringTokenizer(convertToString(),".");
        int days = Integer.parseInt(tokenizer.nextToken());
        //hours are always percentage of 24 hours
       /* float hours = 0.0f;
        if(tokenizer.hasMoreTokens()) {
            //if greater than 2 digits insert a dot as this represents a percentage of 24 hour
            StringBuilder hourTok = new StringBuilder(tokenizer.nextToken());
            if(hourTok.length() > 2) {
                hourTok = hourTok.insert(2, '.');
            }
            hours = Float.parseFloat(hourTok.toString());
        }*/
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(1900, 0, 0);
        cal.add(Calendar.DATE, days - 1);
        //TODO : Calculate the hour of the day.
        return cal;
    }
}
