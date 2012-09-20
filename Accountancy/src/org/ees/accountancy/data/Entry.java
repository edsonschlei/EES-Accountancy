package org.ees.accountancy.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;

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
    private static final String VERSION = "version";
    private static final Integer RECORD_VERSION = 1;

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
    public static Entity createOrUpdate(String id, long date, double value, String credit,
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
	entry.setProperty(VERSION, RECORD_VERSION);
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
	Filter filter = new Query.FilterPredicate(ID, Query.FilterOperator.EQUAL, id);
	List<Entity> list = queryEntries(filter);
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
	return queryEntries(null);
    }

    /**
     * Return the Date value assigned with the DATE field. 
     * @param entity
     * @return
     */
    public static Date getDate(Entity entity) {
	Long date = (Long) entity.getProperty(DATE);
	return new Date(date);
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
    public static List<Entity> getAll(int year, int month, String code) {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	// create initial date
	Date initialDate = new Date();
	try {
	    initialDate = sdf.parse("1/" + month + "/" + year);
	} catch (ParseException e) {
	    e.printStackTrace();
	    return Collections.emptyList();
	}
	
	// calculate final date
	Calendar cal = Calendar.getInstance();
	cal.setTime(initialDate);
	cal.add(Calendar.MONTH, 1);
	Date finalDate = cal.getTime();
	
	// Account code filter
	Filter codeFilter = getCodeFilter(code);
	
	// between dates filter "date >= 1/mm/yyyy and date < 1/mm+1/yyyy(may be +1) 
	Filter initialDateFilter = new Query.FilterPredicate(DATE, Query.FilterOperator.GREATER_THAN_OR_EQUAL, initialDate.getTime());
	Filter finalDateFilter = new Query.FilterPredicate(DATE, Query.FilterOperator.LESS_THAN, finalDate.getTime());
	Filter dateFilter = CompositeFilterOperator.and(initialDateFilter, finalDateFilter);

	Filter filter = CompositeFilterOperator.and(codeFilter, dateFilter);
	
	return queryEntries(filter);

    }

    /**
     * Return the records oF the Entry entity applying the informed filter.
     * 
     * @param filter
     * @return
     */
    private static List<Entity> queryEntries(Filter filter) {
	Query query = createEntryQuery();
	if (filter != null) {
	    query.setFilter(filter);
	}
	
	query.addSort(DATE, Query.SortDirection.ASCENDING);

	return Util.getDatastoreServiceInstance().prepare(query)
		.asList(FetchOptions.Builder.withDefaults());
    }

    /**
     * Return all entries that have Credit or Debit for the informed code.
     * 
     * @param code
     * @return
     */
    public static List<Entity> getAll(String code) {
	Filter codeFilter = getCodeFilter(code);
	return queryEntries(codeFilter);
    }

    /**
     * Create and prepare a query to be used to filter Entries.
     * @return
     */
    private static Query createEntryQuery() {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY);
	Entity user = ConnectedUser.getUser();
	Query query = new Query(Entities.ENTRIES.getTableName());
	query.setAncestor(user.getKey());
	return query;
    }

    private static Filter getCodeFilter(String code) {
	Filter creditFilter = new Query.FilterPredicate(CREDIT, Query.FilterOperator.EQUAL, code);
	Filter debitFilter = new Query.FilterPredicate(DEBIT, Query.FilterOperator.EQUAL, code);
	Filter codeFilter = CompositeFilterOperator.or(creditFilter, debitFilter);
	return codeFilter;
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
