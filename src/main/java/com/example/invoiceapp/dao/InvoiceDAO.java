package com.example.invoiceapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.invoiceapp.Database;
import com.example.invoiceapp.model.Customer;
import com.example.invoiceapp.model.Invoice;
import com.example.invoiceapp.model.InvoiceItem;
import com.example.invoiceapp.model.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class InvoiceDAO {
	public void addInvoice(Invoice invoice) {
		InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();
		int invoiceId;
		String query = "INSERT INTO invoices (customer_id, total_amount, status) VALUES (?, ?, ?)";
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, invoice.getCustomerId());
			pst.setInt(2, invoice.getTotalAmount());
			pst.setString(3, invoice.getStatus());
			pst.executeUpdate();
			
			try(ResultSet rs = pst.getGeneratedKeys()){
				if(rs.next()) {
					invoiceId = rs.getInt(1);
				}
				else {
					throw new SQLException("NO ID Obtained while generating Invoice ID");
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		invoiceItemDAO.addInvoiceItems(invoice.getInvoiceItems(), invoiceId);
		
	}
	public ArrayList<Invoice> getAllInvoices(){
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();
		String query = "select * from invoices";
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Invoice invoice =  new Invoice();
				invoice.setInvoiceId(rs.getInt(1));
				invoice.setCustomerId(rs.getInt(2));
				invoice.setTotalAmount(rs.getInt(3));
				invoice.setStatus(rs.getString(4));
				invoices.add(invoice);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return invoices;
	}
	public JsonObject getInvoiceById(int invoiceId) {
		JsonObject json = new JsonObject();
		CustomerDAO customerDAO = new CustomerDAO();
		InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();
		String query = "select * from invoices where invoice_id = " + invoiceId;
		Invoice invoice =  new Invoice();
		ItemDAO itemDAO = new ItemDAO();
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				invoice.setInvoiceId(rs.getInt(1));
				invoice.setCustomerId(rs.getInt(2));
				invoice.setTotalAmount(rs.getInt(3));
				invoice.setStatus(rs.getString(4));
			}
			Customer customer = customerDAO.getCustomerById(invoice.getCustomerId());
			json.addProperty("Invoice ID", invoice.getInvoiceId());
			json.addProperty("Customer ID", Integer.valueOf(customer.getCustomerId()));
			json.addProperty("Customer Name", customer.getCustomerName());
			json.addProperty("Customer Mobile Number", customer.getMobileNo());
			json.addProperty("Customer Email ID", customer.getEmailId());
			json.addProperty("Customer Address", customer.getAddress());
			ArrayList<InvoiceItem> invoiceItems =  invoiceItemDAO.getInvoiceItems(invoiceId);
			JsonArray jsonArray = new JsonArray();
			for(InvoiceItem invoiceItem : invoiceItems) {
				JsonObject items = new JsonObject();
				Item item = itemDAO.getItemById(invoiceItem.getItemId());
				items.addProperty("Item Name", item.getItemName());
				items.addProperty("Item Price", item.getItemPrice());
				items.addProperty("Quantity", invoiceItem.getQuantity());
				items.addProperty("Sub Total Price", invoiceItem.getTotalPrice());
				jsonArray.add(items);
			}
			json.add("Invoice Items", jsonArray);
			json.addProperty("Total Price",Integer.valueOf(invoice.getTotalAmount()));
			json.addProperty("Payment Status", invoice.getStatus());
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return json;
	}
	public void updatePaymentStatus(int invoiceId, String updatedStatus) {
		String queryString = "UPDATE invoices SET status = ? where invoice_id = ?";
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(queryString);
			pst.setString(1, updatedStatus);
			pst.setInt(2, invoiceId);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
}
