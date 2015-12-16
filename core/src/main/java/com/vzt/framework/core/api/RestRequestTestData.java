package com.vzt.framework.core.api;

import com.vzt.framework.core.annotations.Data;

public class RestRequestTestData {
	
	@Data(name="BaseURL")
	private String baseURL;
	
	
	@Data(name="TCID")
	private String tcid;
	
	@Data(name="Api_Name")
	private String api_Name;
	
	@Data(name = "Auth_Service")
	private String auth_Service;
	
	@Data(name="Run_Mode")
	private String run_Mode;
	
	@Data(name="API")
	private String api;
	
	@Data(name="Request_Method")
	private String request_Method;
	
	@Data(name="Header_Key")
	private String header_Key;
	
	@Data(name="Header_Value")
	private String header_Value;
	
	@Data(name="Url_Parameters")
	private String url_Parameters;
	
	@Data(name="Request_Name")
	private String request_Name;
	
	@Data(name="Request_Parameters")
	private String request_Parameters;
	
	@Data(name="Expected_Response_Code")
	private String expected_Response_Code;
	
	@Data(name="Error_Name")
	private String error_Name;
	
	@Data(name="Response_Keys")
	private String response_Keys;
	
	@Data(name="File_Src_Path")
	private String file_Src_Path;	
	
	@Data(name="Response")
	private String response;
	
	@Data(name="Request_Values")
	private String request_Values;
	
	@Data(name="Test_Result")
	private String test_Result;
	
	@Data(name="Failure_Cause")
	private String failure_Cause;
	
	@Data(name="ContentType")
	private String contentType;
	
	

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return the baseURL
	 */
	public String getBaseURL() {
		return baseURL;
	}

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
	 * @return the api
	 */
	public String getApi() {
		return api;
	}

	/**
	 * @return the request_Method
	 */
	public String getRequest_Method() {
		return request_Method;
	}

	/**
	 * @return the header_Key
	 */
	public String getHeader_Key() {
		return header_Key;
	}

	/**
	 * @return the header_Value
	 */
	public String getHeader_Value() {
		return header_Value;
	}

	/**
	 * @return the url_Parameters
	 */
	public String getUrl_Parameters() {
		return url_Parameters;
	}

	/**
	 * @return the request_Name
	 */
	public String getRequest_Name() {
		return request_Name;
	}

	/**
	 * @return the request_Parameters
	 */
	public String getRequest_Parameters() {
		return request_Parameters;
	}

	/**
	 * @return the expected_Response_Code
	 */
	public String getExpected_Response_Code() {
		return expected_Response_Code;
	}

	/**
	 * @return the error_Name
	 */
	public String getError_Name() {
		return error_Name;
	}

	/**
	 * @return the response_Keys
	 */
	public String getResponse_Keys() {
		return response_Keys;
	}

	/**
	 * @return the file_Src_Path
	 */
	public String getFile_Src_Path() {
		return file_Src_Path;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @return the request_Values
	 */
	public String getRequest_Values() {
		return request_Values;
	}

	/**
	 * @return the test_Result
	 */
	public String getTest_Result() {
		return test_Result;
	}

	/**
	 * @return the failure_Cause
	 */
	public String getFailure_Cause() {
		return failure_Cause;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RestRequestTestData [baseURL=" + baseURL + ", tcid=" + tcid
				+ ", api_Name=" + api_Name + ", auth_Service=" + auth_Service
				+ ", run_Mode=" + run_Mode + ", api=" + api
				+ ", request_Method=" + request_Method + ", header_Key="
				+ header_Key + ", header_Value=" + header_Value
				+ ", url_Parameters=" + url_Parameters + ", request_Name="
				+ request_Name + ", request_Parameters=" + request_Parameters
				+ ", expected_Response_Code=" + expected_Response_Code
				+ ", error_Name=" + error_Name + ", response_Keys="
				+ response_Keys + ", file_Src_Path=" + file_Src_Path
				+ ", response=" + response + ", request_Values="
				+ request_Values + ", test_Result=" + test_Result
				+ ", failure_Cause=" + failure_Cause + ", getBaseURL()="
				+ getBaseURL() + ", getTcid()=" + getTcid()
				+ ", getApi_Name()=" + getApi_Name() + ", getAuth_Service()="
				+ getAuth_Service() + ", getRun_Mode()=" + getRun_Mode()
				+ ", getApi()=" + getApi() + ", getRequest_Method()="
				+ getRequest_Method() + ", getHeader_Key()=" + getHeader_Key()
				+ ", getHeader_Value()=" + getHeader_Value()
				+ ", getUrl_Parameters()=" + getUrl_Parameters()
				+ ", getRequest_Name()=" + getRequest_Name()
				+ ", getRequest_Parameters()=" + getRequest_Parameters()
				+ ", getExpected_Response_Code()="
				+ getExpected_Response_Code() + ", getError_Name()="
				+ getError_Name() + ", getResponse_Keys()="
				+ getResponse_Keys() + ", getFile_Src_Path()="
				+ getFile_Src_Path() + ", getResponse()=" + getResponse()
				+ ", getRequest_Values()=" + getRequest_Values()
				+ ", getTest_Result()=" + getTest_Result()
				+ ", getFailure_Cause()=" + getFailure_Cause()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	

	
	
	

}
