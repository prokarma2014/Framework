package com.vzt.framework.hpqc.entity.services.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vzt.framework.hpqc.logger.VzTLogger;
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
import com.vzt.framework.hpqc.utils.Constants;
import com.vzt.framework.hpqc.utils.EntityBeanUtils;
import com.vzt.framework.hpqc.utils.URLBuilder;

/**
 * @author Cassian Raja
 */
public class TestInstanceEntityService extends BaseEntityService {
	private static final Object NULL = null;
	private static String myEntityType = "test-instances";

	/**
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 */
	public TestInstanceEntityService(final String aBaseURL,
			final String aDomain, final String aProject,
			final String aUserName, final String aPassword, final String anEnv) {
		super(aBaseURL, aDomain, aProject, aUserName, aPassword, anEnv);
	}

	public TestInstanceEntityService(final String aBaseURL,
			final String aDomain, final String aProject,
			final String aUserName, final String aPassword) {
		super(aBaseURL, aDomain, aProject, aUserName, aPassword);
	}

	public EntityBean getTestInstanceById(final String entityId) {
		EntityBean entityBean = null;
		entityBean = super.getEntityById(myEntityType, entityId);

		return entityBean;
	}

	public EntityBean createTestInstance(String entityName,
			final String parentId, final String testId,
			final String testConfigId) {
		String targetURL = null;
		String entityURL = null;
		entityURL = getEntityURL(myEntityType);

		if (getBaseURL() == NULL || getDomain() == NULL || getProject() == NULL
				|| getEnv() == NULL) {
			targetURL = Constants.BASE_URL + entityURL;
		}

		else {
			targetURL = URLBuilder.createPostURL(getBaseURL(), getDomain(),
					getProject(), "test-instances");
		}

		// Get the Test Configs for a Test Case
		ListOfEntitiesBean responseBeans = null;
		List<String> testConfigIds = new ArrayList<String>();

		TestEntityService testEntity = new TestEntityService(myBaseURL,
				myDomain, myProject, myUserName, myPassword);
		responseBeans = testEntity.getTestConfigsByTestId(testId);
		testConfigIds = EntityBeanUtils.getEntityIds(responseBeans);

		EntityBean newTestInstanceEntity = createRequestData("test-instances",
				entityName, parentId, testId, testConfigIds.get(0));

		WebTarget postTestInstanceWebTarget = null;
		EntityBean postTestInstanceEntityResponse = null;

		try {
			postTestInstanceWebTarget = myRestClient.target(new URI(targetURL));
		} catch (URISyntaxException ex) {
			VzTLogger.e("URISyntaxException {}", ex.getMessage());
		}

		try {
			Invocation.Builder postTestInstanceInvocationBuilder = postTestInstanceWebTarget
					.request().header("Cookie", myAuthenticationCookie)
					.header("Cookie", myQcSessionCookie)
					.header("Content-Type", "*/*").header("Accept", "*/*");

			postTestInstanceEntityResponse = postTestInstanceInvocationBuilder
					.post(Entity.entity(newTestInstanceEntity,
							MediaType.APPLICATION_XML), EntityBean.class);
		} catch (Exception ex) {
			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return postTestInstanceEntityResponse;
	}

	public ListOfEntitiesBean getTestInstancesByTestSetId(final String testSetId) {
		String targetURL = null;
		ListOfEntitiesBean testInstanceEntities = null;
		String entityURL = null;
		entityURL = getEntityURL(myEntityType);
//
//		if (getBaseURL() == NULL || getDomain() == NULL || getProject() == NULL
//				|| getEnv() == NULL) {
//			targetURL = Constants.BASE_URL + entityURL + "?query="
//					+ "{test-set.id[" + testSetId + "]}";
//		} else {
			targetURL = URLBuilder.createGetURLWithQuery(getBaseURL(),
					getDomain(), getProject(), "test-instances?query=",
					"{test-set.id[" + testSetId + "]}");
//		}

		WebTarget getEntityWebTarget = null;

		try {
			getEntityWebTarget = myRestClient.target(new URI(targetURL));
		} catch (URISyntaxException ex) {
			VzTLogger.e("URISyntaxException {}", ex.getMessage());
		}

		try {
			Invocation.Builder getEntityInvocationBuilder = getEntityWebTarget
					.request().accept(MediaType.APPLICATION_XML)
					.header("Cookie", myAuthenticationCookie)
					.header("Cookie", myQcSessionCookie);

			Invocation getEntityInvocation = getEntityInvocationBuilder
					.build("GET");

			Response getEntityEntityResponse = getEntityInvocation.invoke();

			if (getEntityEntityResponse.getStatus() == Constants.GET_SUCCESS) {

				testInstanceEntities = getEntityEntityResponse
						.readEntity(ListOfEntitiesBean.class);
			}
		} catch (Exception ex) {

			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return testInstanceEntities;
	}

	public EntityBean updateTestInstance(String entityId, String entityStatus) {
		EntityBean entityBean = null;
		entityBean = super.updateEntity(myEntityType, entityId, entityStatus);

		return entityBean;
	}
}