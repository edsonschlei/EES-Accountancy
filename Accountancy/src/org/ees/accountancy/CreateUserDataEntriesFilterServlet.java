package org.ees.accountancy;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.UserDataEntriesFilterBean;
import org.ees.accountancy.bean.UserDataFactory;

/**
 * Servlet id: create-user-data-entries-filter-servlet
 * 
 * @author schlei
 *
 */
@SuppressWarnings("serial")
public class CreateUserDataEntriesFilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
	UserDataEntriesFilterBean bean = UserDataFactory.getUserDataEntriesFilterBean();

	req.getSession().setAttribute("userDataEntries", bean);
	
	RequestDispatcher dispatcher = req.getRequestDispatcher("/ShowUserEntriesForm.jsp");
	dispatcher.forward(req, resp);
	
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
	this.doGet(req, resp);
    }
    
}
