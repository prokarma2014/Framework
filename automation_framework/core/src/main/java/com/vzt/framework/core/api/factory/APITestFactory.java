
package com.vzt.framework.core.api.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import com.vzt.framework.core.api.RestRequestTestData;



/**
 * Main class for initializing request parameter, Callig the REST Services 
 * and for validating the Response of the service calls..
 * 
 * @author prokarma
 * @verion 1.0
 * 
 */


public class APITestFactory {
	
	private String baseUrl;
	private String requestURL;
	private String headerKey;
	private String headerValue;
	private String requestName;
	private String requestParam;
	private String expectedResponseCode;
	private String requestBody;
	private String contentType;
	private String requestMethod;
	private String responseKeySet;
	private String errorName;
	protected String tcid;
	protected String fileoutpath;
	protected String urlParameters;
	private String requestValues;
	private String fileUploadSrcPath;
	private String responseStoreRetr;
	private String loginUrl;
	
	private List<Map<String, String>> resultsList;

	private boolean isLoginRequired;
	private static Logger logger = LoggerFactory.getLogger(APITestFactory.class);

	/**
	 * Initialize all the required parameters for a web service call.
	 * 
	 * @param rowData
	 * @throws IOException 
	 */
	public void initialize(RestRequestTestData  rowData) throws IOException {
		
		
		baseUrl		         = (rowData.getBaseURL()!= null)?rowData.getBaseURL():"";
		requestURL           = baseUrl + rowData.getApi();
		headerKey            = (rowData.getHeader_Key()!= null)?rowData.getHeader_Key():"";
		headerValue          = (rowData.getHeader_Value()!= null)?rowData.getHeader_Value():"";
		requestName          = (rowData.getRequest_Name()!= null)?rowData.getRequest_Name():"";
		requestParam         = (rowData.getRequest_Parameters()!= null)?rowData.getRequest_Parameters():"";
		expectedResponseCode = (rowData.getExpected_Response_Code()!= null)?rowData.getExpected_Response_Code():"";
		requestMethod        = (rowData.getRequest_Method()!= null)?rowData.getRequest_Method():"";
		contentType          = (rowData.getContentType()!= null)?rowData.getContentType():"";
		requestBody          = generateValidRequestBody(requestName, requestParam);
		errorName            = (rowData.getError_Name()!= null)?rowData.getError_Name():"";
		responseKeySet       = (rowData.getResponse_Keys()!= null)?rowData.getResponse_Keys():"";
		urlParameters        = (rowData.getUrl_Parameters()!= null)?rowData.getUrl_Parameters():"";
		requestValues        = (rowData.getRequest_Values()!= null)?rowData.getRequest_Values():"";
		fileUploadSrcPath    = (rowData.getFile_Src_Path()!= null)?rowData.getFile_Src_Path():"";
		tcid 				 = rowData.getTcid().trim();
		
		if(rowData.getAuth_Service().trim().equalsIgnoreCase("YES")){
			isLoginRequired = true;
		}
		else{
			isLoginRequired = false;
		}
	}


	/**
	 * Call the web service and get the response.
	 * 
	 * @return
	 */
	public Response getResponse(SSLSocketFactory sslSocketFactory) {
		
		
		final SSLConfig config = RestAssured
                .config()
                .getSSLConfig()
                .sslSocketFactory(sslSocketFactory);

     	
		Response response = null;
		
		try {
			logger.info("Final request url: " + requestURL);
			Map<String, String> cookies = new HashMap<String, String>();

			//When the header data needs to be sent as cookie, hit the login url, get the 
			//cookies and pass it in the actual request on hitting the request endpoint.
			if(isLoginRequired) {
				Response cookieResp = RestAssured.given().redirects()
						.follow(false).get(loginUrl);
				cookies = cookieResp.cookies();
				RestAssured.given().redirects().follow(false)
				.cookies(cookies).get(baseUrl);
			}
			

			if (requestMethod.equalsIgnoreCase("POST")) {
				// Call POST service
			
				response = RestAssured.given().config(new RestAssuredConfig().sslConfig(config)).header(headerKey, headerValue)
						.contentType(contentType).body(requestBody).when()
						.post(requestURL).andReturn();
			} /*else if (requestMethod.equalsIgnoreCase("FILEPOST")) {
				HashMap<String, String> formParams;
				formParams = getFormParams(requestParam);
				if(formParams != null) {
					response = RestAssured.given().cookies(cookies).headers(headerKey, headerValue)
							.multiPart(new File(fileUploadSrcPath))
							.formParams(formParams).post(requestURL);
				}
			} */else if (requestMethod.equalsIgnoreCase("DELETE")) {
				response = RestAssured.given().cookies(cookies).headers(headerKey, headerValue)
						.delete(requestURL).andReturn();

			} else if (requestMethod.equalsIgnoreCase("GET")) {

				if(headerKey.isEmpty()){
					logger.info("No header key present, "+headerKey);
					response = RestAssured.given().cookies(cookies)
							.contentType(contentType).get(requestURL)
							.andReturn();

				}else{
					// Call GET service
					response = RestAssured.given().cookies(cookies).headers(headerKey, headerValue)
							.contentType(contentType).get(requestURL).andReturn();
				}
			}
			

		} catch (Exception exception) {
			
			logger.info(exception.getMessage(), exception);

		}
		return response;
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

		if(response!=null){
		
		
		int actualResponseCode = response.getStatusCode();
		int expResponseCode = (int) Float.parseFloat(expectedResponseCode.trim());

		if (actualResponseCode == expResponseCode) {
			if (actualResponseCode == 200) {				
				retResponse = validateValidResponse(response);		

			} else if (actualResponseCode == 400 || actualResponseCode == 499 || actualResponseCode == 401) {
				retResponse = validateErrorResponse(response);
			} 
		} else {    
			logger.debug("Exp response: " + expResponseCode + " Act response: " + actualResponseCode);
		
			retResponse = Integer.toString(actualResponseCode);
		}
		}
		else{
			logger.error("No Response");
			retResponse = "No response";
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

	/**
	 * Generates a valid json request from the json obatained from the
	 * properties file. Uses the request parameters provided in the Excel sheet
	 * to form a valid Json Request body.
	 * 
	 * @param requestName
	 * @param requestParam
	 * @return
	 */
	private String generateValidRequestBody(String requestName,
			String requestParam) {
		
		if (requestMethod.equalsIgnoreCase("POST")) {
			
        /*	String arrData[] = requestParam.split(",");
			JSONObject jsonObj = new JSONObject();
			for (int i = 0; i < arrData.length; i++) {
				jsonObj.put(arrData[i].substring(0, arrData[i].indexOf(":")),
						(arrData[i].substring(arrData[i].indexOf(":") + 1,
								arrData[i].length())));
			}			
		*/				
			return requestParam.toString();
		}
		return null;
	}
}
