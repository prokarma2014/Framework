package com.vzt.framework.hpqc.entity.services.impl;
 
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
 
/**
 * @author Cassian Raja
 */
public class TestSetEntityService extends BaseEntityService
{
    private static String myEntityType = "test-sets";
 
    /**
     * @param string
     * @param string2
     * @param string3
     */
    public TestSetEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword, final String anEnv)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword, anEnv);
    }
 
    public TestSetEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword);
    }
 
    public EntityBean getTestSetById(final String entityId)
    {
            EntityBean entityBean = null;
            entityBean = super.getEntityById(myEntityType, entityId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getTestSetByName(final String entityName)
    {
            ListOfEntitiesBean entitiesBean = null;
            entitiesBean = super.getEntityByName(myEntityType, entityName);
 
            return entitiesBean;
    }
 
    public EntityBean createTestSet(String entityName, String parentId,
               String testId, String testConfigId)
    {
            EntityBean entityBean = null;
            entityBean = super.createEntity(myEntityType, entityName, parentId,
                        testId, testConfigId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getListOfTestSet(String parentId)
    {
            ListOfEntitiesBean entitiesBean = null;
            entitiesBean = super.getListOfEntities(myEntityType, parentId);
 
            return entitiesBean;
    }
 
}