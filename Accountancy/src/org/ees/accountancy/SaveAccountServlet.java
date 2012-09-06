package org.ees.accountancy;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.SavedFormBean;
import org.ees.accountancy.bean.UserDataAccountBean;
import org.ees.accountancy.bean.UserDataFactory;
import org.ees.accountancy.data.Account;

/**
 * Servlet id: /save-account-servlet
 * 
 * @author schlei
 * 
 */
@SuppressWarnings("serial")
public class SaveAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

	String code = req.getParameter("code").trim();
	String description = req.getParameter("description").trim();

	UserDataAccountBean bean = (UserDataAccountBean) req.getSession().getAttribute(
		"userDataAccount");
	if (bean == null) {
	    bean = UserDataFactory.getUserDataAccountBean();
	}
	bean.setInvalidMessages(new ArrayList<String>());

	if (code.isEmpty()) {
	    bean.setInvalidCode("Code must have a no empty code!");
	}
	if (description.isEmpty()) {
	    bean.setInvalidDescription("Description must have a no empty description!");
	}

	if (!bean.getInvalidMessages().isEmpty()) {
	    bean.setCode(code);
	    bean.setDescription(description);
	    
	    System.out.println("save-account " + bean);

	    req.getSession().setAttribute("userDataAccount", bean);

	    RequestDispatcher dispatcher = req.getRequestDispatcher("/EditAccountForm.jsp");
	    dispatcher.forward(req, resp);

	} else {
	    Account.createOrUpdate(code, description);
	    
	    SavedFormBean savedForm = new SavedFormBean();
	    savedForm.setForwardPage("create-user-data-account-servlet");
	    savedForm.setMessage("Account saved with success!");
	    req.getSession().setAttribute("savedForm", savedForm);

	    RequestDispatcher dispatcher = req.getRequestDispatcher("/SavedForm.jsp");
	    dispatcher.forward(req, resp);
	}

    }

}
