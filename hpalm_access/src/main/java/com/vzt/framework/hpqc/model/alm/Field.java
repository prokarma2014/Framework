package com.vzt.framework.hpqc.model.alm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "value" })
public class Field {

	@XmlElement(name = "Value", required = true)
	private List<String> value;

	@XmlAttribute(name = "Name", required = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String aValue) {
		this.name = aValue;
	}

	public void setValue(final List<String> valueList) {
		this.value = valueList;
	}

	public List<String> getListOfValues() {

		if (value.isEmpty()) {
			value = new ArrayList<String>();
		}
		return this.value;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		builder.append("[");
		builder.append("name=");
		builder.append(name);

		builder.append(", value=");
		builder.append(value);
		builder.append("]");

		return builder.toString();
	}

}
