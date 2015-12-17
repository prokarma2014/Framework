package com.vzt.framework.hpqc.entity.extensionproviders;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.vzt.framework.hpqc.model.alm.EntityBean;

@Produces("application/xml")
public class EntityMessageBodyWriter implements MessageBodyWriter<EntityBean> {

	@Override
	public long getSize(final EntityBean hpqcEntity, final Class<?> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType) {

		return 0;
	}

	@Override
	public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {

		return type == EntityBean.class;
	}

	@Override
	public void writeTo(final EntityBean hpqcEntity, final Class<?> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException {

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(EntityBean.class);
			jaxbContext.createMarshaller().marshal(hpqcEntity, entityStream);

		} catch (JAXBException jaxbException) {
			throw new ProcessingException("Error serializing entity bean to the output stream", jaxbException);
		}

	}

}
