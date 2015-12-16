package com.vzt.framework.hpqc.session;

import java.util.HashMap;
import java.util.Map;

import com.vzt.framework.hpqc.Response;
import com.vzt.framework.hpqc.RestConnector;
import com.vzt.framework.hpqc.utils.Constants;

public class SiteSession {

	private String myBaseURL;

	private String myDomain;

	private String myProject;

	public SiteSession() {

	}

	/**
	 * @param myproject
	 * @param string
	 */
	public SiteSession(final String aBaseURL, final String aDomain, String aproject) {

		this.myBaseURL = aBaseURL;
		this.myDomain = aDomain;
		this.myProject = aproject;

	}

	// public String getQCSessionCookie(final Client aRestClient, final String
	// anAuthenticationCookie) {
	//
	// String targetURL = null;
	//
	// if (("").equals(myBaseURL)) {
	//
	// targetURL = Constants.BASE_URL + Constants.SITE_SESSION_URL;
	// } else {
	//
	// targetURL = myBaseURL + Constants.SITE_SESSION_URL;
	//
	// }
	//
	// WebTarget qcSessionWebTarget = null;
	// String qcSessionResponse = null;
	//
	// SessionParameters sessionParameters =
	// createEntityRequest("session-parameters");
	// try {
	// qcSessionWebTarget = aRestClient.target(targetURL);
	// Invocation.Builder qcSessionInvocationBuilder =
	// qcSessionWebTarget.request()
	// .header("Cookie", anAuthenticationCookie).header("Content-Type",
	// "*/*").header("Accept", "*/*");
	//
	// qcSessionResponse = qcSessionInvocationBuilder
	// .post(null, String.class);
	//
	// UPLogger.i("TestSet Folder created successfully", qcSessionResponse);
	// }catch (Exception ex) {
	// UPLogger.e("Exception {}", ex.getMessage());
	// }
	//
	// return qcSessionResponse;
	//
	// }

	/**
	 * @param authenticationCookie
	 * @param string
	 * @return
	 * @return
	 */
	// private SessionParameters createEntityRequest(String entityType) {
	//
	// SessionParameters sessionParameters = new SessionParameters();
	// String keyName = null;
	// int fieldsCount = 1;
	//
	// for (int count = 0; count < fieldsCount; count++) {
	// // Field field = new Field();
	// //
	// List<String> allValues = new ArrayList<String>();
	//
	// Map<String, String> fieldMap =
	// InputDataLoader.getRequiredFieldsMap(count, entityType);
	//
	// Iterator<Entry<String, String>> itr = fieldMap.entrySet().iterator();
	// while (itr.hasNext()) {
	// Entry<String, String> entry = itr.next();
	// keyName = entry.getKey();
	// sessionParameters.setClientType(entry.getValue());
	// }

	// field.setName(keyName);
	// field.setValue(allValues);
	//
	// allFields.add(field);
	// }

	// fields.setListOfFields(allFields);

	// newDefectEntity.setFields(fields);
	// newDefectEntity.setType(entityType);

	// return sessionParameters;

	// }

	public String getQCSession(String authenticationCookie) {
		String qcSessionCookie = null;
		String targetURL = null;

		if (("").equals(myBaseURL)) {

			targetURL = Constants.BASE_URL + Constants.SITE_SESSION_URL;
		} else {

			targetURL = myBaseURL + Constants.SITE_SESSION_URL;

		}

		RestConnector restConnector = RestConnector.getInstance();
		// String qcsessionurl = restConnector.buildUrl(targetURL);
		Map<String, String> requestHeaders = new HashMap<String, String>();
		Map<String, String> cookies = new HashMap<String, String>();
		// cookies.put("Cookie", authenticationCookie);

		requestHeaders.put("Cookie", authenticationCookie);
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		try {
			restConnector.init(cookies, targetURL);
			Response resp = restConnector.httpPost(targetURL, null, requestHeaders);

			// Map<String, ? extends Iterable<String>> headers =
			// resp.getResponseHeaders();

			qcSessionCookie = cookies.get("QCSession");
			// Iterator<Entry<String, List<Object>>> iterator =
			// headers.entrySet().iterator();
			// while (iterator.hasNext()) {
			// Entry<String, List<Object>> entry = iterator.next();
			// UPLogger.i(entry.getKey(), entry.getValue().toArray());
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "QCSession=" + qcSessionCookie;
	}

	/**
	 * @return the myBaseURL
	 */
	public String getBaseURL() {
		return myBaseURL;
	}

	/**
	 * @param myBaseURL
	 *            the myBaseURL to set
	 */
	public void setBaseURL(String aBaseURL) {
		this.myBaseURL = aBaseURL;
	}

	/**
	 * @return the myDomain
	 */
	public String getDomain() {
		return myDomain;
	}

	/**
	 * @param myDomain
	 *            the myDomain to set
	 */
	public void setDomain(String aDomain) {
		this.myDomain = aDomain;
	}

	/**
	 * @return the myProject
	 */
	public String getProject() {
		return myProject;
	}

	/**
	 * @param myProject
	 *            the myProject to set
	 */
	public void setProject(String aProject) {
		this.myProject = aProject;
	}

}
