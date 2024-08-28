package com.example.invoiceapp.servlet;
import com.example.invoiceapp.model.Customer;
import com.google.gson.Gson;
import com.example.invoiceapp.dao.CustomerDAO;
import java.io.IOException;
import java.util.ArrayList;


import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomerServlet extends HttpServlet{
	private CustomerDAO customerDAO = new CustomerDAO();
	
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    	String customerIdParam = req.getParameter("id");
    	String json;
    	if(customerIdParam != null) {
    		int customerId = Integer.parseInt(customerIdParam);
    		Customer customer = customerDAO.getCustomerById(customerId);
    		json = new Gson().toJson(customer);
    	}
    	else {
    		ArrayList<Customer> customers = customerDAO.getAllCustomers();
            json = new Gson().toJson(customers);
    		
    	}
        res.setContentType("application/json");
    	res.getWriter().write(json);
   }
	

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    	Customer customer = new Gson().fromJson(req.getReader(), Customer.class);
    	customerDAO.addCustomer(customer);
    	res.setContentType("application/json");
    	res.getWriter().write(new Gson().toJson(customer));
    	
   }
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    	int customerIdParam = Integer.parseInt(req.getParameter("id"));
    	customerDAO.deleteCustomerById(customerIdParam);
    	ArrayList<Customer> customers = customerDAO.getAllCustomers();
        String json = new Gson().toJson(customers);
        res.setContentType("application/json");
    	res.getWriter().write(json);
   } 

}
