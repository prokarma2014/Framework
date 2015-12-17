package com.vzt.framework.hpqc.entity.extensionproviders;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.vzt.framework.hpqc.model.alm.EntityBean;

public class EntityMessageBodyReader implements MessageBodyReader<EntityBean> {

	@Override
	public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annoatations,
			final MediaType mediaType) {

		return type == EntityBean.class;
	}

	@Override
	public EntityBean readFrom(Class<EntityBean> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream)
					throws IOException, WebApplicationException {

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(EntityBean.class);
			EntityBean entityBean = (EntityBean) jaxbContext.createUnmarshaller().unmarshal(entityStream);
			return entityBean;

		} catch (JAXBException jaxbException) {
			throw new ProcessingException("Error deserializing entity bean", jaxbException);
		}

	}

}
