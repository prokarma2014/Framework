package com.vzt.framework.hpqc.model.alm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "fields" })
@XmlRootElement(name = "Entity")
public class EntityBean {

	@XmlElement(name = "Fields", required = true)
	private Fields fields;

	@XmlAttribute(name = "Type", required = true)
	private String type;

	public EntityBean() {

	}

	public EntityBean(final EntityBean entity) {
		type = entity.getType();
		fields = new Fields(entity.getFields());
	}

	public Fields getFields() {
		return fields;
	}

	public void setFields(final Fields value) {
		this.fields = value;
	}

	public String getType() {
		return type;
	}

	public void setType(final String value) {
		this.type = value;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		return "EntityBean [fields=" + fields + ", type=" + type + "]";
	}

}
