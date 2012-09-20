package org.ees.accountancy.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.appengine.api.datastore.Entity;

public class CreateInitialAccounts {

    
    public static void create() {
	List<Entity> list = Account.getAll();
	if (list.isEmpty()) {
	    createAccounts();
	}
	fixEntries();
    }

    private static void fixEntries() {
	List<Entity> all = Entry.getAll();
	if (all.isEmpty()) {
	    return;
	}
	Entity entity = all.get(0);
	Integer version = (Integer) entity.getProperty("version");
	if (version == null) {
	    validateEntryDate(all);
	}
	
    }

    private static void validateEntryDate(List<Entity> all) {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
	for (Entity entity : all) {
	    String strDate = (String) entity.getProperty("date");
	    try {
		Date date = sdf.parse(strDate);
		entity.setProperty("date", date.getTime());
		Util.persistEntity(entity);
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
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
