package org.ees.accountancy;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.UserDataAccountBean;
import org.ees.accountancy.bean.UserDataFactory;

/**
 * Servlet id: create-user-data-account-servlet
 * @author schlei
 *
 */
@SuppressWarnings("serial")
public class CreateUserDataAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
	UserDataAccountBean bean = UserDataFactory.getUserDataAccountBean();
	
	bean.getInvalidMessages().clear();
	req.getSession().setAttribute("userDataAccount", bean);
	
	RequestDispatcher dispatcher = req.getRequestDispatcher("/EditAccountForm.jsp");
	dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
	doGet(req, resp);
    }
}
