package com.vzt.framework.hpqc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.vzt.framework.hpqc.logger.VzTLogger;

public class URLBuilder {

	private URLBuilder() {

	}

	public static String createGetURL(final String aBaseURL,
			final String aDomain, final String aProject, final String anEntity) {

		StringBuilder urlBuilder = new StringBuilder(aBaseURL);
		urlBuilder.append("/");

		urlBuilder.append("rest");
		urlBuilder.append("/");

		urlBuilder.append("domains");
		urlBuilder.append("/");

		urlBuilder.append(aDomain);
		urlBuilder.append("/");

		urlBuilder.append("projects");
		urlBuilder.append("/");

		urlBuilder.append(aProject);
		urlBuilder.append("/");

		urlBuilder.append(anEntity);

		return urlBuilder.toString();
	}

	public static String createGetURLWithId(final String aBaseURL,
			final String aDomain, final String aProject, final String anEntity,
			final String entityId) {

		StringBuilder urlBuilder = new StringBuilder(aBaseURL);
		urlBuilder.append("/");

		urlBuilder.append("rest");
		urlBuilder.append("/");

		urlBuilder.append("domains");
		urlBuilder.append("/");

		urlBuilder.append(aDomain);
		urlBuilder.append("/");

		urlBuilder.append("projects");
		urlBuilder.append("/");

		urlBuilder.append(aProject);
		urlBuilder.append("/");

		urlBuilder.append(anEntity);
		urlBuilder.append("/");

		urlBuilder.append(entityId);

		return urlBuilder.toString();
	}

	public static String createGetURLWithQuery(final String aBaseURL,
			final String aDomain, final String aProject, final String anEntity,
			final String queryString) {

		StringBuilder urlBuilder = new StringBuilder(aBaseURL);
		urlBuilder.append("/");

		urlBuilder.append("rest");
		urlBuilder.append("/");

		urlBuilder.append("domains");
		urlBuilder.append("/");

		urlBuilder.append(aDomain);
		urlBuilder.append("/");

		urlBuilder.append("projects");
		urlBuilder.append("/");

		urlBuilder.append(aProject);
		urlBuilder.append("/");

		urlBuilder.append(anEntity);

		try {
			urlBuilder.append(URLEncoder.encode(queryString, "UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			VzTLogger.e("UnsupportedEncodingException {}", ex.getMessage());
		}

		return urlBuilder.toString();
	}

	public static String createGetURLWithSubstituteQuery(final String aBaseURL,
			final String aDomain, final String aProject, final String anEntity,
			final String queryString) {

		StringBuilder urlBuilder = new StringBuilder(aBaseURL);
		urlBuilder.append("/");

		urlBuilder.append("rest");
		urlBuilder.append("/");

		urlBuilder.append("domains");
		urlBuilder.append("/");

		urlBuilder.append(aDomain);
		urlBuilder.append("/");

		urlBuilder.append("projects");
		urlBuilder.append("/");

		urlBuilder.append(aProject);
		urlBuilder.append("/");

		urlBuilder.append(anEntity);
		// urlBuilder.append(queryString);

		try {
			urlBuilder.append(URLEncoder.encode(queryString, "UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			VzTLogger.e("UnsupportedEncodingException {}", ex.getMessage());
		}

		return urlBuilder.toString();
	}

	public static String createPostURL(final String aBaseURL,
			final String aDomain, final String aProject, final String anEntity) {

		StringBuilder urlBuilder = new StringBuilder(aBaseURL);
		urlBuilder.append("/");

		urlBuilder.append("rest");
		urlBuilder.append("/");

		urlBuilder.append("domains");
		urlBuilder.append("/");

		urlBuilder.append(aDomain);
		urlBuilder.append("/");

		urlBuilder.append("projects");
		urlBuilder.append("/");

		urlBuilder.append(aProject);
		urlBuilder.append("/");

		urlBuilder.append(anEntity);

		return urlBuilder.toString();
	}

	public static String createPutURL(String aBaseURL, String aDomain,
			String aProject, String anEntity) {
		StringBuilder urlBuilder = new StringBuilder(aBaseURL);
		urlBuilder.append("/");

		urlBuilder.append("rest");
		urlBuilder.append("/");

		urlBuilder.append("domains");
		urlBuilder.append("/");

		urlBuilder.append(aDomain);
		urlBuilder.append("/");

		urlBuilder.append("projects");
		urlBuilder.append("/");

		urlBuilder.append(aProject);
		urlBuilder.append("/");

		urlBuilder.append(anEntity);

		return urlBuilder.toString();
	}
}
