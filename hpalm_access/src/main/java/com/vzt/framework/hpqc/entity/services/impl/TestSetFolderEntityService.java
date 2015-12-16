/**
 * 
 */
package com.vzt.framework.hpqc.entity.services.impl;
 
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
 
/**
 * @author xprk091
 */
public class TestSetFolderEntityService extends BaseEntityService
{
 
    private static String myEntityType = "test-set-folders";
 
    /**
     * @param string
     * @param string2
     * @param string3
     */
    public TestSetFolderEntityService(final String aBaseURL,
               final String aDomain, final String aProject,
               final String aUserName, final String aPassword, final String anEnv)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword, anEnv);
    }
 
    public TestSetFolderEntityService(final String aBaseURL,
               final String aDomain, final String aProject,
               final String aUserName, final String aPassword)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword);
    }
 
    public EntityBean getTestSetFolderById(final String entityId)
    {
            EntityBean entityBean = null;
            entityBean = super.getEntityById(myEntityType, entityId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getTestSetFolderByName(final String entityName)
    {
            ListOfEntitiesBean entitiesBean = null;
            entitiesBean = super.getEntityByName(myEntityType, entityName);
 
            return entitiesBean;
    }
 
    public EntityBean createTestSetFolder(String entityName, String parentId,
               String testId, String testConfigId)
    {
            EntityBean entityBean = null;
            entityBean = super.createEntity(myEntityType, entityName, parentId,
                        testId, testConfigId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getListOfTestSetFolder(String parentId)
    {
            ListOfEntitiesBean entitiesBean = null;
            entitiesBean = super.getListOfEntities(myEntityType, parentId);
 
            return entitiesBean;
    }
 
}