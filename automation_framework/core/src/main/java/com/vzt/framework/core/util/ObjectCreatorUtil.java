package com.vzt.framework.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import  com.vzt.framework.core.exception.FrameworkException;

import com.google.common.base.Optional;

/**
 * Utility that can be used to create instance of objects using reflections. Provides value add by providing APIs that
 * can search constructors based on given types and choose the right one for invocation etc.
 * 
 * @author prokarma
 * @version 1.0
 */
public class ObjectCreatorUtil {

    /**
     * Searches the given cls for the constructors matching parameters and invokes the right one if found and returns a
     * optional instance.
     * 
     * @param cls
     * @param parameters
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> selectConstructorAndInvoke(Class<T> cls, Object... parameters) {
        try {

            Map<Class<?>, Object> expectedTypes = new HashMap<Class<?>, Object>();
            for (Object param : parameters) {
                expectedTypes.put(param.getClass(), param);
            }
            T pageObj = null;

            for (Constructor<T> constructor : (Constructor<T>[]) cls.getConstructors()) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length != parameters.length + 1) {
                    continue;
                }
                Object[] args = new Object[parameters.length + 1];
                int count = 0;
                boolean allMatch = true;
                for (Class<?> param : parameterTypes) {
                    if (expectedTypes.containsKey(param)) {
                        args[count++] = expectedTypes.get(param);
                    } else {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    pageObj = constructor.newInstance(args);
                    break;
                }
            }
            return Optional.fromNullable(pageObj);
        } catch (Exception excp) {
            throw new FrameworkException("Error while creating the object " + cls, excp);
        }
    }

}
