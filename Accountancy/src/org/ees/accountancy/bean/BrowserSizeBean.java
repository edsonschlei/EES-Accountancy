package org.ees.accountancy.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BrowserSizeBean implements Serializable {

    private int height;
    private int width;

    public final int getHeight() {
	return height;
    }

    public final void setHeight(int height) {
	this.height = height;
    }

    public final int getWidth() {
	return width;
    }

    public final void setWidth(int width) {
	this.width = width;
    }

    @Override
    public String toString() {
	return "BrowserSizeBean [height=" + height + ", width=" + width + "]";
    }

    
}
