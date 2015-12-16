package com.vzt.framework.hpqc;

import javax.ws.rs.client.Client;

import com.vzt.framework.hpqc.authentication.Authentication;
import com.vzt.framework.hpqc.session.SiteSession;
import com.vzt.framework.hpqc.utils.Constants;

public class ALMInitializer {

	protected String myBaseURL;

	protected String myDomain;

	protected String myProject;

	protected String myEnv;

	protected String myUserName;

	protected String myPassword;

	protected Client myRestClient;

	protected String myAuthenticationCookie;

	protected String myQcSessionCookie;

	private Authentication authentication;

	private SiteSession siteSession;

	public ALMInitializer() {

	}

	public ALMInitializer(final String aBaseURL, final String aDomain,
			final String aProject, final String aUserName, String aPassword,
			final String anEnv) {
		this.myBaseURL = aBaseURL;
		this.myDomain = aDomain;
		this.myProject = aProject;
		this.myUserName = aUserName;
		if (aPassword == null) {
			aPassword = "";
		}
		this.myPassword = aPassword;
		this.myEnv = anEnv;

		myRestClient = InitializeRestClient.doCreate();
		doAuthenticate();
	}

	public ALMInitializer(final String aBaseURL, final String aDomain,
			final String aProject, final String aUserName, String aPassword) {
		this.myBaseURL = aBaseURL;
		this.myDomain = aDomain;
		this.myProject = aProject;
		this.myUserName = aUserName;
		if (aPassword == null) {
			aPassword = "";
		}
		this.myPassword = aPassword;

		myRestClient = InitializeRestClient.doCreate();
		doAuthenticate();
	}

	/**
	     * 
	     */
	private void doAuthenticate() {
		// Retrieve the QC Authentication Cookie value
		if (myBaseURL == null || myUserName == null) {
			authentication = new Authentication(Constants.BASE_URL);
			myAuthenticationCookie = authentication.doAuthentication(
					myRestClient, Constants.USERNAME, Constants.PASSWORD);
		} else {
			authentication = new Authentication(myBaseURL);
			myAuthenticationCookie = authentication.doAuthentication(
					myRestClient, myUserName, myPassword);
		}

		// Retrieve the QC Session Cookie value
		if (!(myBaseURL == null || myDomain == null || myProject == null)) {
			siteSession = new SiteSession(myBaseURL, myDomain, myProject);
		} else {
			siteSession = new SiteSession(Constants.BASE_URL, Constants.DOMAIN,
					Constants.PROJECT);
		}

		myQcSessionCookie = siteSession.getQCSession(myAuthenticationCookie);

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
	public void setBaseURL(final String aBaseURL) {
		this.myBaseURL = aBaseURL;
	}

	/**
	 * @return the myEnv
	 */
	public String getEnv() {
		return myEnv;
	}

	/**
	 * @param myEnv
	 *            the myEnv to set
	 */
	public void setEnv(final String aEnv) {
		this.myEnv = aEnv;
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
	public void setDomain(String myDomain) {
		this.myDomain = myDomain;
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
	public void setProject(String myProject) {
		this.myProject = myProject;
	}

	@Override
	public String toString() {
		return "BaseURL=" + this.myBaseURL + ", Domain=" + this.myDomain
				+ ", Project=" + this.myProject + "]";
	}

	/**
	 * @return the myUserName
	 */
	public String getUserName() {
		return myUserName;
	}

	/**
	 * @param myUserName
	 *            the myUserName to set
	 */
	public void setUserName(String myUserName) {
		this.myUserName = myUserName;
	}

	/**
	 * @return the myPassword
	 */
	public String getPassword() {
		return myPassword;
	}

	/**
	 * @param myPassword
	 *            the myPassword to set
	 */
	public void setPassword(String myPassword) {
		this.myPassword = myPassword;
	}

	/**
	 * @return the myRestClient
	 */
	public Client getRestClient() {
		return myRestClient;
	}

	/**
	 * @param myRestClient
	 *            the myRestClient to set
	 */
	public void setRestClient(Client myRestClient) {
		this.myRestClient = myRestClient;
	}

	/**
	 * @return the myAuthenticationCookie
	 */
	public String getAuthenticationCookie() {
		return myAuthenticationCookie;
	}

	/**
	 * @param myAuthenticationCookie
	 *            the myAuthenticationCookie to set
	 */
	public void setAuthenticationCookie(String myAuthenticationCookie) {
		this.myAuthenticationCookie = myAuthenticationCookie;
	}

	/**
	 * @return the myQcSessionCookie
	 */
	public String getQcSessionCookie() {
		return myQcSessionCookie;
	}

	/**
	 * @param myQcSessionCookie
	 *            the myQcSessionCookie to set
	 */
	public void setQcSessionCookie(String myQcSessionCookie) {
		this.myQcSessionCookie = myQcSessionCookie;
	}

}
