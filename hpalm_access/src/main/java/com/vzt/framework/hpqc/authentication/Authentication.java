package com.vzt.framework.hpqc.authentication;

/**
 * author Cassian Raja
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import sun.misc.BASE64Encoder;

import com.vzt.framework.hpqc.logger.VzTLogger;
import com.vzt.framework.hpqc.utils.Constants;

public class Authentication {

	private String myBaseURL;

	private String myEnv;

	public Authentication() {

	}

	public Authentication(final String aBaseURL) {
		this.myBaseURL = aBaseURL;
		// this.myEnv = anEnv;
	}

	public boolean isAuthenticated(final Client aRestClient) {

		boolean isAuthenticated = false;
		String targetURL = null;

		if (("").equals(myBaseURL)) {

			targetURL = Constants.BASE_URL
					+ Constants.IS_USER_AUTHENTICATED_URL;
		} else {
			targetURL = myBaseURL + Constants.IS_USER_AUTHENTICATED_URL;
		}

		WebTarget isAuthenticatedWebTarget = null;

		try {
			isAuthenticatedWebTarget = aRestClient.target(targetURL);
			Invocation.Builder isAuthenticatedInvocationBuilder = isAuthenticatedWebTarget
					.request().header("Content-Type", "application/xml")
					.accept(MediaType.APPLICATION_XML);
			Response isAuthenticatedResponse = isAuthenticatedInvocationBuilder
					.get();

			isAuthenticated = Boolean.parseBoolean(String
					.valueOf(isAuthenticatedResponse.getStatus()));

			VzTLogger.i("Response of Is_Authenticated: ",
					String.valueOf(isAuthenticatedResponse.getStatus()));

			if (isAuthenticatedResponse.getStatus() == Constants.UNAUTHORIZED) {

				MultivaluedMap<String, Object> headers = isAuthenticatedResponse
						.getHeaders();
				Iterator<Entry<String, List<Object>>> iterator = headers
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, List<Object>> entry = iterator.next();
					VzTLogger.i(entry.getKey(), entry.getValue().toArray());
				}
			}
		} catch (Exception ex) {
			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return isAuthenticated;

	}

	public String doAuthentication(final Client aRestClient,
			final String aUserName, final String aPassword) {

		Map<String, String> encodedLoginString = new HashMap<String, String>();

		String cookie = null;
		String targetURL = Constants.BASE_URL
				+ Constants.USER_AUTHENTICATION_URL;

		// if (("").equals(aUserName) || ("").equals(aPassword))
		// {
		// encodedLoginString = createEncodedLoginString(Constants.USERNAME,
		// Constants.PASSWORD);
		// }
		// else
		// {
		encodedLoginString = createEncodedLoginString(aUserName, aPassword);
		// }

		WebTarget getAuthenticationWebTarget = null;
		try {
			getAuthenticationWebTarget = aRestClient.target(targetURL);
			Invocation.Builder getAuthenticationInvocationBuilder = getAuthenticationWebTarget
					.request().header("Content-Type", "application/xml")
					.accept(MediaType.APPLICATION_XML);

			Iterator<Entry<String, String>> mapItr = encodedLoginString
					.entrySet().iterator();

			while (mapItr.hasNext()) {
				Entry<String, String> entry = mapItr.next();
				getAuthenticationInvocationBuilder.header(entry.getKey(),
						entry.getValue());
			}
			Response getAuthenticationResponse = getAuthenticationInvocationBuilder
					.get();

			VzTLogger.i("Response of User Authentication: ",
					String.valueOf(getAuthenticationResponse.getStatus()));

			if (getAuthenticationResponse.getStatus() == Constants.GET_SUCCESS) {
				MultivaluedMap<String, Object> headers = getAuthenticationResponse
						.getHeaders();
				cookie = (String) headers.get("Set-Cookie").get(0);
				Iterator<Entry<String, List<Object>>> iterator = headers
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, List<Object>> entry = iterator.next();
					VzTLogger.i(entry.getKey(), entry.getValue().toArray());
				}
			}

			VzTLogger.i("The Authentication Cookie: ", cookie);
		} catch (Exception ex) {
			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return cookie;

	}

	private static Map<String, String> createEncodedLoginString(
			final String username, final String passwd) {

		String authString = username + ":" + passwd;
		String authStringEnc = new BASE64Encoder()
				.encode(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);

		// Client restClient = Client.create();
		// WebResource webResource = restClient.resource(url);
		// ClientResponse resp = webResource.accept("application/json")
		// .header("Authorization", "Basic " + authStringEnc)
		// .get(ClientResponse.class);
		// byte[] encodedBytes = (username + ":" + passwd).getBytes();
		// String encodeStr = "Basic " + Base64.encode(encodedBytes);

		Map<String, String> encodedLoginString = new HashMap<String, String>();
		encodedLoginString.put("Authorization", "Basic " + authStringEnc);
		return encodedLoginString;
	}

	public static void doLogout(final Client restClient, final String cookie) {

		WebTarget logoutWebTarget = restClient.target(Constants.BASE_URL
				+ Constants.USER_LOGOUT_URL);

		Invocation.Builder logoutInvocationBuilder = logoutWebTarget.request()
				.accept("application/xml");

		Response logoutResponse = logoutInvocationBuilder.get();

		VzTLogger.i("Response of User Logout: ",
				String.valueOf(logoutResponse.getStatus()));

	}

	/**
	 * @return the myBaseURL
	 */
	public String getMyBaseURL() {
		return myBaseURL;
	}

	/**
	 * @param myBaseURL
	 *            the myBaseURL to set
	 */
	public void setMyBaseURL(final String aBaseURL) {
		this.myBaseURL = aBaseURL;
	}

	/**
	 * @return the myEnv
	 */
	public String getMyEnv() {
		return myEnv;
	}

	/**
	 * @param myEnv
	 *            the myEnv to set
	 */
	public void setMyEnv(final String aEnv) {
		this.myEnv = aEnv;
	}

}
