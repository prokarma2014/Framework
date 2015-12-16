package com.vzt.framework.core.api;

import com.vzt.framework.core.annotations.Data;

public class JAXWSRequestTestData {
	
	@Data(name="SoapEnvelope")
	private String soapEnvelope;
	
	@Data(name="TCID")
	private String tcid;
	
	@Data(name="Api_Name")
	private String api_Name;
	
	@Data(name="Auth_Service")
	private String auth_Service;
	
	@Data(name="Run_Mode")
	private String run_Mode;
	
	@Data(name="BaseURL")
	private String baseURL;
	
	@Data(name="API")
	private String api;
	
	@Data(name="ContentType")
	private String contentType;
	
	@Data(name="Header_Key")
	private String header_Key;
	
	@Data(name="Header_Value")
	private String header_Value;
	
	@Data(name="Expected_Response_Code")
	private String expected_Response_Code;
	
	@Data(name="Response")
	private String response;
	
	

	/**
	 * @return the tcid
	 */
	public String getTcid() {
		return tcid;
	}

	/**
	 * @return the api_Name
	 */
	public String getApi_Name() {
		return api_Name;
	}

	/**
	 * @return the auth_Service
	 */
	public String getAuth_Service() {
		return auth_Service;
	}

	/**
	 * @return the run_Mode
	 */
	public String getRun_Mode() {
		return run_Mode;
	}

	/**
	 * @return the baseURL
	 */
	public String getBaseURL() {
		return baseURL;
	}

	/**
	 * @return the api
	 */
	public String getApi() {
		return api;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return the header_Value
	 */
	public String getHeader_Value() {
		return header_Value;
	}

	/**
	 * @return the header_Key
	 */
	public String getHeader_Key() {
		return header_Key;
	}

	/**
	 * @return the expected_Response_Code
	 */
	public String getExpected_Response_Code() {
		return expected_Response_Code;
	}

	/**
	 * @return the response
	 */
	public String getRespone() {
		return response;
	}

	/**
	 * @return the envelope
	 */
	public String getSoapEnvelope() {
		return soapEnvelope;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JAXWSRequestTestData [envelope=" + soapEnvelope + ", tcid=" + tcid
				+ ", api_Name=" + api_Name + ", auth_Service=" + auth_Service
				+ ", run_Mode=" + run_Mode + ", baseURL=" + baseURL + ", api="
				+ api + ", contentType=" + contentType + ", header_Value="
				+ header_Value + ", header_Key=" + header_Key
				+ ", expected_Response_Code=" + expected_Response_Code
				+ ", respone=" + response + "]";
	}


	

}
