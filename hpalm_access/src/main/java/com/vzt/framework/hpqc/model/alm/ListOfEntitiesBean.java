package com.vzt.framework.hpqc.model.alm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "entity" })
@XmlRootElement(name = "Entities")
public class ListOfEntitiesBean {

	@XmlElement(name = "Entity", required = true)
	private List<EntityBean> entity;

	@XmlAttribute(name = "TotalResults", required = true)
	private int totalResults;

	public ListOfEntitiesBean() {

	}

	public ListOfEntitiesBean(final ListOfEntitiesBean listOfEntitiesBean) {
		entity = new ArrayList<EntityBean>(listOfEntitiesBean.getListOfEntity());
	}

	public List<EntityBean> getListOfEntity() {

		if (entity.isEmpty()) {
			entity = new ArrayList<EntityBean>();
		}

		return this.entity;
	}

	public void setListOfEntity(final List<EntityBean> entityList) {

		this.entity = entityList;
	}

	public int getTotalResults() {

		return totalResults;
	}

	public void setTotalResults(final int aTotalResults) {

		this.totalResults = aTotalResults;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("List of records: ");
		builder.append("[");
		builder.append("totalResults=");
		builder.append(totalResults);
		builder.append(", ");
		builder.append(entity);

		return builder.toString();
	}

}
