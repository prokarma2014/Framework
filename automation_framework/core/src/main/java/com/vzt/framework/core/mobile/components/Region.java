package com.vzt.framework.core.mobile.components;

import io.appium.java_client.MobileElement;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
 * Represents regions within a specific element. Region given a point within that region and also the bounds.
 * 
 * @author prokarma
 * @verion 1.0
 */
public enum Region {
    LEFT(-1),
    RIGHT(1),
    MIDDLE(0);

    private final int direction;

    /**
     * @param locatorVariant
     */
    private Region(int locatorVariant) {
        this.direction = locatorVariant;
    }

    /**
     * Gets a tappable point in the given location.
     * 
     * @return
     */
    public Point getTappablePoint(MobileElement element) {
        Point center = element.getCenter();
        Dimension bounds = element.getSize();
        int regionX = center.getX();
        int regionY = center.getY();
        regionX = regionX + direction * (bounds.getWidth() / 4);
        regionY = regionY + direction * (bounds.getHeight() / 4);
        return new Point(regionX, regionY);
    }
}