package com.vzt.test.core;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import com.vzt.framework.core.annotations.Data;
import com.vzt.framework.core.annotations.FieldAccessor;
import com.vzt.framework.core.annotations.FieldAnnotationRetriever;
import com.vzt.framework.core.annotations.PathToField;

/**
 * Test for field accessor to verify retrieval of data for nested types and well top level data from test objects.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class FieldAccessorTest {

    @Test
    public void itShouldSetFieldsOfSupportedTypes() {
        Set<Class<? extends Annotation>> annotSet = new HashSet<Class<? extends Annotation>>();
        annotSet.add(Data.class);
        FieldAnnotationRetriever retriever = new FieldAnnotationRetriever(TestObj2.class, annotSet);
        assertNotNull(retriever);
        List<Pair<PathToField, Annotation>> list = retriever.getAllFieldsUsing(Data.class);
        assertNotNull(list);
        Map<Class<?>, PathToField> annotatedFieldToTypeMap = createTypeToFieldMap(list);
        FieldAccessor accessor = new FieldAccessor(new TestObj2());
        checkType(annotatedFieldToTypeMap, accessor, String.class, null, "String1");
        checkType(annotatedFieldToTypeMap, accessor, long.class, 0L, 10L);
        checkType(annotatedFieldToTypeMap, accessor, float.class, 0.0f, 10.01f);
        checkType(annotatedFieldToTypeMap, accessor, Calendar.class, null, Calendar.getInstance());
    }

    @Test
    public void itShouldSetFieldsOfNestedTypeSingleLevel() {
        Set<Class<? extends Annotation>> annotSet = new HashSet<Class<? extends Annotation>>();
        annotSet.add(Data.class);
        FieldAnnotationRetriever retriever = new FieldAnnotationRetriever(TestObj3.class, annotSet);
        assertNotNull(retriever);
        List<Pair<PathToField, Annotation>> list = retriever.getAllFieldsUsing(Data.class);
        assertNotNull(list);
        Map<Class<?>, PathToField> annotatedFieldToTypeMap = createTypeToFieldMap(list);
        TestObj3 obj3 = new TestObj3();
        FieldAccessor accessor = new FieldAccessor(obj3);
        checkType(annotatedFieldToTypeMap, accessor, String.class, null, "String1");
        checkType(annotatedFieldToTypeMap, accessor, long.class, 0L, 10L);
        checkType(annotatedFieldToTypeMap, accessor, float.class, 0.0f, 10.01f);
        Calendar cal = Calendar.getInstance();
        checkType(annotatedFieldToTypeMap, accessor, Calendar.class, null, cal);
        assertNotNull(obj3.getObj4());
        assertNotNull(obj3.getObj4().getObj2());
        assertEquals("String1", obj3.getObj4().getObj2().getStringField());
        assertEquals(cal, obj3.getObj4().getObj2().getCal());
    }

    @Test
    public void itShouldSetFieldsOfNestedTypeTwoLevel() {
        Set<Class<? extends Annotation>> annotSet = new HashSet<Class<? extends Annotation>>();
        annotSet.add(Data.class);
        FieldAnnotationRetriever retriever = new FieldAnnotationRetriever(TestObj4.class, annotSet);
        assertNotNull(retriever);
        List<Pair<PathToField, Annotation>> list = retriever.getAllFieldsUsing(Data.class);
        assertNotNull(list);
        Map<Class<?>, PathToField> annotatedFieldToTypeMap = createTypeToFieldMap(list);
        TestObj4 obj4 = new TestObj4();
        FieldAccessor accessor = new FieldAccessor(obj4);
        checkType(annotatedFieldToTypeMap, accessor, String.class, null, "String1");
        checkType(annotatedFieldToTypeMap, accessor, long.class, 0L, 10L);
        checkType(annotatedFieldToTypeMap, accessor, float.class, 0.0f, 10.01f);
        Calendar cal = Calendar.getInstance();
        checkType(annotatedFieldToTypeMap, accessor, Calendar.class, null, cal);
        assertNotNull(obj4.getObj2());
        assertEquals("String1", obj4.getObj2().getStringField());
        assertEquals(cal, obj4.getObj2().getCal());
    }

    private void checkType(Map<Class<?>, PathToField> annotatedFieldToTypeMap, FieldAccessor accessor, Class<?> type,
            Object initialValue, Object value) {
        PathToField field = annotatedFieldToTypeMap.get(type);
        assertEquals(initialValue, accessor.get(field));
        accessor.set(field, value);
        assertEquals(value, accessor.get(annotatedFieldToTypeMap.get(type)));
    }

    private Map<Class<?>, PathToField> createTypeToFieldMap(List<Pair<PathToField, Annotation>> list) {
        Map<Class<?>, PathToField> annotatedFieldToTypeMap = new HashMap<Class<?>, PathToField>();
        for (Pair<PathToField, Annotation> annotatedField : list) {
            annotatedFieldToTypeMap.put(annotatedField.getKey().getTargetField().getType(), annotatedField.getKey());
        }
        return annotatedFieldToTypeMap;
    }

}
