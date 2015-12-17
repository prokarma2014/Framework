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

import com.vzt.framework.hpqc.model.alm.ListOfEntitiesBean;

public class EntitiesMessageBodyReader implements MessageBodyReader<ListOfEntitiesBean> {

	@Override
	public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annoatations,
			final MediaType mediaType) {

		return type == ListOfEntitiesBean.class;
	}

	@Override
	public ListOfEntitiesBean readFrom(Class<ListOfEntitiesBean> type, final Type genericType,
			final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders,
			final InputStream entitiesStream) throws IOException, WebApplicationException {

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ListOfEntitiesBean.class);
			ListOfEntitiesBean ListOfEntitiesBean = (ListOfEntitiesBean) jaxbContext.createUnmarshaller()
					.unmarshal(entitiesStream);
			return ListOfEntitiesBean;

		} catch (JAXBException jaxbException) {
			throw new ProcessingException("Error deserializing entities bean", jaxbException);
		}

	}
}
