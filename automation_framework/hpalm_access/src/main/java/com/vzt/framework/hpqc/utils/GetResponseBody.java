package com.vzt.framework.hpqc.utils;

import java.util.List;

import com.vzt.framework.hpqc.logger.VzTLogger;
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.Field;
import com.vzt.framework.hpqc.model.alm.Fields;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;

public class GetResponseBody {

	private GetResponseBody() {

	}

	public static void printResponseEntity(final EntityBean entity) {

		Fields fields = entity.getFields();
		List<Field> fieldList = fields.getListOfFields();
		String type = entity.getType();
		VzTLogger.i("Entity Type {}", type);

		for (Field fld : fieldList) {
			String fldName = fld.getName();

			if (!fldName.startsWith("user-")) {
				List<String> fldValues = fld.getListOfValues();
				for (String value : fldValues) {
					VzTLogger.i(fldName + "---> {}", value);

				}

			}

		}
	}

	public static void printResponseEntities(final ListOfEntitiesBean entities) {

		List<EntityBean> entityList = entities.getListOfEntity();

		VzTLogger.i("Total Entities:  {}", String.valueOf(entities.getTotalResults()));

		for (EntityBean entityBean : entityList) {

			Fields fields = entityBean.getFields();

			List<Field> fieldList = fields.getListOfFields();

			String type = entityBean.getType();

			VzTLogger.i("Entity Type :  {}", type);

			for (Field fld : fieldList) {
				String fldName = fld.getName();
				if (!fldName.startsWith("user-")) {
					List<String> fldValues = fld.getListOfValues();
					for (String value : fldValues) {
						VzTLogger.i(fldName + "---> {}", value);
					}

				}

			}

		}

	}

	/**
	 * @param anEntity
	 * @return
	 */
	public static String getResponseEntityAsString(final EntityBean anEntity) {

		return anEntity.toString();

	}

	/**
	 * @param anEntity
	 * @return
	 */
	public static String getResponseEntitiesAsListOfString(final ListOfEntitiesBean listOfEntities) {

		return listOfEntities.getListOfEntity().toString();

	}

}
