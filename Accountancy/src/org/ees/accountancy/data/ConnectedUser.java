package org.ees.accountancy.data;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ConnectedUser {

    /**
     * Create the ConnectedUser entity whether the user is new.
     * @param user
     */
    public static Entity createOrUpdate() {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY); 
	Entity connectedUser = getUser();
	if (connectedUser == null) {
	    String userID = getUserID();
	    connectedUser = new Entity(Entities.CONNECTED_USERS.getTableName(), userID);
	    Util.persistEntity(connectedUser);
	}
	return connectedUser;
    }

    public static String getUserID() {
	User user = getCurrentUser();
	assert user != null;
	return MD5Util.encode(user.getEmail());
    }

    public static Entity getUser() {
	NamespaceManager.set(Constants.NS_ACCOUNTANCY); 
	String userID = getUserID();
	Key key = KeyFactory.createKey(Entities.CONNECTED_USERS.getTableName(), userID);
	return Util.findEntity(key);
    }

    public static User getCurrentUser() {
	UserService userService = UserServiceFactory.getUserService();
	return userService.getCurrentUser();
    }

}
