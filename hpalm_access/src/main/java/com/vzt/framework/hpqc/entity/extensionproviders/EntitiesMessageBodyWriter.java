package com.vzt.framework.hpqc.entity.extensionproviders;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;

public class EntitiesMessageBodyWriter implements MessageBodyWriter<ListOfEntitiesBean> {

	@Override
	public long getSize(final ListOfEntitiesBean hpqcentities, final Class<?> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType) {

		return 0;
	}

	@Override
	public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {

		return type == ListOfEntitiesBean.class;
	}

	@Override
	public void writeTo(final ListOfEntitiesBean hpqcentities, final Class<?> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders,
			OutputStream entitiesStream) throws IOException, WebApplicationException {

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ListOfEntitiesBean.class);
			jaxbContext.createMarshaller().marshal(hpqcentities, entitiesStream);

		} catch (JAXBException jaxbException) {
			throw new ProcessingException("Error serializing entities bean to the output stream", jaxbException);
		}

	}
}
