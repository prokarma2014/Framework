package com.vzt.framework.hpqc.model.alm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "field" })
public class Fields {

	@XmlElement(name = "Field", required = true)
	private List<Field> field;

	public Fields() {

	}

	public Fields(final Fields fields) {

		field = new ArrayList<Field>(fields.getListOfFields());
	}

	public List<Field> getListOfFields() {

		if (field.isEmpty()) {
			field = new ArrayList<Field>();
		}

		return this.field;
	}

	public void setListOfFields(final List<Field> value) {
		this.field = value;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		builder.append("Fields ");
		builder.append(field);
		builder.append("]");

		return builder.toString();
	}
}
