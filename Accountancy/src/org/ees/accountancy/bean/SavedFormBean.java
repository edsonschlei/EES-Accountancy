package org.ees.accountancy.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SavedFormBean implements Serializable {

    private String message;
    private String forwardPage;

    public final String getMessage() {
	return message;
    }

    public final void setMessage(String message) {
	this.message = message;
    }

    public final String getForwardPage() {
	return forwardPage;
    }

    public final void setForwardPage(String forwardPage) {
	this.forwardPage = forwardPage;
    }

    @Override
    public String toString() {
	return "SavedFormBean [message=" + message + ", forwardPage=" + forwardPage + "]";
    }

}
