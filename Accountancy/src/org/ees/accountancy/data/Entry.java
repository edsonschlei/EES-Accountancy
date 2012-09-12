package org.ees.accountancy.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.User;

public class Entry {

    private Entry() {
    }

    private static final String ID = "ID";
    private static final String DATE = "date";
    private static final String VALUE = "value";
    private static final String CREDIT = "credit_code";
    private static final String DEBIT = "debit_code";
    private static final String DESCRIPTION = "description";
    private static final String LOCATION = "location";

    /**
     * Create or update a new Entry. <br>
     * To update is necessary inform the id.
     * 
     * @param user
     * @param id
     * @param date
     * @param value
     * @param credit
     * @param debit
     * @param description
     * @param location
     * @return
     */
    public static Entity createOrUpdate(String id, String date, double value, String credit,
	    String debit, String description, String location) {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY);
	Entity eUser = ConnectedUser.getUser();
	Entity entry = null;
	if (id != null) {
	    entry = getSingleEntry(id);
	}
	if (entry == null) {
	    entry = new Entity(Entities.ENTRIES.getTableName(), eUser.getKey());
	    UUID uuid = UUID.randomUUID();
	    entry.setProperty(ID, uuid.toString());
	}
	entry.setProperty(DATE, date);
	entry.setProperty(VALUE, value);
	entry.setProperty(CREDIT, credit);
	entry.setProperty(DEBIT, debit);
	entry.setProperty(DESCRIPTION, description);
	entry.setProperty(LOCATION, location);

	Util.persistEntity(entry);
	return entry;
    }

    /**
     * 
     * @param id
     * @param user
     * @return
     */
    private static Entity getSingleEntry(String id) {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY);
	Entity user = ConnectedUser.getUser();
	Query query = new Query(Entities.ENTRIES.getTableName());
	query.setAncestor(user.getKey());
	query.addFilter(ID, FilterOperator.EQUAL, id);
	PreparedQuery prepare = Util.getDatastoreServiceInstance().prepare(query);
	List<Entity> list = prepare.asList(FetchOptions.Builder.withDefaults());
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    /**
     * Return all entries of the informed user.
     * 
     * @param user
     * @return
     */
    public static List<Entity> getAll() {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY);
	Entity user = ConnectedUser.getUser();
	Query query = new Query(Entities.ENTRIES.getTableName());
	query.setAncestor(user.getKey());
	query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN,
		user.getKey());
	List<Entity> results = Util.getDatastoreServiceInstance().prepare(query)
		.asList(FetchOptions.Builder.withDefaults());
	return results;
    }

    public static Date getDate(Entity entity) {
	String date = (String) entity.getProperty(DATE);
	try {
	    return DateFormat.getDateInstance(DateFormat.SHORT).parse(date);
	} catch (ParseException e) {
	}
	return null;
    }

    /**
     * This is not the best way to filter records, next step is to filter the
     * record using the query filter.
     * 
     * @param year
     * @param month
     * @param code
     * @return
     */
    public static List<Entity> getAll(String year, String month, String code) {
	List<Entity> results = getAll(code);
	if (results.isEmpty()) {
	    return results;
	}
	SimpleDateFormat sdfMonthNum = new SimpleDateFormat("M");
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	List<Entity> entities = new ArrayList<Entity>(results);

	for (Iterator<Entity> iter = entities.iterator(); iter.hasNext();) {
	    Entity entity = (Entity) iter.next();
	    Date date = Entry.getDate(entity);
	    String yearValue = sdfYear.format(date);
	    if (!year.equals(yearValue)) {
		iter.remove();
		continue;
	    }
	    String monthValue = sdfMonthNum.format(date);
	    if (!month.equals(monthValue)) {
		iter.remove();
		continue;
	    }
	}
	return entities;
    }

    /**
     * Return all entries that have Credit or Debit for the informed code.
     * 
     * @param code
     * @return
     */
    public static List<Entity> getAll(String code) {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY);
	Entity user = ConnectedUser.getUser();
	Query query = new Query(Entities.ENTRIES.getTableName());
	query.setAncestor(user.getKey());
	query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN,
		user.getKey());

	// CompositeFilter or = CompositeFilterOperator.or(
	// FilterOperator.EQUAL.of(CREDIT, code),
	// FilterOperator.EQUAL.of(DEBIT, code));
	//
	// query.setFilter(or);

	List<Entity> results = Util.getDatastoreServiceInstance().prepare(query)
		.asList(FetchOptions.Builder.withDefaults());

	List<Entity> filteredList = new ArrayList<Entity>(results);
	for (Iterator<Entity> iter = filteredList.iterator(); iter.hasNext();) {
	    Entity entity = (Entity) iter.next();
	    String creditAccount = Entry.getCreditAccount(entity);
	    String debitAccount = Entry.getDebitAccount(entity);
	    if (!(code.equals(debitAccount) || code.equals(creditAccount))) {
		iter.remove();
	    }
	}

	System.out.println(filteredList);

	return filteredList;
    }

    public static String getCreditAccount(Entity entity) {
	return (String) entity.getProperty(CREDIT);
    }

    public static String getDebitAccount(Entity entity) {
	return (String) entity.getProperty(DEBIT);
    }

    public static Double getValue(Entity entity) {
	return (Double) entity.getProperty(VALUE);
    }

}
