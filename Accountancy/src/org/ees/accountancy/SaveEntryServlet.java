package org.ees.accountancy;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.SavedFormBean;
import org.ees.accountancy.bean.UserDataEntryBean;
import org.ees.accountancy.bean.UserDataFactory;
import org.ees.accountancy.data.Entry;

@SuppressWarnings("serial")
public class SaveEntryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
	resp.setContentType("text/html");
	PrintWriter out = resp.getWriter();
	
	Map<?,?> map = req.getParameterMap();
	Set<?> keySet = map.keySet();
	for (Object key : keySet) {
	    Object value = req.getParameter(key.toString());
	    out.println(key + " - " + value + "<br>");
	}
	
	String parDate = req.getParameter("date");
	String parValue = req.getParameter("value");
	String parCredit = req.getParameter("credit");
	String parDebit = req.getParameter("debit");
	String parDescription = req.getParameter("description");
	
	UserDataEntryBean bean = (UserDataEntryBean) req.getSession().getAttribute("userDataEntry");
	if (bean == null) {
	    bean = UserDataFactory.getUserDataEntryBean();
	}
	bean.setInvalidMessages(new ArrayList<String>());
	Date date = null;
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
	    date = sdf.parse(parDate);
	} catch (ParseException e) {
	    bean.setInvalidDate(e.getLocalizedMessage());
	}
	
	try {
	    double value = Double.parseDouble(parValue);
	    if (value <= 0) {
		bean.setInvalidValue("Value must be greater than zero!");
	    }
	} catch (NumberFormatException e) {
	    bean.setInvalidValue(e.getLocalizedMessage());
	}
	
	if (parCredit.equals(parDebit)) {
	    bean.setInvalidCreditDebit("Credit and Debit must been different!");
	}

	if (!bean.getInvalidMessages().isEmpty()) {
	    bean.setDate(parDate);
	    bean.setValue(parValue);
	    bean.setCredit(parCredit);
	    bean.setDebit(parDebit);
	    bean.setDescription(parDescription);
	    
	    req.getSession().setAttribute("userDataEntry", bean);
	    System.out.println("foundError " + bean);
	    
	    RequestDispatcher dispatcher = req.getRequestDispatcher("/EditEntryForm.jsp");
	    dispatcher.forward(req, resp);
	} else {
	    String id = null;
	    double value = Double.parseDouble(parValue);
	    Entry.createOrUpdate(id, date.getTime(), value, parCredit, parDebit, parDescription, null);
	    
	    SavedFormBean savedForm = new SavedFormBean();
	    savedForm.setForwardPage("create-user-data-entry-servlet");
	    savedForm.setMessage("Entry saved with success!");
	    req.getSession().setAttribute("savedForm", savedForm);

	    RequestDispatcher dispatcher = req.getRequestDispatcher("/SavedForm.jsp");
	    dispatcher.forward(req, resp);
	}
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        this.doPost(req, resp);
    }
    
}
