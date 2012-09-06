package org.ees.accountancy;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.UserDataEntryBean;
import org.ees.accountancy.bean.UserDataFactory;


/**
 * Servlet id
 * 
 * /create-user-data-entry-servlet
 * 
 * @author schlei
 *
 */
@SuppressWarnings("serial")
public class CreateUserDataEntryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	    IOException {
	
	UserDataEntryBean bean = UserDataFactory.getUserDataEntryBean();
	req.getSession().setAttribute("userDataEntry", bean);
	
	RequestDispatcher dispatcher = req.getRequestDispatcher("/EditEntryForm.jsp");
	dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
	this.doGet(req, resp);
    }
    
}
