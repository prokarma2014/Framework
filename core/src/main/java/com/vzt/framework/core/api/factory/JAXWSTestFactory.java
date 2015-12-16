
package com.vzt.framework.core.api.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.vzt.framework.core.api.JAXWSRequestTestData;
import com.vzt.framework.core.api.RestRequestTestData;



/**
 * Main class for initializing request parameter, Callig the REST Services 
 * and for validating the Response of the service calls..
 * 
 * @author prokarma
 * @verion 1.0
 * 
 */


public class JAXWSTestFactory {
	
	
	private String tcid;
	private String api_name;
	private String auth_Service;
	private String run_Mode;
	private String baseURL;
	private String api;	
	private String contentType;	
	private String header_Key;	
	private String header_Value;	
	private String expected_Response_Code;	
	private String response;
	private String envelope;
	
	
	private static Logger logger = LoggerFactory.getLogger(JAXWSTestFactory.class);

	/**
	 * Initialize all the required parameters for a web service call.
	 * 
	 * @param rowData
	 * @throws IOException 
	 */
	public void initialize(JAXWSRequestTestData  testData) throws IOException {
		
		
		tcid = (testData.getTcid()!= null)?testData.getTcid():"";
		api_name = (testData.getApi_Name()!= null)?testData.getApi_Name():"";
		auth_Service = (testData.getAuth_Service()!= null)?testData.getAuth_Service():"";
		run_Mode = (testData.getRun_Mode()!= null)?testData.getRun_Mode():"";
		baseURL = (testData.getBaseURL()!= null)?testData.getBaseURL():"";
		api = (testData.getApi()!= null)?testData.getApi():"";	
		contentType = (testData.getContentType()!= null)?testData.getContentType():"";	
		header_Key	= (testData.getHeader_Key()!= null)?testData.getHeader_Key():"";
		header_Value = (testData.getHeader_Value()!= null)?testData.getHeader_Value():"";	
		expected_Response_Code = (testData.getExpected_Response_Code()!= null)?testData.getExpected_Response_Code():"";
		response = (testData.getRespone()!= null)?testData.getRespone():"";
		envelope = (testData.getSoapEnvelope()!= null)?testData.getSoapEnvelope():"";
	}


	/**
	 * Call the web service and get the response.
	 * 
	 * @return
	 */
	public Response getResponse() {
		
		Response outputResponse  = null;
		
		try {
			logger.info(" Inside getResponse ()");
			
			Map<String, String> authhdrs = new HashMap<String, String>();
			authhdrs.put(header_Key,header_Value);
			  
			outputResponse  =
					RestAssured
				         .given()
				         .request()
				         .headers(authhdrs)
				         .contentType(contentType)
				         .body(envelope)
				         .when()				         
				         .post(baseURL+api)
				         .andReturn();

		} catch (Exception exception) {
			
			logger.info(exception.getMessage(), exception);

		}
		return outputResponse;
	}

	/**
	 * Check the HTTP response status code and validate the reponse. If 200 OK,
	 * assert the response with the values from DB If 400, 499, assert the error
	 * details in the response body with values in the ErrorCodes.properties
	 * file.
	 * 
	 * @param response
	 */
	public String validateResponse(Response response) {
		String retResponse = null;
		int actualResponseCode = response.getStatusCode();
		int expResponseCode = (int) Float.parseFloat(expected_Response_Code.trim());

		if (actualResponseCode == expResponseCode) {
			if (actualResponseCode == 200) {				
				retResponse = validateValidResponse(response);		

			} else if (actualResponseCode == 400 || actualResponseCode == 499 || actualResponseCode == 401) {
				retResponse = validateErrorResponse(response);
			} 
		} else {    
			logger.debug("Exp response: " + expResponseCode + " Act response: " + actualResponseCode);
		}
		
		return retResponse;
	}


	/**
	 * When HTTP Response Code 200, use this method to validate the response
	 * obtained against Test Data values.
	 * 
	 * @param responseBody
	 */
	private String validateValidResponse(Response response) {

		
		logger.debug(response.getBody().asString());
		logger.debug(response.body().asString());
		
		return response.body().asString();
	}


	/**
	 * When HTTP Response Code 400/499, use this method to validate the error
	 * response obtained against the values in properties file.
	 * 
	 * @param response
	 */
	private String validateErrorResponse(Response response) {
		
		logger.debug(response.body().asString());	
		return response.body().asString();
		
		
	}

}
