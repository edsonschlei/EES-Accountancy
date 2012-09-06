package org.ees.accountancy.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class UserDataEntriesFilteredBean implements Serializable {

    private List<EntryBean> entries = new ArrayList<EntryBean>();

    public final List<EntryBean> getEntries() {
	return entries;
    }

    public final void setEntries(List<EntryBean> entries) {
	this.entries = entries;
    }

    @Override
    public String toString() {
	return "UserDataEntriesFilteredBean [entries=" + entries + "]";
    }

}
