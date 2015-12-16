package com.vzt.framework.core.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindAll;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSFindBys;

import java.lang.reflect.Field;

import  com.vzt.framework.core.exception.FrameworkException;
import  com.vzt.framework.core.mobile.MobilePage;
import  com.vzt.framework.core.mobile.components.TapableContent;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Special field decorator for mobile elements to supports custom mobile types.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class MobileSpecialTypeFieldDecorator extends SpecialFieldTypeDecorator {

    private static final Logger logger = LoggerFactory.getLogger(MobileSpecialTypeFieldDecorator.class);

    /**
     * Initialize with web driver and web page.
     * 
     * @param driver
     * @param webPage
     */
    public MobileSpecialTypeFieldDecorator(MobilePage webPage) {
        super(webPage, MobilePage.class, getElementLocatorFactory(webPage.getDriver()), new AppiumFieldDecorator(
                webPage.getDriver()));
    }

    /**
     * Initialize with web driver and web page.
     * 
     * @param driver
     * @param webPage
     */
    public MobileSpecialTypeFieldDecorator(TapableContent element) {
        super(element, MobilePage.class, getElementLocatorFactory(element.getPage().getDriver()),
                new AppiumFieldDecorator(element.getPage().getDriver()));
    }

    /**
     * Override with BaseMobileElement
     */
    @Override
    protected boolean isSupportedType(Class<?> type) {
        return TapableContent.class.isAssignableFrom(type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vzt.framework.core.util.SpecialFieldTypeDecorator#isSupportedAnnotation(java.lang.reflect.Field)
     */
    @Override
    protected boolean isSupportedAnnotation(Field field) {
        boolean isSupported = super.isSupportedAnnotation(field);
        if (!isSupported) {
            isSupported = true;
            if (field.getAnnotation(AndroidFindBy.class) == null && field.getAnnotation(iOSFindBy.class) == null
                    && field.getAnnotation(iOSFindAll.class) == null && field.getAnnotation(iOSFindBys.class) == null
                    && field.getAnnotation(AndroidFindBys.class) == null
                    && field.getAnnotation(AndroidFindAll.class) == null) {
                isSupported = false;
            }
        }
        return isSupported;
    }

    /*
     * This method returns the appium element locator factory. The way to get the factory is unconventional as the
     * default decorator is initialized and introspected through reflections for creation of the locator factory.
     * Brittle code as if the field name changes the code will fail. Implemented this way as Appium does not currently
     * have a public level class for element factory.
     */
    private static ElementLocatorFactory getElementLocatorFactory(AppiumDriver<?> driver) {
        try {
            AppiumFieldDecorator decorator = new AppiumFieldDecorator(driver);
            Field field = AppiumFieldDecorator.class.getDeclaredField("factory");
            field.setAccessible(true);
            return (ElementLocatorFactory) field.get(decorator);
        } catch (Exception excp) {
            logger.error("Error while initializing the element locator factory for AppiumDecorator", excp);
            throw new FrameworkException("Error while initializing the decorator", excp);
        }
    }
}