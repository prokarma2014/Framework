package com.vzt.framework.core.annotations;

import java.util.Calendar;

import com.vzt.framework.data_access.NativeTypeConvertor;
import com.vzt.framework.data_access.TableBasedDataAccess;

/**
 * This class uses the relevant native type converter from the datasource to
 * convert the field to relevant value based on its type value. Type conversion
 * happens only for primitives, strings and date objects.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class TypeConvertor {

	private final NativeTypeConvertor nativeType;

	/**
	 * @param nativeType
	 */
	public TypeConvertor(NativeTypeConvertor nativeType) {
		this.nativeType = nativeType;
	}

	/**
	 * Returns a optional value containing the converted data or empty data.
	 * Conversion can happen only for int,long,float,double,calendar and string.
	 * 
	 * @param field
	 *            field type of the field.
	 * @param dataValue
	 *            data value retrieved from data source.
	 * @return Optional data object.
	 */
	public Object convert(Class<?> fieldType, Object dataValue) {
		Object convertedValue = null;
		if (!TableBasedDataAccess.EMPTY_CELL.equals(dataValue)) {
			nativeType.nativeType(dataValue);
			if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
				convertedValue = nativeType.asInt();
			} else if (double.class.equals(fieldType)) {
				convertedValue = nativeType.asDouble();
			} else if (float.class.equals(fieldType)) {
				convertedValue = ((Double) nativeType.asDouble()).floatValue();
			} else if (Calendar.class.isAssignableFrom(fieldType)) {
				convertedValue = nativeType.asCalendar("MM/DD/YYYY");
			} else if (String.class.equals(fieldType)) {
				convertedValue = nativeType.asString();
			} else if (long.class.equals(fieldType)
					|| Long.class.equals(fieldType)) {
				convertedValue = Long.parseLong(dataValue.toString());
			} else if (boolean.class.equals(fieldType)) {
				convertedValue = Boolean.parseBoolean(dataValue.toString());
			} else {
				convertedValue = dataValue;
			}
		}
		return convertedValue;
	}
}