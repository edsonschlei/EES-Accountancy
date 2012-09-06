package org.ees.accountancy.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccountBean implements Serializable {

    private String code = "";
    private String description = "";

    public AccountBean() {
    }
    
    public AccountBean(String code, String description) {
	this.code = code;
	this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
	return "AccountBean [code=" + code + ", description=" + description + "]";
    }
    
}
