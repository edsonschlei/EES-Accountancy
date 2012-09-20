package org.ees.accountancy;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.data.ConnectedUser;
import org.ees.accountancy.data.CreateInitialAccounts;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class MainAccountancyServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	User user = ConnectedUser.getCurrentUser();

	if (user != null) {
	    ConnectedUser.createOrUpdate();
	    CreateInitialAccounts.create();
	    resp.sendRedirect("/create-user-data-entry-servlet");
	} else {
	    UserService userService = UserServiceFactory.getUserService();
	    resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
	}
    }
}
