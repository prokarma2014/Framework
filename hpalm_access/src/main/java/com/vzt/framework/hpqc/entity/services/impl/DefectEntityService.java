package com.vzt.framework.hpqc.entity.services.impl;
 
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
 
/**
 * @author Cassian Raja
 */
public class DefectEntityService extends BaseEntityService
{
    private static String myEntityType = "defects";
 
    /**
     * @param string
     * @param string2
     * @param string3
     */
    public DefectEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword, final String anEnv)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword, anEnv);
    }
 
    public DefectEntityService(final String aBaseURL, final String aDomain,
               final String aProject, final String aUserName,
               final String aPassword)
    {
            super(aBaseURL, aDomain, aProject, aUserName, aPassword);
    }
 
    public EntityBean getDefectById(final String entityId)
    {
            EntityBean entityBean = null;
            entityBean = super.getEntityById(myEntityType, entityId);
 
            return entityBean;
    }
 
    public EntityBean createDefect(String entityName, String parentId,
               String testId, String optionalId)
    {
            EntityBean entityBean = null;
            entityBean = super.createEntity(myEntityType, entityName, parentId,
                        testId, optionalId);
 
            return entityBean;
    }
 
    public ListOfEntitiesBean getListOfDefects(String parentId)
    {
            ListOfEntitiesBean entitiesBean = null;
            entitiesBean = super.getListOfEntities(myEntityType, parentId);
 
            return entitiesBean;
    }
 
    public EntityBean updateDefect(String entityId)
    {
            EntityBean entityBean = null;
            entityBean = super.updateEntity(myEntityType, entityId, "");
 
            return entityBean;
    }
 
    // public void deleteDefect(String entityId)
    // {
    //
    // }
 
}
 