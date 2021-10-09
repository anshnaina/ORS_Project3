package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.util.ServletUtility;

@WebServlet(name="WelcomeCtl",urlPatterns={"/WelcomeCtl"})
public class WelcomeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log=Logger.getLogger(WelcomeCtl.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug("WelcomeCtl Do Get method started");

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() 
	{
		return ORSView.WELCOME_VIEW;
	}

}
