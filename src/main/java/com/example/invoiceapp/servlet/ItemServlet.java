package com.example.invoiceapp.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.example.invoiceapp.dao.ItemDAO;
import com.example.invoiceapp.model.Customer;
import com.example.invoiceapp.model.Item;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ItemServlet extends HttpServlet{
	ItemDAO itemDAO = new ItemDAO();
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    	String itemIdParam = req.getParameter("id");
    	String json;
    	if(itemIdParam != null) {
    		int itemId = Integer.parseInt(itemIdParam);
    		Item item = itemDAO.getItemById(itemId);
    		json = new Gson().toJson(item);
    	}
    	else {
    		ArrayList<Item> items = itemDAO.getAllItems();
            json = new Gson().toJson(items);
    		
    	}
        res.setContentType("application/json");
    	res.getWriter().write(json);
   }
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    	Item item = new Gson().fromJson(req.getReader(), Item.class);
    	itemDAO.addItem(item);
    	res.setContentType("application/json");
    	res.getWriter().write(new Gson().toJson(item));
    	
   }
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
	    int itemIdParam = Integer.parseInt(req.getParameter("id"));
	    itemDAO.deleteItemById(itemIdParam);
	    ArrayList<Item> items = itemDAO.getAllItems();
	    String json = new Gson().toJson(items);
	    res.setContentType("application/json");
	    res.getWriter().write(json);
	   }
}
