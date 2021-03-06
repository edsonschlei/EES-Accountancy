package org.ees.accountancy.data;

import java.util.List;

import com.google.appengine.api.datastore.Entity;

public class CreateInitialAccounts {

    
    public static void create() {
	List<Entity> list = Account.getAll();
	if (list.isEmpty()) {
	    createAccounts();
	}
    }

    private static void createAccounts() {
	String[] accountNames = {
		"Bank", "Wallet", "Grocery", "Clothes", "Credit Card", "Salary", "Phone", "Books and Magazine"
	};
	String[] accountCode = {
		"1.1.1", "1.2.2", "5.3.1", "5.4.1", "2.2.1", "4.1.3", "5.1.6", "5.2.3"
	};

	for (int i = 0; i < accountCode.length; i++) {
	    Account.createOrUpdate(accountCode[i], accountNames[i]);
	}
    }

    
}
