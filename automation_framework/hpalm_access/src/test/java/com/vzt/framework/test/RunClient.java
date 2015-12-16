package com.vzt.framework.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vzt.framework.hpqc.entity.services.impl.TestEntityService;
import com.vzt.framework.hpqc.entity.services.impl.TestFolderEntityService;
import com.vzt.framework.hpqc.entity.services.impl.TestInstanceEntityService;
import com.vzt.framework.hpqc.entity.services.impl.TestSetEntityService;
import com.vzt.framework.hpqc.entity.services.impl.TestSetFolderEntityService;
import com.vzt.framework.hpqc.model.alm.EntityBean;
import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;
import com.vzt.framework.hpqc.utils.Constants;
import com.vzt.framework.hpqc.utils.EntityBeanUtils;

public class RunClient {

	private static String TEST_CASE_NAME = "testCaseName=VerifyVINCreation";
	private static String TEST_SET_FOLDER_NAME = "DemoTestSet";
	private static String TEST_SET_NAME = "RegressionTesting";

	public static void main(String[] args) {
		TestFolderEntityService testFolderService;
		TestEntityService testService;
		TestSetFolderEntityService testSetFolderService;
		TestSetEntityService testSetService;
		TestInstanceEntityService testInstanceService;
		String PARENT_ALM_TEST_FOLDER_ID = null;
		String PARENT_ALM_TEST_FOLDER_NAME = null;
		String PARENT_ALM_TESTSET_FOLDER_ID = null;
		String PARENT_ALM_TESTSET_FOLDER_NAME = null;

		String TEST_ID = null;
		String TEST_SET_ID = null;

		ListOfEntitiesBean allEntities = null;
		EntityBean entity = null;

		testFolderService = new TestFolderEntityService(Constants.BASE_URL,
				Constants.DOMAIN, Constants.PROJECT, Constants.USERNAME,
				Constants.PASSWORD);
		testService = new TestEntityService(Constants.BASE_URL,
				Constants.DOMAIN, Constants.PROJECT, Constants.USERNAME,
				Constants.PASSWORD);
		testSetFolderService = new TestSetFolderEntityService(
				Constants.BASE_URL, Constants.DOMAIN, Constants.PROJECT,
				Constants.USERNAME, Constants.PASSWORD);
		testSetService = new TestSetEntityService(Constants.BASE_URL,
				Constants.DOMAIN, Constants.PROJECT, Constants.USERNAME,
				Constants.PASSWORD);
		testInstanceService = new TestInstanceEntityService(Constants.BASE_URL,
				Constants.DOMAIN, Constants.PROJECT, Constants.USERNAME,
				Constants.PASSWORD);

		String testFolderPath = Constants.TEST_FOLDER_PATH;
		List<String> allDefaultTestFolders = new ArrayList<String>();

		allDefaultTestFolders = Arrays.asList(testFolderPath.split("\\."));
		PARENT_ALM_TEST_FOLDER_NAME = Constants.ROOT_ALM_TEST_FOLDER_NAME;
		allEntities = testFolderService.getListOfEntities("test-folders",
				PARENT_ALM_TEST_FOLDER_ID);
		PARENT_ALM_TEST_FOLDER_ID = EntityBeanUtils.getEntityIdWithName(
				allEntities, PARENT_ALM_TEST_FOLDER_NAME);
		for (String defaultFolder : allDefaultTestFolders) {
			allEntities = testFolderService.getListOfEntities("test-folders",
					PARENT_ALM_TEST_FOLDER_ID);
			String defaultTestFolderId = null;
			if (!(null == allEntities)) {
				defaultTestFolderId = EntityBeanUtils.getEntityIdWithName(
						allEntities, defaultFolder);
			}
			if ((null == defaultTestFolderId)) {
				entity = testFolderService.createEntity("test-folders",
						defaultFolder, PARENT_ALM_TEST_FOLDER_ID, "", "");
				if (!(null == entity)) {
					PARENT_ALM_TEST_FOLDER_ID = EntityBeanUtils
							.getEntityId(entity);
				}
			} else {
				PARENT_ALM_TEST_FOLDER_ID = defaultTestFolderId;
			}
		}

		entity = testService.createEntity("tests", TEST_CASE_NAME,
				PARENT_ALM_TEST_FOLDER_ID, null, null);

		TEST_ID = EntityBeanUtils.getEntityId(entity);
		// ***********************************************************************************//

		entity = testSetFolderService.createEntity("test-set-folders",
				TEST_SET_FOLDER_NAME, "", "", "");

		PARENT_ALM_TESTSET_FOLDER_ID = EntityBeanUtils.getEntityId(entity);

		entity = testService.createEntity("test-sets", TEST_SET_NAME,
				PARENT_ALM_TESTSET_FOLDER_ID, null, null);

		TEST_SET_ID = EntityBeanUtils.getEntityId(entity);

		// Retrieve the QC Authentication Cookie value
		/*
		 * authenticationCookie = authentication.doAuthentication(restClient,
		 * Constants.USERNAME, Constants.PASSWORD);
		 * 
		 * // Retrieve the QC Session Cookie value SiteSession siteSession = new
		 * SiteSession(Constants.BASE_URL, Constants.DOMAIN, Constants.PROJECT);
		 * String qcSessionCookie =
		 * siteSession.getQCSession(authenticationCookie);
		 * 
		 * // Get all the Test Folders under Test Plan section AbstractEntity
		 * testFoldersEntity = new TestFoldersEntity(Constants.BASE_URL,
		 * Constants.DOMAIN, Constants.PROJECT, Constants.ENVIRONMENT);
		 * responseBeans = testFoldersEntity.getListOfEntities(restClient,
		 * authenticationCookie, qcSessionCookie);
		 * logger.debug("Successfully retrieved Test Folders under Test Plan  - "
		 * , responseBeans.toString());
		 * 
		 * // Get the Test Folder with the given name PARENT_TESTFOLDER_ID =
		 * EntityBeanUtils.getEntityIdWithName(responseBeans,
		 * myRootTestFolderName);
		 * 
		 * // Create a new Test Folder under Test Plan section responseBean =
		 * testFoldersEntity.createEntity(restClient, authenticationCookie,
		 * qcSessionCookie, PARENT_TESTFOLDER_ID, null);
		 * logger.debug("Successfully Created Test Folder: ",
		 * responseBean.toString()); TESTFOLDER_ID =
		 * EntityBeanUtils.getEntityId(responseBean);
		 * 
		 * // Create a new Test Case under Test Folder AbstractEntity
		 * testsEntity = new TestsEntity(Constants.BASE_URL, Constants.DOMAIN,
		 * Constants.PROJECT, Constants.ENVIRONMENT); responseBean =
		 * testsEntity.createEntity(restClient, authenticationCookie,
		 * qcSessionCookie, TESTFOLDER_ID, null);
		 * logger.debug("Successfully Created Test Case under Test Folder '" +
		 * TESTFOLDER_ID + "' - ", responseBean.toString());
		 * 
		 * // Get all the Tests(Test Cases) under a Test Folder responseBeans =
		 * testsEntity.getTestsByFolderId(restClient, authenticationCookie,
		 * qcSessionCookie, TESTFOLDER_ID);
		 * logger.debug("Successfully retrieved Tests under the folder '" +
		 * TESTFOLDER_ID + "' - ", responseBeans.toString()); testIds =
		 * EntityBeanUtils.getEntityIds(responseBeans);
		 * 
		 * // Create a new TestSet Folder under Test Lab section AbstractEntity
		 * testSetFoldersEntity = new TestSetFoldersEntity(Constants.BASE_URL,
		 * Constants.DOMAIN, Constants.PROJECT, Constants.ENVIRONMENT);
		 * responseBean = testSetFoldersEntity.createEntity(restClient,
		 * authenticationCookie, qcSessionCookie, null, null);
		 * logger.debug("Successfully Created Test Set Folder: ",
		 * responseBean.toString()); TESTSETFOLDER_ID =
		 * EntityBeanUtils.getEntityId(responseBean);
		 * 
		 * // Create a new TestSet under TestSet Folder AbstractEntity
		 * testSetEntity = new TestSetsEntity(Constants.BASE_URL,
		 * Constants.DOMAIN, Constants.PROJECT, Constants.ENVIRONMENT);
		 * responseBean = testSetEntity.createEntity(restClient,
		 * authenticationCookie, qcSessionCookie, TESTSETFOLDER_ID, null);
		 * logger.debug("Successfully Created Test Set: ",
		 * responseBean.toString()); TESTSET_ID =
		 * EntityBeanUtils.getEntityId(responseBean);
		 * 
		 * // Adding Test instances to a Test Set TestInstancesEntity
		 * testInstancesEntity = new TestInstancesEntity(Constants.BASE_URL,
		 * Constants.DOMAIN, Constants.PROJECT, Constants.ENVIRONMENT); for
		 * (String testId : testIds) { responseBean =
		 * testInstancesEntity.createEntity(restClient, authenticationCookie,
		 * qcSessionCookie, TESTSET_ID, testId);
		 * logger.debug("Successfully Created Test Instance: ",
		 * responseBean.toString()); }
		 * 
		 * // Updating Test Run status responseBeans =
		 * testInstancesEntity.getTestInstancesByTestSetId(restClient,
		 * authenticationCookie, qcSessionCookie, TESTSET_ID);
		 * logger.debug("Successfully retrieved Test Instances for Test Set '" +
		 * TESTSET_ID + "' - ", responseBeans.toString()); List<EntityBean>
		 * testInstances = responseBeans.getListOfEntity(); List<String>
		 * testInstanceIds = new ArrayList<String>(); for (EntityBean entity :
		 * testInstances) { Fields fields = entity.getFields(); List<Field>
		 * listOfFields = fields.getListOfFields(); for (Field field :
		 * listOfFields) { if (field.getName().equals("id")) {
		 * testInstanceIds.add(field.getListOfValues().get(0)); } } } for
		 * (String testInstanceId : testInstanceIds) {
		 * testInstancesEntity.updateEntity(restClient, authenticationCookie,
		 * qcSessionCookie, testInstanceId);
		 * logger.debug("Successfully updated Test status", ""); }
		 * 
		 * // Creating a new Defect AbstractEntity defectsEntity = new
		 * DefectsEntity(Constants.BASE_URL, Constants.DOMAIN,
		 * Constants.PROJECT, Constants.ENVIRONMENT); responseBean =
		 * defectsEntity.createEntity(restClient, authenticationCookie,
		 * qcSessionCookie, null, null); DEFECT_ID =
		 * EntityBeanUtils.getEntityId(responseBean);
		 * logger.debug("Successfully created a new Defect with Id '" +
		 * DEFECT_ID, "");
		 */
	}
}
