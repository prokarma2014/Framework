package com.vzt.framework.core.annotations;

import java.lang.reflect.Field;

import com.vzt.framework.core.exception.FrameworkException;

/**
 * Class provides API to access field and get & set relevant data given the top level object instance and path to the
 * field. Each accessor is associated with a specific class and can be used get & set multiple fields.
 * 
 * @author prokarma
 * @version 1.0
 */
public class FieldAccessor {

    private final Object topLevelClsInstance;

    /**
     * @param topLevelClsInstance
     */
    public FieldAccessor(Object topLevelClsInstance) {
        this.topLevelClsInstance = topLevelClsInstance;
    }

    /**
     * Gets the object value for given field
     * 
     * @param fieldPath
     * @return
     */
    public Object get(PathToField fieldPath) {
        try {
            fieldPath.getTargetField().setAccessible(true);
            return fieldPath.getTargetField().get(findField(fieldPath));
        } catch (Exception excp) {
            throw new FrameworkException("Field Set error " + fieldPath, excp);
        }
    }

    /**
     * Set the object value for given field
     * 
     * @param fieldPath
     * @return
     */
    public void set(PathToField fieldPath, Object value) {
        try {
            fieldPath.getTargetField().setAccessible(true);
            fieldPath.getTargetField().set(findField(fieldPath), value);
        } catch (Exception excp) {
            throw new FrameworkException("Field Set error " + fieldPath, excp);
        }
    }

    private Object findField(PathToField fieldPath) throws IllegalAccessException, InstantiationException {
        Object nextInPath = topLevelClsInstance;
        for (Field field : fieldPath.getPath()) {
            Object parent = nextInPath;
            field.setAccessible(true);
            nextInPath = field.get(nextInPath);
            if (nextInPath == null) {
                nextInPath = createInstanceFromField(field, parent);
            }
        }
        return nextInPath;
    }

    private Object createInstanceFromField(Field field, Object prev) throws InstantiationException,
            IllegalAccessException {
        field.set(prev, field.getType().newInstance());
        return field.get(prev);
    }
}