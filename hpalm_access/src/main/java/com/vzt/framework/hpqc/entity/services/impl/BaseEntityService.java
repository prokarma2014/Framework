package com.vzt.framework.hpqc.entity.services.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.vzt.framework.hpqc.ALMInitializer;
import com.vzt.framework.hpqc.entity.services.ALMService;
import com.vzt.framework.hpqc.logger.VzTLogger;
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.Field;
import com.vzt.framework.hpqc.model.alm.Fields;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
import com.vzt.framework.hpqc.utils.Constants;
import com.vzt.framework.hpqc.utils.InputDataLoader;
import com.vzt.framework.hpqc.utils.URLBuilder;

/**
 * @author xprk091
 */
public class BaseEntityService extends ALMInitializer implements ALMService {
	private static final Object NULL = null;

	public BaseEntityService() {

	}

	public BaseEntityService(final String aBaseURL, final String aDomain,
			final String aProject, final String aUserName,
			final String aPassword, final String anEnv) {
		super(aBaseURL, aDomain, aProject, aUserName, aPassword, anEnv);
	}

	public BaseEntityService(final String aBaseURL, final String aDomain,
			final String aProject, final String aUserName,
			final String aPassword) {
		super(aBaseURL, aDomain, aProject, aUserName, aPassword);
	}

	@Override
	public EntityBean getEntityById(final String entityType,
			final String entityId) {
		String targetURL = null;
		EntityBean entityBean = null;
		String entityURL = null;
		entityURL = getEntityURL(entityType);

		if (getBaseURL() == NULL || getDomain() == NULL || getProject() == NULL
				|| getEnv() == NULL) {
			targetURL = Constants.BASE_URL + entityURL + "/" + entityId;
		} else {
			targetURL = URLBuilder.createGetURLWithId(getBaseURL(),
					getDomain(), getProject(), entityType, entityId);
		}

		WebTarget getEntityWebTarget = null;

		try {
			getEntityWebTarget = myRestClient.target(new URI(targetURL));
		} catch (URISyntaxException ex) {
			VzTLogger.e("URISyntaxException {}", ex.getMessage());
		}

		try {
			Invocation.Builder getEntityInvocationBuilder = getEntityWebTarget
					.request().header("Cookie", myAuthenticationCookie)
					.header("Cookie", myQcSessionCookie)
					.accept(MediaType.APPLICATION_XML);

			Invocation getEntityInvocation = getEntityInvocationBuilder
					.build("GET");

			Response getEntityResponse = getEntityInvocation.invoke();

			if (getEntityResponse.getStatus() == Constants.GET_SUCCESS) {
				entityBean = getEntityResponse.readEntity(EntityBean.class);
			}
		} catch (Exception ex) {
			VzTLogger.e("Exception {}", ex.getMessage());
		}
		return entityBean;
	}

	@Override
	public ListOfEntitiesBean getEntityByName(String entityType,
			String entityName) {
		String targetURL = null;
		ListOfEntitiesBean entitiesBean = null;
		String entityURL = null;
		entityURL = getEntityURL(entityType);

		if (StringUtils.isNotEmpty(entityName)) {
			targetURL = URLBuilder.createGetURLWithQuery(myBaseURL, myDomain,
					myProject, entityType + "?query=", "{name[" + entityName
							+ "]}");
		} else {
			targetURL = Constants.BASE_URL + entityURL + "/" + entityName;
		}

		WebTarget getEntityWebTarget = null;

		try {
			getEntityWebTarget = myRestClient.target(new URI(targetURL));
		} catch (URISyntaxException ex) {
			VzTLogger.e("URISyntaxException {}", ex.getMessage());
		}

		try {
			Invocation.Builder getEntityInvocationBuilder = getEntityWebTarget
					.request().header("Cookie", myAuthenticationCookie)
					.header("Cookie", myQcSessionCookie)
					.accept(MediaType.APPLICATION_XML);

			Invocation getEntityInvocation = getEntityInvocationBuilder
					.build("GET");

			Response getEntityResponse = getEntityInvocation.invoke();

			if (getEntityResponse.getStatus() == Constants.GET_SUCCESS) {
				entitiesBean = getEntityResponse
						.readEntity(ListOfEntitiesBean.class);
			}
		} catch (Exception ex) {
			VzTLogger.e("Exception {}", ex.getMessage());
		}
		return entitiesBean;
	}

	@Override
	public ListOfEntitiesBean getListOfEntities(final String entityType,
			String parentId) {
		String targetURL = null;
		ListOfEntitiesBean entitiesBean = null;
		String entityURL = null;
		entityURL = getEntityURL(entityType);

		if (StringUtils.isNotEmpty(parentId)) {
			targetURL = URLBuilder.createGetURLWithQuery(getBaseURL(),
					getDomain(), getProject(), entityType + "?query=",
					"{parent-id[" + parentId + "]}");
		} else if (getBaseURL() == NULL || getDomain() == NULL
				|| getProject() == NULL || getEnv() == NULL) {
			targetURL = Constants.BASE_URL + entityURL;
		} else {
			targetURL = URLBuilder.createGetURL(getBaseURL(), getDomain(),
					getProject(), entityType);
		}

		WebTarget getEntitiesWebTarget = null;

		try {
			getEntitiesWebTarget = myRestClient.target(new URI(targetURL));
		} catch (URISyntaxException ex) {
			VzTLogger.e("URISyntaxException {}", ex.getMessage());
		}

		try {
			Invocation.Builder getEntitiesInvocationBuilder = getEntitiesWebTarget
					.request().accept(MediaType.APPLICATION_XML)
					.header("Cookie", myAuthenticationCookie)
					.header("Cookie", myQcSessionCookie);

			Invocation getEntitiesInvocation = getEntitiesInvocationBuilder
					.build("GET");

			Response getEntitiesResponse = getEntitiesInvocation.invoke();

			if (getEntitiesResponse.getStatus() == Constants.GET_SUCCESS) {
				entitiesBean = getEntitiesResponse
						.readEntity(ListOfEntitiesBean.class);
			}
		} catch (Exception ex) {

			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return entitiesBean;
	}

	@Override
	public EntityBean createEntity(String entityType, String entityName,
			String parentId, String testId, String testConfigId) {
		String targetURL = null;
		String entityURL = null;
		entityURL = getEntityURL(entityType);

		if (getBaseURL() == NULL || getDomain() == NULL || getProject() == NULL
				|| getEnv() == NULL) {
			targetURL = Constants.BASE_URL + entityURL;
		} else {
			targetURL = URLBuilder.createPostURL(getBaseURL(), getDomain(),
					getProject(), entityType);
		}

		EntityBean newEntity = createRequestData(entityType, entityName,
				parentId, testId, testConfigId);

		WebTarget postEntityWebTarget = null;
		EntityBean responseBean = null;

		try {
			postEntityWebTarget = myRestClient.target(new URI(targetURL));
		} catch (URISyntaxException ex) {
			VzTLogger.e("URISyntaxException {}", ex.getMessage());
		}

		try {
			Invocation.Builder postEntityInvocationBuilder = postEntityWebTarget
					.request().header("Cookie", myAuthenticationCookie)
					.header("Cookie", myQcSessionCookie)
					.header("Content-Type", "*/*").header("Accept", "*/*");

			Response postEntityResponse = postEntityInvocationBuilder
					.post(Entity.entity(newEntity, MediaType.APPLICATION_XML));

			responseBean = postEntityResponse.readEntity(EntityBean.class);

		} catch (Exception ex) {

			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return responseBean;
	}

	@Override
	public EntityBean updateEntity(String entityType, String entityId,
			String entityStatus) {
		String targetURL = null;
		String entityURL = null;
		entityURL = getEntityURL(entityType);

		if (getBaseURL() == NULL || getDomain() == NULL || getProject() == NULL
				|| getEnv() == NULL) {
			targetURL = Constants.BASE_URL + entityURL + "/" + entityId;
		} else {
			targetURL = URLBuilder.createGetURLWithId(getBaseURL(),
					getDomain(), getProject(), entityType, entityId);
		}

		EntityBean updatedEntity = createRequestData(entityType, null, null,
				null, null);

		if (entityType.equals("test-instances")) {
			List<Field> allFields = updatedEntity.getFields().getListOfFields();
			List<String> allValues = new ArrayList<String>();
			allValues.add(entityStatus);

			for (Field fld : allFields) {
				if (fld.getName().equals("status")) {
					fld.setValue(allValues);
				}
			}

		}

		WebTarget postEntityWebTarget = null;
		EntityBean postEntityResponse = null;

		try {
			postEntityWebTarget = myRestClient.target(new URI(targetURL));
		} catch (URISyntaxException ex) {
			VzTLogger.e("URISyntaxException {}", ex.getMessage());
		}

		try {
			Invocation.Builder postEntityInvocationBuilder = postEntityWebTarget
					.request().header("Cookie", myAuthenticationCookie)
					.header("Cookie", myQcSessionCookie)
					.header("Content-Type", "*/*").header("Accept", "*/*");

			postEntityResponse = postEntityInvocationBuilder.put(
					Entity.entity(updatedEntity, MediaType.APPLICATION_XML),
					EntityBean.class);
		} catch (Exception ex) {
			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return postEntityResponse;
	}

	@Override
	public void deleteEntity(String entityType, String entityId) {

	}

	protected EntityBean createRequestData(String entityType,
			String entityName, String parentId, String testId,
			String testConfigId) {
		EntityBean newEntity = new EntityBean();
		Fields fields = new Fields();
		List<Field> allFields = new ArrayList<Field>();
		String keyName = null;
		int fieldsCount = 0;

		if (entityType.equals("defects")) {
			fieldsCount = 12;
		} else if (entityType.equals("test-sets")) {
			fieldsCount = 3;
		} else if (entityType.equals("test-set-folders")) {
			fieldsCount = 0;
		} else if (entityType.equals("test-instances")) {
			fieldsCount = 6;
		} else if (entityType.equals("runs")) {
			fieldsCount = 5;
		} else if (entityType.equals("test-folders")) {
			fieldsCount = 0;
		} else if (entityType.equals("tests")) {
			fieldsCount = 6;
		}

		entityType = entityType.substring(0, entityType.length() - 1);

		// if (entityType.equals("test"))
		// {
		// StringBuilder entityTypeBuilder = new StringBuilder(entityType);
		// entityTypeBuilder.append("case");
		// entityType = entityTypeBuilder.toString();
		// }

		for (int count = 0; count < fieldsCount; count++) {
			Field field = new Field();

			List<String> allValues = new ArrayList<String>();

			Map<String, String> fieldMap = null;
			try {
				fieldMap = InputDataLoader.getRequiredFieldsMap(count,
						entityType + ".properties");
			} catch (IOException ex) {
				VzTLogger.e("Exception {}", ex.getMessage());
			}
			Iterator<Entry<String, String>> itr = fieldMap.entrySet()
					.iterator();
			while (itr.hasNext()) {
				Entry<String, String> entry = itr.next();
				keyName = entry.getKey();
				allValues.add(entry.getValue());
			}

			field.setName(keyName);
			field.setValue(allValues);

			allFields.add(field);
		}

		if (!(entityName == null || entityName.equals(""))) {
			List<String> allValues = new ArrayList<String>();
			allValues.add(entityName);

			Field field = new Field();
			field.setName("name");
			field.setValue(allValues);
			allFields.add(field);
		}

		if (!(parentId == null || parentId.equals(""))) {
			List<String> allValues = new ArrayList<String>();
			allValues.add(parentId);

			Field field = new Field();
			if (!(entityType.equals("test-instance"))) {
				field.setName("parent-id");
			} else {
				field.setName("cycle-id");
			}
			field.setValue(allValues);
			allFields.add(field);
		}

		if (!(testId == null || testId.equals(""))) {
			List<String> allValues = new ArrayList<String>();
			allValues.add(testId);

			Field field = new Field();
			field.setName("test-id");
			field.setValue(allValues);
			allFields.add(field);
		}

		if (!(testConfigId == null || testConfigId.equals(""))) {
			List<String> allValues = new ArrayList<String>();
			allValues.add(testConfigId);

			Field field = new Field();
			field.setName("test-config-id");
			field.setValue(allValues);
			allFields.add(field);
		}

		fields.setListOfFields(allFields);

		newEntity.setFields(fields);
		newEntity.setType(entityType);

		return newEntity;
	}

	/**
	 * @param entityType
	 * @return
	 */
	public String getEntityURL(String entityType) {
		String entityURL = null;

		if (entityType.equals("test-folders")) {
			entityURL = Constants.TEST_FOLDER_URL;
		} else if (entityType.equals("tests")) {
			entityURL = Constants.TESTS_URL;
		} else if (entityType.equals("test-set-folders")) {
			entityURL = Constants.TEST_SET_FOLDER_URL;
		} else if (entityType.equals("test-sets")) {
			entityURL = Constants.TEST_SET_URL;
		} else if (entityType.equals("test-instances")) {
			entityURL = Constants.TEST_INSTANCES_URL;
		} else if (entityType.equals("test-configs")) {
			entityURL = Constants.TEST_CONFIG_URL;
		} else if (entityType.equals("defects")) {
			entityURL = Constants.DEFECT_URL;
		}

		return entityURL;
	}
}