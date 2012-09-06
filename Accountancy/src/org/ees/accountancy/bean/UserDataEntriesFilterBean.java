package org.ees.accountancy.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class UserDataEntriesFilterBean implements Serializable {

    private List<String> years = new ArrayList<String>();
    private Map<String, List<MonthBean>> months = new HashMap<String, List<MonthBean>>();
    private List<AccountBean> accounts = new ArrayList<AccountBean>();

    public final List<String> getYears() {
	return years;
    }

    public final void setYears(List<String> years) {
	this.years = years;
    }

    public final Map<String, List<MonthBean>> getMonths() {
	return months;
    }

    public final void setMonths(Map<String, List<MonthBean>> months) {
	this.months = months;
    }

    public final List<AccountBean> getAccounts() {
	return accounts;
    }

    public final void setAccounts(List<AccountBean> accounts) {
	this.accounts = accounts;
    }

}
