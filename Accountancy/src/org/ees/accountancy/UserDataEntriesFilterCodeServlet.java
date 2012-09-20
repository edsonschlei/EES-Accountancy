package org.ees.accountancy;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.UserDataEntriesFilteredBean;
import org.ees.accountancy.bean.UserDataFactory;

/**
 * 
 * @author schlei
 * 
 */
@SuppressWarnings("serial")
public class UserDataEntriesFilterCodeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	resp.setHeader("Cache-Control", "no-cache");
	resp.setHeader("Pragma", "no-cache");

	String parYear = req.getParameter("year");
	String parMonth = req.getParameter("month");
	String parCode = req.getParameter("code");
	
	int year = Integer.parseInt(parYear);
	int month = Integer.parseInt(parMonth);

	UserDataEntriesFilteredBean bean = UserDataFactory.getUserData(year, month, parCode);

	req.getSession().setAttribute("filtered", bean);

	String outputPage = "TableEntriesFiltered.jsp";
	RequestDispatcher dispatcher = req.getRequestDispatcher(outputPage);
	dispatcher.include(req, resp);

    }

}
