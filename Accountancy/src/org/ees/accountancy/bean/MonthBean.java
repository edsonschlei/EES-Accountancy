package org.ees.accountancy.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MonthBean implements Serializable {

    private String number;
    private String description;

    public MonthBean() {
    }

    public MonthBean(String number, String description) {
	this.number = number;
	this.description = description;
    }

    public final String getNumber() {
	return number;
    }

    public final void setNumber(String number) {
	this.number = number;
    }

    public final String getDescription() {
	return description;
    }

    public final void setDescription(String description) {
	this.description = description;
    }

    @Override
    public String toString() {
	return "MonthBean [number=" + number + ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((number == null) ? 0 : number.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof MonthBean)) {
	    return false;
	}
	MonthBean other = (MonthBean) obj;
	if (description == null) {
	    if (other.description != null) {
		return false;
	    }
	} else if (!description.equals(other.description)) {
	    return false;
	}
	if (number == null) {
	    if (other.number != null) {
		return false;
	    }
	} else if (!number.equals(other.number)) {
	    return false;
	}
	return true;
    }

}
