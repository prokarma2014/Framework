package com.vzt.framework.hpqc.utils;

public class Constants {

	private Constants() {

	}

	private static PropertyUtils myPropertyUtils = PropertyUtils
			.getInstance("restclient.properties");

	public static final String CONNECTION_TIMEOUT = myPropertyUtils
			.getProperty("connection_timeout");
	public static final String READ_TIMEOUT = myPropertyUtils
			.getProperty("read_timeout");
	public static final String BASE_URL = myPropertyUtils
			.getProperty("baseUrl");
	public static final String DOMAIN = myPropertyUtils.getProperty("domain");
	public static final String PROJECT = myPropertyUtils.getProperty("project");
	public static final String USERNAME = myPropertyUtils
			.getProperty("username");
	public static final String PASSWORD = myPropertyUtils
			.getProperty("password");
	public static final String ENVIRONMENT = myPropertyUtils
			.getProperty("environment");

	public static final String IS_USER_AUTHENTICATED_URL = myPropertyUtils
			.getProperty("isUserAuthenticatedURL");
	public static final String USER_AUTHENTICATION_URL = myPropertyUtils
			.getProperty("userAuthenticationURL");
	public static final String SITE_SESSION_URL = myPropertyUtils
			.getProperty("siteSessionURL");
	public static final String USER_LOGOUT_URL = myPropertyUtils
			.getProperty("logoutURL");
	public static final String DEFECT_URL = myPropertyUtils
			.getProperty("getDefectByIdURL");
	public static final String TEST_FOLDER_URL = myPropertyUtils
			.getProperty("testFolderURL");
	public static final String TESTS_URL = myPropertyUtils
			.getProperty("testsURL");
	public static final String TEST_CONFIG_URL = myPropertyUtils
			.getProperty("testConfigsURL");
	public static final String TEST_SET_FOLDER_URL = myPropertyUtils
			.getProperty("testSetFolderURL");
	public static final String TEST_SET_URL = myPropertyUtils
			.getProperty("testSetURL");
	public static final String TEST_INSTANCES_URL = myPropertyUtils
			.getProperty("testInstancesURL");
	public static final String TEST_RUN_URL = myPropertyUtils
			.getProperty("testRunURL");

	public static final String TEST_FOLDER_PATH = myPropertyUtils
			.getProperty("testFolderPath");
	public static final String TEST_SET_FOLDER_PATH = myPropertyUtils
			.getProperty("testSetFolderPath");
	public static final String ROOT_ALM_TEST_FOLDER_NAME = myPropertyUtils
			.getProperty("rootALMTestFolderName");
	public static final String ROOT_ALM_TESTSET_FOLDER_NAME = myPropertyUtils
			.getProperty("rootALMTestSetFolderName");
	
	
	public static final int UNAUTHORIZED = 401;
	public static final int GET_SUCCESS = 200;
	public static final int POST_SUCCESS = 201;
	public static final String TEST_SUCCESS = "Passed";
	public static final String TEST_FAILED = "Failed";
	public static final String NOT_RUN = "No Run";


}
