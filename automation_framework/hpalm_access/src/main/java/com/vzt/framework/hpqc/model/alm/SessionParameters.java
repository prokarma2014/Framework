package com.vzt.framework.hpqc.model.alm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "session-parameters")
public class SessionParameters {

	@XmlElement(name = "client-type", required = true)
	private String clientType;

	// @XmlElement(name = "client-type", required = true)
	// private String clientType;
	//
	// @XmlElement(name = "client-type", required = true)
	// private String clientType;

	/**
	 * @return the clientType
	 */
	public String getClientType() {
		return clientType;
	}

	/**
	 * @param clientType
	 *            the clientType to set
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SessionParameters [clientType=" + clientType + "]";
	}

}
