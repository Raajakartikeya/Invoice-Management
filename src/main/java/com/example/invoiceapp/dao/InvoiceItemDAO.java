package com.example.invoiceapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.invoiceapp.Database;
import com.example.invoiceapp.model.InvoiceItem;

public class InvoiceItemDAO {
	public void addInvoiceItems(ArrayList<InvoiceItem> invoiceItems, int invoiceId) {
		String query = "INSERT INTO INVOICE_ITEMS (invoice_id, item_id, quantity, total_price) VALUES (?, ?, ?, ?)";
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			for(InvoiceItem invoiceItem: invoiceItems)
			{
				pst.setInt(1, invoiceId);
				pst.setInt(2, invoiceItem.getItemId());
				pst.setInt(3, invoiceItem.getQuantity());
				pst.setInt(4, invoiceItem.getTotalPrice());
				pst.addBatch();
			}
			pst.executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	public ArrayList<InvoiceItem> getInvoiceItems(int id) {
		String query = "select * from invoice_items where invoice_id = " + id;
		ArrayList<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				InvoiceItem invoiceItem = new InvoiceItem();
				invoiceItem.setItemId(rs.getInt(3));
				invoiceItem.setQuantity(rs.getInt(4));
				invoiceItem.setTotalPrice(rs.getInt(5));
				invoiceItems.add(invoiceItem);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
		return invoiceItems;
		
	}
}
