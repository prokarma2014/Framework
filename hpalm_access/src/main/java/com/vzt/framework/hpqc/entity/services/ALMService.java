package com.vzt.framework.hpqc.entity.services;

import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;

public interface ALMService {

	public EntityBean getEntityById(final String entityType,
			final String entityId);

	public ListOfEntitiesBean getEntityByName(String entityType, String entityName);

	public ListOfEntitiesBean getListOfEntities(final String entityType,
			String hierarchicalPath);

	public EntityBean createEntity(String entityType, String entityName,
			String parentId, String testId, String testConfigId);

	public EntityBean updateEntity(String entityType, String entityId, String entityStatus);

	public void deleteEntity(String entityType, String entityId);


}
