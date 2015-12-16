package com.vzt.framework.hpqc;

/**
 * author Cassian Raja
 */
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.vzt.framework.hpqc.entity.extensionproviders.EntitiesMessageBodyReader;
import com.vzt.framework.hpqc.entity.extensionproviders.EntitiesMessageBodyWriter;
import com.vzt.framework.hpqc.entity.extensionproviders.EntityMessageBodyReader;
import com.vzt.framework.hpqc.entity.extensionproviders.EntityMessageBodyWriter;
import com.vzt.framework.hpqc.logger.VzTLogger;
import com.vzt.framework.hpqc.utils.Constants;

public class InitializeRestClient {

	private InitializeRestClient() {

	}

	public static Client doCreate() {

		Client restClient = null;

		HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.universal(Constants.USERNAME,
				Constants.PASSWORD);

		try {
			restClient = ClientBuilder.newClient().register(authenticationFeature)
					.register(EntityMessageBodyReader.class).register(EntityMessageBodyWriter.class)
					.register(EntitiesMessageBodyReader.class).register(EntitiesMessageBodyWriter.class);
		} catch (Exception ex) {
			VzTLogger.e("Exception {}", ex.getMessage());
		}

		return restClient;

	}

}
