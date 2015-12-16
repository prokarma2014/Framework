package com.vzt.framework.core.actions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;
import com.vzt.framework.core.annotations.Data;
import com.vzt.framework.core.annotations.FieldAccessor;
import com.vzt.framework.core.annotations.FieldAnnotationRetriever;
import com.vzt.framework.core.annotations.PathToField;
import com.vzt.framework.core.annotations.TypeConvertor;
import com.vzt.framework.core.exception.FrameworkException;
import com.vzt.framework.core.util.TestConfiguration;
import com.vzt.framework.data_access.DataSheet;
import com.vzt.framework.data_access.DataSheetContainer;
import com.vzt.framework.data_access.TestDataRow;
import com.vzt.framework.data_access.excel.ExcelTestDataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;

import com.google.common.base.Optional;

/**
 * This class is responsible for mapping the test data from the relevant datasources to the target data beans using
 * annotations. The mapped beans would be used by the test methods as test data for test scripts.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class TestDataProvider {

    private DataSheetContainer container;
    private final Map<Class<?>, FieldAnnotationRetriever> annotationContext = new ConcurrentHashMap<Class<?>, FieldAnnotationRetriever>();

    private static final Logger logger = LoggerFactory.getLogger(TestDataProvider.class);

    /**
     * Initialize with test configuration.
     * 
     * @param testConfig
     */
    public TestDataProvider(TestConfiguration testConfig) {
        initialize(testConfig);
    }

    /**
     * Map test data parameters
     */
    public Object[][] getTestData(TestContext context,ITestContext testContext) {
        List<TestDataRow> testDataRows = loadTestDataForTest(context);
        if (testDataRows.isEmpty()) {
            logger.warn("Empty test data found for test {}", context.getTestMethod().getName());
        }
        Object[][] testData = new Object[testDataRows.size()][context.getTestMethod().getParameterTypes().length];
        for (int testRow = 0; testRow < testDataRows.size(); testRow++) {
            TestDataRow currentRow = testDataRows.get(testRow);
            TypeConvertor convertor = new TypeConvertor(currentRow.getConvertor());
            for (int paramIndex = 0; paramIndex < context.getTestMethod().getParameterTypes().length; paramIndex++) {
                Object finalTestDataValue = null;
                Class<?> parameterType = context.getTestMethod().getParameterTypes()[paramIndex];
                if (isSimpleType(parameterType)) {
                    finalTestDataValue = processSimpleTestData(currentRow, convertor, paramIndex, finalTestDataValue,
                            context.getTestMethod(), parameterType);
                } else {
                    finalTestDataValue = processComplexTestData(currentRow, convertor, parameterType);
                }
                testData[testRow][paramIndex] = finalTestDataValue;
            }
            context.setControl((TestControl) processComplexTestData(currentRow, convertor, TestControl.class));  
        }
        return testData;
    }

    private Object processSimpleTestData(TestDataRow currentRow, TypeConvertor convertor, int paramIndex,
            Object finalTestDataValue, Method parameter, Class<?> parameterType) {
        if (parameter.getAnnotation(Data.class) != null) {
            finalTestDataValue = populateByDataAnnotation(currentRow, convertor, finalTestDataValue, parameter,
                    parameterType);
        } else {
            finalTestDataValue = populateByColumnIndex(currentRow, convertor, paramIndex, parameterType);
        }
        return finalTestDataValue;
    }

    private Object processComplexTestData(TestDataRow currentRow, TypeConvertor convertor, Class<?> parameterType) {
        Object finalTestDataValue = createParameter(parameterType);
        FieldAccessor accessor = new FieldAccessor(finalTestDataValue);
        cacheTypeAnnotationIfNotPresent(parameterType);
        List<Pair<PathToField, Annotation>> fieldsToPopulate = annotationContext.get(parameterType).getAllFieldsUsing(
                Data.class);
        setComplexTypeTestData(currentRow, convertor, accessor, fieldsToPopulate);
        return finalTestDataValue;
    }

    private void setComplexTypeTestData(TestDataRow currentRow, TypeConvertor convertor, FieldAccessor accessor,
            List<Pair<PathToField, Annotation>> fieldsToPopulate) {
        for (Pair<PathToField, Annotation> fieldParam : fieldsToPopulate) {
            Data testMetaData = (Data) fieldParam.getRight();
            String columnName = testMetaData.contextPrefix() + testMetaData.name();
            if (currentRow.getHeader().containsColumnName(columnName)) {
                Object value = currentRow.valueAt(currentRow.getHeader().getColumnIndexFor(columnName));
                accessor.set(fieldParam.getKey(),
                        convertor.convert(fieldParam.getKey().getTargetField().getType(), value));
            } else {
                accessor.set(fieldParam.getKey(), null);
            }
        }
    }

    private void cacheTypeAnnotationIfNotPresent(Class<?> parameterType) {
        if (!annotationContext.containsKey(parameterType)) {
            Set<Class<? extends Annotation>> annotations = new HashSet<Class<? extends Annotation>>();
            annotations.add(Data.class);
            annotationContext.put(parameterType, new FieldAnnotationRetriever(parameterType, annotations));
        }
    }

    private boolean isSimpleType(Class<?> parameterType) {
        return parameterType.isPrimitive() || parameterType.getPackage().getName().startsWith("java.")
                || String.class.equals(parameterType) || Number.class.isAssignableFrom(parameterType);
    }

    private Object populateByColumnIndex(TestDataRow currentRow, TypeConvertor convertor, int paramIndex,
            Class<?> parameterType) {
        return convertor.convert(parameterType, currentRow.valueAt(paramIndex));
    }

    private Object populateByDataAnnotation(TestDataRow currentRow, TypeConvertor convertor, Object finalTestDataValue,
            Method parameter, Class<?> parameterType) {
        Data testMetaData = parameter.getAnnotation(Data.class);
        String columnName = testMetaData.contextPrefix() + testMetaData.name();
        if (currentRow.getHeader().containsColumnName(columnName)) {
            Object value = currentRow.valueAt(currentRow.getHeader().getColumnIndexFor(columnName));
            finalTestDataValue = convertor.convert(parameterType, value);
        }
        return finalTestDataValue;
    }

    private Object createParameter(Class<?> parameterType) {
        Object paramInstance;
        try {
            paramInstance = parameterType.newInstance();
        } catch (Exception excp) {
            throw new FrameworkException("Error while creating parameter instance " + parameterType, excp);
        }
        return paramInstance;
    }

    private List<TestDataRow> loadTestDataForTest(TestContext context) {
        List<DataSheet> dataSheets = container.findSheetByName(context.getTestMethod().getName());
        List<TestDataRow> testDataRows = new ArrayList<TestDataRow>();
        for (DataSheet sheet : dataSheets) {
            for (TestDataRow row : sheet) {
                testDataRows.add(row);
            }
        }
        return testDataRows;
    }

    private void initialize(TestConfiguration testConfig) {
        List<DataSheet> dataSheets = new ArrayList<DataSheet>();
        Optional<String> excelBasePath = Optional.fromNullable(testConfig.getConfig("excel.baselocation"));
        if (excelBasePath.isPresent()) {
            // process excel sheet related data.
            ExcelTestDataFactory factory = new ExcelTestDataFactory(excelBasePath.get());
            dataSheets.addAll(factory.create());
        }
        container = new DataSheetContainer(dataSheets);
    }
}
