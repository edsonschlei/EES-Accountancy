package org.ees.accountancy;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.MonthBean;
import org.ees.accountancy.bean.UserDataEntriesFilterBean;
import org.ees.accountancy.bean.UserDataFactory;

/**
 * Servlet id: get-months-servlet
 * @author schlei
 *
 */
public class GetMonthsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
	
	String parYear = req.getParameter("year");
	
	UserDataEntriesFilterBean bean = (UserDataEntriesFilterBean) req.getSession().getAttribute("userDataEntries");
	if (bean == null) {
	    System.out.println("create new !!");
	    bean = UserDataFactory.getUserDataEntriesFilterBean();
	    req.getSession().setAttribute("userDataEntries", bean);
	}
	
	List<MonthBean> list = bean.getMonths().get(parYear);
	req.setAttribute("monthList", list);
	
	System.out.println(list);
	
	String outputPage = "GetMonthsListJSON.jsp";
	RequestDispatcher dispatcher = req.getRequestDispatcher(outputPage);
	dispatcher.include(req, resp);


    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
	doPost(req, resp);
    }
    
}
