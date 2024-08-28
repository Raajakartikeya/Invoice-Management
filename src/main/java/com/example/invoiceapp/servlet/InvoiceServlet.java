package com.example.invoiceapp.servlet;

import java.io.IOException;
import java.util.ArrayList;


import com.example.invoiceapp.dao.InvoiceDAO;
import com.example.invoiceapp.dao.ItemDAO;
import com.example.invoiceapp.model.Invoice;
import com.example.invoiceapp.model.InvoiceItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InvoiceServlet extends HttpServlet{
	JsonObject data = new JsonObject();
	ItemDAO itemDAO = new ItemDAO();
	InvoiceDAO invoiceDAO= new InvoiceDAO();
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		Invoice invoice = new Invoice();
    	data = new Gson().fromJson(req.getReader(), JsonObject.class);
    	invoice.setCustomerId(data.get("customerId").getAsInt());
    	invoice.setStatus(data.get("paymentStatus").getAsString());
    	JsonArray invoiceItemsJson = data.get("invoiceItems").getAsJsonArray();
        ArrayList<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
        int totalInvoiceAmount = 0;
    	for(JsonElement invoiceItemJson : invoiceItemsJson) {
    		InvoiceItem invoiceItem = new InvoiceItem();
    		invoiceItem.setItemId(((JsonObject) invoiceItemJson).get("itemId").getAsInt());
    		invoiceItem.setQuantity(((JsonObject) invoiceItemJson).get("quantity").getAsInt());
    		invoiceItem.setTotalPrice(itemDAO.getItemPriceById(invoiceItem.getItemId()) * invoiceItem.getQuantity());
    		totalInvoiceAmount += invoiceItem.getTotalPrice();
    		invoiceItems.add(invoiceItem);
    	}
    	invoice.setTotalAmount(totalInvoiceAmount);
    	invoice.setInvoiceItems(invoiceItems);
    	
    	invoiceDAO.addInvoice(invoice);
   }
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String invoiveIdParam = req.getParameter("id");
		String json;
		if(invoiveIdParam != null) {
			int invoiceId = Integer.parseInt(invoiveIdParam);
			JsonObject jsonObject = invoiceDAO.getInvoiceById(invoiceId);
			json = new Gson().toJson(jsonObject);
		}
		else {
			ArrayList<Invoice> invoices = invoiceDAO.getAllInvoices();
			json = new Gson().toJson(invoices);
		}
        res.setContentType("application/json");
        res.getWriter().write(json);
   }
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		data = new Gson().fromJson(req.getReader(), JsonObject.class);
		int invoiceId = data.get("invoiceId").getAsInt();
		String updatedStatus = data.get("paymentStatus").getAsString();
		System.out.println(invoiceId + " " + updatedStatus);
		invoiceDAO.updatePaymentStatus(invoiceId, updatedStatus);
		JsonObject jsonObject = invoiceDAO.getInvoiceById(invoiceId);
		String json = new Gson().toJson(jsonObject);
		res.setContentType("application/json");
        res.getWriter().write(json);
		
   }
}
