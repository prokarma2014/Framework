package com.vzt.framework.core.mobile.components;

import java.util.HashMap;
import java.util.Map;

import  com.vzt.framework.core.mobile.MobilePage;
import  com.vzt.framework.core.util.ElementLocatorWrapper;
import org.openqa.selenium.NoSuchElementException;

/**
 * Represents a toggle button that can be used to check or uncheck values.
 * 
 * @author prokarma
 * @version 1.0
 */
public class Toggle extends TapableContent {

    private static final Map<String, String> toggleConstants = new HashMap<String, String>();
    static {
        toggleConstants.put("value", "1");
        toggleConstants.put("checked", "true");
    }

    /**
     * @param wrapper
     * @param page
     */
    public Toggle(ElementLocatorWrapper wrapper, MobilePage page) {
        super(wrapper, page);
    }

    /**
     * Toggles the given element only if the status does not match the toggle status say true is given as input and
     * toggle is off. if null is provided as input not action taken.
     * 
     * @return
     */
    public boolean toggle(Boolean status) {
        boolean toggl = isToggleOn();
        if (status != toggl) {
            tap();
        }
        return toggl;
    }

    /**
     * Returns true if the element is toggle on or off
     * 
     * @return
     */
    public Boolean isToggleOn() {
        waitUntilVisible();
        Boolean toggl = null;
        for (String attribute : toggleConstants.keySet()) {
            if (getElement().getAttribute(attribute) != null) {
                toggl = toggleConstants.get(attribute).equals(getElement().getAttribute(attribute));
                break;
            }
        }
        if (toggl == null) {
            throw new NoSuchElementException("Error while toggling no matching attributes found. Searched for "
                    + toggleConstants);
        }
        return toggl;
    }

    /**
     * Sets the toggle status to default i.e. false.
     * 
     * @return
     */
    public Toggle reset() {
        if (isToggleOn()) {
            tap();
        }
        return this;
    }
}
