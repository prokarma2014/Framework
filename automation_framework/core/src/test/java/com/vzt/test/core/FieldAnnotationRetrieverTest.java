package com.vzt.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import  com.vzt.framework.core.annotations.Data;
import  com.vzt.framework.core.annotations.FieldAnnotationRetriever;
import  com.vzt.framework.core.annotations.PathToField;

/**
 * Test class to validate retrieval of set of annotations of interest from the target class.
 *  * @author prokarma
 * @version 1.0
 */
public class FieldAnnotationRetrieverTest {

    /**
     * Verify annotation fields are retrieved.
     */
    @Test
    public void itShouldRetriveAnnotatedFieldsOfSameType() {
        Set<Class<? extends Annotation>> annotSet = new HashSet<Class<? extends Annotation>>();
        annotSet.add(Data.class);
        FieldAnnotationRetriever retriever = new FieldAnnotationRetriever(TestObj1.class, annotSet);
        assertNotNull(retriever);
        List<Pair<PathToField, Annotation>> list = retriever.getAllFieldsUsing(Data.class);
        assertNotNull(list);
        assertEquals("Element mismatch", 3, list.size());
    }

    /**
     * Verify annotation fields are retrieved.
     */
    @Test
    public void itShouldRetriveAnnotatedFieldsOfDifferentType() {
        Set<Class<? extends Annotation>> annotSet = new HashSet<Class<? extends Annotation>>();
        annotSet.add(Data.class);
        annotSet.add(TestAnnotation1.class);
        FieldAnnotationRetriever retriever = new FieldAnnotationRetriever(TestObj2.class, annotSet);
        assertNotNull(retriever);
        List<Pair<PathToField, Annotation>> list = retriever.getAllFieldsUsing(Data.class);
        assertNotNull(list);
        assertEquals("Element mismatch", 5, list.size());
        checkAnnotationTypeMatches(list,Data.class);
        Map<String, Class<?>> expectedTypes = new HashMap<String, Class<?>>();
        expectedTypes.put("stringField", String.class);
        expectedTypes.put("intField", int.class);
        expectedTypes.put("floatField", float.class);
        expectedTypes.put("longField", long.class);
        expectedTypes.put("cal", Calendar.class);
        // assert all the elements belon to the specified annotation type
        Map<String, Class<?>> actualTypes = new HashMap<String, Class<?>>();
        for (Pair<PathToField, Annotation> data : list) {
            actualTypes.put(data.getKey().getTargetField().getName(), data.getKey().getTargetField().getType());
        }
        assertEquals(expectedTypes, actualTypes);
        list = retriever.getAllFieldsUsing(TestAnnotation1.class);
        assertNotNull(list);
        assertEquals("Element mismatch", 2, list.size());
        checkAnnotationTypeMatches(list,TestAnnotation1.class);
    }

    private void checkAnnotationTypeMatches(List<Pair<PathToField, Annotation>> list,Class<?> type) {
        // assert all the elements belon to the specified annotation type
        for (Pair<PathToField, Annotation> data : list) {
            assertEquals(type, data.getRight().annotationType());
        }
    }

}
