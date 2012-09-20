package org.ees.accountancy;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ees.accountancy.bean.BrowserSizeBean;

/**
 * Servlet id: browser_size_servlet
 *  
 * @author schlei
 *
 */
@SuppressWarnings("serial")
public class BrowserSizeServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(BrowserSizeServlet.class.getCanonicalName());
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

	String parHeight = req.getParameter("height");
	String parWidth = req.getParameter("width");
	
	int height = Integer.parseInt(parHeight);
	int width = Integer.parseInt(parWidth);
	
	BrowserSizeBean bean = new BrowserSizeBean();
	bean.setHeight(height);
	bean.setWidth(width);
	
	req.getSession().setAttribute("browser_size", bean);
	
	logger.log(Level.INFO, bean.toString());
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
	this.doPost(req, resp);
    }
    
}
