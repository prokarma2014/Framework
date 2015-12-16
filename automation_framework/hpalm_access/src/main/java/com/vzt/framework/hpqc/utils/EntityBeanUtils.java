package com.vzt.framework.hpqc.utils;

import java.util.ArrayList;
import java.util.List;

import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.Field;
import com.vzt.framework.hpqc.model.alm.Fields;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;

public class EntityBeanUtils {

	public static String getEntityIdWithName(ListOfEntitiesBean responseBeans,
			String entityName) {

		List<EntityBean> allEntities = responseBeans.getListOfEntity();
		String id = null;
		EntityBean matchingEntity = null;
		boolean isMatchingEntityFound = false;

		for (EntityBean entity : allEntities) {
			Fields fields = entity.getFields();
			List<Field> allFields = fields.getListOfFields();
			for (Field fld : allFields) {
				if (fld.getName().equals("name")
						&& fld.getListOfValues().get(0).equals(entityName)) {
					matchingEntity = entity;
					isMatchingEntityFound = true;
					break;
				}
			}

			if (isMatchingEntityFound) {
				break;
			}
		}

		if (!(null == matchingEntity)) {
			Fields fields = matchingEntity.getFields();
			List<Field> allFields = fields.getListOfFields();
			for (Field fld : allFields) {
				if (fld.getName().equals("id")) {
					id = fld.getListOfValues().get(0);
					break;
				}
			}
		}

		return id;
	}

	public static String getEntityId(EntityBean responseBean) {
		Fields fields = responseBean.getFields();
		List<Field> allFields = fields.getListOfFields();
		String id = null;
		for (Field fld : allFields) {
			if (fld.getName().equals("id")) {
				id = fld.getListOfValues().get(0);
			}
		}
		return id;
	}

	public static String getEntityName(EntityBean responseBean) {
		Fields fields = responseBean.getFields();
		List<Field> allFields = fields.getListOfFields();
		String name = null;
		for (Field fld : allFields) {
			if (fld.getName().equals("name")) {
				name = fld.getListOfValues().get(0);
			}
		}
		return name;
	}
	
	public static String getEntityHierarchyPath(EntityBean responseBean) {
		Fields fields = responseBean.getFields();
		List<Field> allFields = fields.getListOfFields();
		String id = null;
		for (Field fld : allFields) {
			if (fld.getName().equals("hierarchical-path")) {
				id = fld.getListOfValues().get(0);
			}
		}
		return id;
	}

	public static List<String> getEntityIds(ListOfEntitiesBean responseBeans) {

		List<EntityBean> allEntities = responseBeans.getListOfEntity();
		List<String> allIds = new ArrayList<String>();
		String id = null;

		for (EntityBean entity : allEntities) {
			Fields fields = entity.getFields();
			List<Field> allFields = fields.getListOfFields();
			for (Field fld : allFields) {
				if (fld.getName().equals("id")) {
					id = fld.getListOfValues().get(0);
					break;
				}
			}
			allIds.add(id);
		}
		return allIds;
	}
}
