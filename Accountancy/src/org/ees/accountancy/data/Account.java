package org.ees.accountancy.data;

import java.util.List;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class Account {
    
    private static final String CODE = "code";
    private static final String DESCRIPTION = "description";
    private static final String COUNT_DEBIT = "count_debit";
    private static final String COUNT_CREDIT = "count_credit";

    public static Entity createOrUpdate(String code, String description) {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY); 
	Entity eUser = ConnectedUser.getUser();
	Entity account = getSingleAccount(code, eUser);
	if (account == null) {
	    account = new Entity(Entities.ACCOUNTS.getTableName(), eUser.getKey());
	    account.setProperty(CODE, code);
	    account.setProperty(DESCRIPTION, description);
	    account.setProperty(COUNT_CREDIT, 0);
	    account.setProperty(COUNT_DEBIT, 0);
	} else {
	    if (description != null && !"".equals(description)) {
		account.setProperty(DESCRIPTION, description);
	    }
	}
	Util.persistEntity(account);
	return account;
    }

    private static Entity getSingleAccount(String code, Entity user) {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY); 
	Query query = new Query(Entities.ACCOUNTS.getTableName());
	query.setAncestor(user.getKey());
	query.addFilter(CODE, FilterOperator.EQUAL, code);
	PreparedQuery prepare = Util.getDatastoreServiceInstance().prepare(query);
	List<Entity> list = prepare.asList(FetchOptions.Builder.withDefaults());
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    
    public static void incrementDebit(Entity entity) {
	increment(COUNT_DEBIT, entity);
    }
    
    public static void incrementCredit(Entity entity) {
	increment(COUNT_DEBIT, entity);
    }

    private static void increment(String propertyName, Entity entity) {
	Integer value = (Integer) entity.getProperty(propertyName);
	entity.setProperty(propertyName, value + 1);
	Util.persistEntity(entity);
    }

    /**
     * Return all accounts of the informed user.
     * 
     * @param user
     * @return
     */
    public static List<Entity> getAll() {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY); 
	Entity user = ConnectedUser.getUser();
	Query query = new Query(Entities.ACCOUNTS.getTableName());
	query.setAncestor(user.getKey());
	query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN, user.getKey());
	List<Entity> results = Util.getDatastoreServiceInstance().prepare(query)
		.asList(FetchOptions.Builder.withDefaults());
	return results;
    }

    public static String getDescription(Entity entity) {
	return (String) entity.getProperty(DESCRIPTION);
    }

    public static String getCode(Entity entity) {
	return (String) entity.getProperty(CODE);
    }

    public static Entity getEntity(String code) {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY); 
	Entity eUser = ConnectedUser.getUser();
	Entity account = getSingleAccount(code, eUser);
	return account;
    }
    
    
}
