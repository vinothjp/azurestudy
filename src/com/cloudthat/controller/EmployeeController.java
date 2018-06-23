package com.cloudthat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.cloudthat.dbo.EmployeeDbo;
import com.cloudthat.model.EmployeeEntity;

//@WebServlet("/employeeController")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String INSERT_OR_EDIT = "/employee.jsp";
	private static String LIST_EMPLOYEE = "/listEmployee.jsp";
	private EmployeeDbo dbo;

	public EmployeeController()
			throws InvalidKeyException, IllegalArgumentException, RuntimeException, IOException, URISyntaxException {
		super();
		dbo = new EmployeeDbo();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("delete")) {
			dbo.deleteEmployee(request.getParameter("lastName"), request.getParameter("firstName"));
			forward = LIST_EMPLOYEE;
			request.setAttribute("employees", dbo.getAllEmployee());
		} else if (action.equalsIgnoreCase("listEmployee")) {
			forward = LIST_EMPLOYEE;
			request.setAttribute("employees", dbo.getAllEmployee());
		} else {
			forward = INSERT_OR_EDIT;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmployeeEntity employee = new EmployeeEntity(request.getParameter("lastName"),
				request.getParameter("firstName"));
		employee.setEmail(request.getParameter("email"));
		Part filePart = request.getPart("empPhoto");
		InputStream inputStream = filePart.getInputStream();
		// Upload the photo to blob storage and store its URI in table storage
		String profileUrl = dbo.uploadImageToBlob(inputStream, request.getParameter("firstName") + ".jpg");
		employee.setProfileURL(profileUrl);
		dbo.addEmployee(employee);
		RequestDispatcher view = request.getRequestDispatcher(LIST_EMPLOYEE);
		request.setAttribute("employees", dbo.getAllEmployee());
		view.forward(request, response);
	}
}
