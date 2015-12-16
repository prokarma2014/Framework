package com.vzt.framework.core.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Annotation parser that parses through entire class hierarchy looking for test data annotations and returns all the
 * fields annotated by the class and classes referred by the class. The parser looks at only field level annotations and
 * not method level or parameter level annotations.
 * 
 * @author prokarma
 * @version 1.0
 */
public class FieldAnnotationRetriever {

    private static final Logger logger = LoggerFactory.getLogger(FieldAnnotationRetriever.class);

    private Map<Class<? extends Annotation>, List<Pair<PathToField, Annotation>>> annotationToFieldMap = new LinkedHashMap<Class<? extends Annotation>, List<Pair<PathToField, Annotation>>>();

    /**
     * Initialize with the class and annotation for parsing.
     * 
     * @param clzz
     *            taget class
     * @param annotationOfInterest
     *            list of annotation to scan for
     */
    public FieldAnnotationRetriever(Class<?> clzz, Set<Class<? extends Annotation>> annotationOfInterest) {
        processAnnotationForClass(clzz, annotationOfInterest, new PathToField.Builder());
    }

    private void processAnnotationForClass(Class<?> clzz, Set<Class<? extends Annotation>> annotationOfInterest,
            PathToField.Builder path) {
        Field[] fields = clzz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotationOfInterest.contains(annotation.annotationType())) {
                    if (field.getType().isPrimitive() || String.class.equals(field.getType())
                            || Number.class.isAssignableFrom(field.getType())
                            || field.getType().getPackage().getName().startsWith("java.")) {
                        cacheAnnotationInfo(clzz, field, annotation, path);
                        path.reset();
                    } else {
                        path.addParent(field);
                        // dependent class found, hence process annotation for that class.
                        processAnnotationForClass(field.getType(), annotationOfInterest, path);
                    }
                }
            }
        }
    }

    private void cacheAnnotationInfo(Class<?> clzz, Field field, Annotation annotation, PathToField.Builder path) {
        logger.debug("Found annotaion of interest {} for class {} in field {} of type {}", annotation.annotationType()
                .getCanonicalName(), clzz.getName(), field.getName(), field.getType().getClass().getName());
        List<Pair<PathToField, Annotation>> fieldGroup = annotationToFieldMap.get(annotation.annotationType());
        if (fieldGroup == null) {
            fieldGroup = new ArrayList<Pair<PathToField, Annotation>>();
            annotationToFieldMap.put(annotation.annotationType(), fieldGroup);
        }
        path.setTarget(field);
        ImmutablePair<PathToField, Annotation> annotPair = new ImmutablePair<PathToField, Annotation>(path.build(),
                annotation);
        fieldGroup.add(annotPair);
    }

    /**
     * Returns the list of annotations that have
     * 
     * @param annotationCls
     * @return
     */
    public List<Pair<PathToField, Annotation>> getAllFieldsUsing(Class<? extends Annotation> annotationCls) {
        return annotationToFieldMap.get(annotationCls);
    }
}
