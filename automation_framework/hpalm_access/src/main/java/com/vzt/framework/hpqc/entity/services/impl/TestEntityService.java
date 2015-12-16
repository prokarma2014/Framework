package com.vzt.framework.hpqc.entity.services.impl;
 
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vzt.framework.hpqc.logger.VzTLogger;
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
import com.vzt.framework.hpqc.utils.Constants;
import com.vzt.framework.hpqc.utils.URLBuilder;
 
/**
 * @author Cassian Raja
 */
public class TestEntityService extends BaseEntityService
{
    private static final Object NULL = null;
    private static String myEntityType = "tests";
 
    public TestEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword, final String anEnv)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword, anEnv);
    }
 
    public TestEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword);
    }
 
    public EntityBean getTestById(final String entityId)
    {
            EntityBean entityBean = null;
            entityBean = super.getEntityById(myEntityType, entityId);
 
            return entityBean;
    }
 
    public EntityBean createTest(String entityName, String parentId,
               String testId, String testConfigId)
    {
            EntityBean entityBean = null;
            entityBean = super.createEntity(myEntityType, entityName, parentId,
                        testId, testConfigId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getTestsByFolderId(final String testFolderId)
    {
            String targetURL = null;
            ListOfEntitiesBean testEntities = null;
            String entityURL = null;
            entityURL = getEntityURL(myEntityType);
 
            // if (getBaseURL() == NULL || getDomain() == NULL || getProject() ==
            // NULL
            // || getEnv() == NULL)
            // {
            // targetURL = Constants.BASE_URL + entityURL + "?query="
            // + "{test-folder.id[" + testFolderId + "]}";
            // }
            // else
            // {
            targetURL = URLBuilder.createGetURLWithQuery(getBaseURL(), getDomain(),
                        getProject(), "tests?query=", "{test-folder.id[" + testFolderId
                                    + "]}");
            // }
            WebTarget getTestsWebTarget = null;
 
            try
            {
               getTestsWebTarget = myRestClient.target(new URI(targetURL));
            }
            catch (URISyntaxException ex)
            {
               VzTLogger.e("URISyntaxException {}", ex.getMessage());
            }
 
            try
            {
               Invocation.Builder getTestsInvocationBuilder = getTestsWebTarget
                           .request().accept(MediaType.APPLICATION_XML)
                           .header("Cookie", myAuthenticationCookie)
                           .header("Cookie", myQcSessionCookie);
 
               Invocation getTestsInvocation = getTestsInvocationBuilder
                           .build("GET");
 
               Response getTestsEntityResponse = getTestsInvocation.invoke();
 
               if (getTestsEntityResponse.getStatus() == Constants.GET_SUCCESS)
               {
 
                        testEntities = getTestsEntityResponse
                                    .readEntity(ListOfEntitiesBean.class);
               }
            }
            catch (Exception ex)
            {
 
               VzTLogger.e("Exception {}", ex.getMessage());
            }
 
            return testEntities;
    }
 
    public ListOfEntitiesBean getTestConfigsByTestId(final String anEntityId)
    {
            String targetURL = null;
            ListOfEntitiesBean testConfigsEntity = null;
//          String entityURL = null;
//          entityURL = getEntityURL(myEntityType);
 
            // if (getBaseURL() == NULL || getDomain() == NULL || getProject() ==
            // NULL
            // || getEnv() == NULL)
            // {
            // targetURL = Constants.BASE_URL + entityURL + "/" + "?query="
            // + "{test.id[" + anEntityId + "]}";
            // }
            // else
            // {
            targetURL = URLBuilder.createGetURLWithQuery(getBaseURL(), getDomain(),
                        getProject(), "test-configs?query=", "{test.id[" + anEntityId
                                    + "]}");
            // }
 
            WebTarget getTestConfigWebTarget = null;
 
            try
            {
               getTestConfigWebTarget = myRestClient.target(new URI(targetURL));
 
               Invocation.Builder getTestConfigInvocationBuilder = getTestConfigWebTarget
                           .request().accept(MediaType.APPLICATION_XML)
                           .header("Cookie", myAuthenticationCookie)
                           .header("Cookie", myQcSessionCookie);
 
               Invocation getTestConfigInvocation = getTestConfigInvocationBuilder
                           .build("GET");
 
               Response getTestConfigResponse = getTestConfigInvocation.invoke();
 
               if (getTestConfigResponse.getStatus() == Constants.GET_SUCCESS)
               {
                        testConfigsEntity = getTestConfigResponse
                                    .readEntity(ListOfEntitiesBean.class);
               }
 
            }
            catch (URISyntaxException ex)
            {
               VzTLogger.e("URISyntaxException {}", ex.getMessage());
            }
            catch (Exception ex)
            {
               VzTLogger.e("Exception {}", ex.getMessage());
            }
 
            return testConfigsEntity;
    }
 
    // @Override
    // public String getTestFromTestInstances(final int aTestSetId)
    // {
    //
    // String testResponse = null;
    // int theTestId = 0;
    // String subStr = null;
    // String targetURL = null;
    // String testInstanceResponse = null;
    //
    // if (getBaseURL() == NULL || getDomain() == NULL || getProject() == NULL
    // || getEnv() == NULL)
    // {
    //
    // targetURL = Constants.BASE_URL + Constants.TEST_SET_URL + "/"
    // + aTestSetId;
    //
    // }
    //
    // else
    // {
    //
    // targetURL = URLBuilder.createGetURLWithQuery(getBaseURL(),
    // getDomain(), getProject(),
    //
    // "test-instances?query=", "{test-set.id[" + aTestSetId
    // + "]}");
    //
    // }
    //
    // WebTarget getTestInstanceWebTarget = null;
    //
    // try
    // {
    //
    // getTestInstanceWebTarget = myRestClient.target(new URI(targetURL));
    //
    // Invocation.Builder getTestInstanceInvocationBuilder =
    // getTestInstanceWebTarget
    // .request()
    //
    // .accept(MediaType.APPLICATION_XML)
    // .header("Cookie", myAuthenticationCookie)
    //
    // .header("Cookie", myQcSessionCookie);
    //
    // Invocation getTestInstanceInvocation = getTestInstanceInvocationBuilder
    // .build("GET");
    //
    // Response getTestInstanceEntityResponse = getTestInstanceInvocation
    // .invoke();
    //
    // if (getTestInstanceEntityResponse.getStatus() == Constants.GET_SUCCESS)
    // {
    //
    // ListOfEntitiesBean testInstanceEntities = getTestInstanceEntityResponse
    //
    // .readEntity(ListOfEntitiesBean.class);
    //
    // testInstanceResponse = GetResponseBody
    // .getResponseEntitiesAsListOfString(testInstanceEntities);
    //
    // }
    //
    // }
    // catch (URISyntaxException ex)
    // {
    //
    // VzTLogger.e("URISyntaxException {}", ex.getMessage());
    //
    // }
    //
    // catch (Exception ex)
    // {
    //
    // VzTLogger.e("Exception {}", ex.getMessage());
    //
    // }
    //
    // subStr = testInstanceResponse.substring(
    // testInstanceResponse.indexOf("test-id"),
    //
    // testInstanceResponse.indexOf(']',
    // testInstanceResponse.indexOf("test-id")));
    //
    // theTestId = Integer.parseInt(subStr.substring(subStr.indexOf('[') + 1));
    //
    // return testResponse;
    //
    // }
}
 