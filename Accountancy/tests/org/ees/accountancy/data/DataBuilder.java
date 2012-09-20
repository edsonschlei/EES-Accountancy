package org.ees.accountancy.data;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import com.google.appengine.api.datastore.Entity;

public class DataBuilder {

    /**
     * Create the Entries with the informed data.
     * data array format:
     * String id = val[0];
     * long date = Long.parseLong(val[1]);
     * double value = Double.parseDouble(val[2]);
     * String credit = val[3];
     * String debit = val[4];
     * String description = val[5];
     * String location = val[6];
     * 
     * @param data
     */
    public static void createEntries(String[][] data) {
	for (String[] val : data) {
	    String id = val[0];
	    long date = Long.parseLong(val[1]);
	    double value = Double.parseDouble(val[2]);
	    String credit = val[3];
	    String debit = val[4];
	    String description = val[5];
	    String location = val[6];
	    Entry.createOrUpdate(id, date, value, credit, debit, description, location);
	    List<String> asList = Arrays.asList(val);
	    System.out.println("Created: " + asList.toString());
	}
    }
    
}
