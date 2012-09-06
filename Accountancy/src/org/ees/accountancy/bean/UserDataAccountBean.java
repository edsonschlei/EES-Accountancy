package org.ees.accountancy.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UserDataAccountBean implements Serializable {

    private String code = "";
    private String description = "";
    private String invalidCode = "";
    private String invalidDescription = "";
    private List<String> invalidMessages = new ArrayList<String>();

    public final String getCode() {
	return code;
    }

    public final void setCode(String code) {
	this.code = code;
    }

    public final String getDescription() {
	return description;
    }

    public final void setDescription(String description) {
	this.description = description;
    }

    public final String getInvalidCode() {
	return invalidCode;
    }

    public final void setInvalidCode(String invalidCode) {
	this.invalidCode = invalidCode;
	invalidMessages.add(invalidCode);
    }

    public final String getInvalidDescription() {
	return invalidDescription;
    }

    public final void setInvalidDescription(String invalidDescription) {
	this.invalidDescription = invalidDescription;
	invalidMessages.add(invalidDescription);
    }

    public final List<String> getInvalidMessages() {
	return invalidMessages;
    }

    public final void setInvalidMessages(List<String> invalidMessages) {
	this.invalidMessages = invalidMessages;
    }

    @Override
    public String toString() {
	return "UserDataAccountBean [code=" + code + ", description=" + description
		+ ", invalidCode=" + invalidCode + ", invalidDescription=" + invalidDescription
		+ ", invalidMessages=" + invalidMessages + "]";
    }

}
