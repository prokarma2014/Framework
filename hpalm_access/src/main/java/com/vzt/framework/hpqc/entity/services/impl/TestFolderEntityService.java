
package com.vzt.framework.hpqc.entity.services.impl;
 
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
 
/**
 * @author xprk091
 */
public class TestFolderEntityService extends BaseEntityService
{
    private static String myEntityType = "test-folders";
 
    /**
     * @param string
     * @param string2
     * @param string3
     */
    public TestFolderEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword, final String anEnv)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword, anEnv);
    }
    public TestFolderEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword);
    }
 
    public EntityBean getTestFolderById(final String entityId)
    {
            EntityBean entityBean = null;
            entityBean = super.getEntityById(myEntityType, entityId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getTestFolderByName(final String entityName)
    {
            ListOfEntitiesBean entitiesBean = null;
            entitiesBean = super.getEntityByName(myEntityType, entityName);
 
            return entitiesBean;
    }
 
    public EntityBean createTestFolder(String entityName, String parentId,
               String testId, String optionalId)
    {
            EntityBean entityBean = null;
            entityBean = super.createEntity(myEntityType, entityName, parentId,
                        testId, optionalId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getListOfTestFolders(String parentId)
    {
            ListOfEntitiesBean entitiesBean = null;
            entitiesBean = super.getListOfEntities(myEntityType, parentId);
 
            return entitiesBean;
    }
 
}