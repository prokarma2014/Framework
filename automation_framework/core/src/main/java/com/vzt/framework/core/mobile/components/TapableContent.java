package com.vzt.framework.core.mobile.components;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import  com.vzt.framework.core.mobile.MobilePage;
import  com.vzt.framework.core.util.ElementLocatorWrapper;
import  com.vzt.framework.core.util.MobileSpecialTypeFieldDecorator;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a mobile base element that 
 * 
 * @author prokarma
 * @verion 1.0
 */
public class TapableContent {

    /**
     * Invocation handler capable of routing calls after finding the element. Once element is found, it is cached and
     * futher calls does not result in relookup of the elements.
     */
    private final class MobileElementHandler implements MethodInterceptor {

        /**
         * Lookup element and invoke the target method.
         */
        public Object intercept(Object instance, Method calledMethod, Object[] args, MethodProxy proxy) throws Throwable {
            MobileElement baseElement = (MobileElement)TapableContent.this.wrapper.findElement();
            validate();
            return calledMethod.invoke(baseElement, args);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(TapableContent.class);

    private final MobilePage page;
    private final TouchAction actions;
    private final ElementLocatorWrapper wrapper;
    private MobileElement element;

    /**
     * @param page
     * @param actions
     * @param wrapper
     */
    public TapableContent(ElementLocatorWrapper wrapper, MobilePage page) {
        this.page = page;
        this.actions = new TouchAction(page.getDriver());
        this.wrapper = wrapper;
        this.element = createElement();
        logger.debug("Creating element {} ", this);
    }

    private MobileElement createElement() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MobileElement.class);
        enhancer.setCallback(new MobileElementHandler());
        return (MobileElement)enhancer.create();
    }

    /**
     * Validates the given element is availabe prior to any action.
     */
    public void validate() {
    }

    /**
     * @return the element
     */
    public MobileElement getElement() {
        return element;
    }

    /**
     * @return the page
     */
    public MobilePage getPage() {
        return page;
    }

    /**
     * Initializes any sub elements that may be available as part of the current web element link.
     */
    protected void initializeSubElements() {
        PageFactory.initElements(new MobileSpecialTypeFieldDecorator(this), this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MobileBaseElement [" + (page != null ? "page=" + page + ", " : "")
                + (getClass() != null ? "getClass()=" + getClass() : "") + "]";
    }

    /**
     * Wait untils specific conditions on the web element.
     * 
     * @param condition
     * @param timeout
     */
    public TapableContent waitUntilVisible() {
        // use the raw element.
        page.waitFor(ExpectedConditions.visibilityOf(getElement()));
        return this;
    }

    /**
     * Returns a actionable instance.
     * 
     * @return
     */
    public TouchAction exec() {
        return actions;
    }

    /**
     * Returns the text associated with the element
     * 
     * @return
     */
    public String content() {
        waitUntilVisible();
        return getElement().getText();
    }

    /**
     * Taps on the element.
     */
    public void tap() {
        waitUntilVisible();
        exec().tap(getElement()).perform();
    }

    /**
     * Taps the element in a general region.
     * 
     * @param region
     */
    public void tap(Region region) {
        Point tappablePoint = region.getTappablePoint(getElement());
        exec().tap(tappablePoint.getX(), tappablePoint.getY()).perform();
    }

    /**
     * Returns true if the element is displayed
     * 
     * @return
     */
    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }
}