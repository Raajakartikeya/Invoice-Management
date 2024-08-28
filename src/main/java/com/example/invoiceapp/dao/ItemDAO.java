package com.example.invoiceapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.invoiceapp.Database;
import com.example.invoiceapp.model.Item;

public class ItemDAO {
	public ArrayList<Item> getAllItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		String query = "select * from items";
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
               Item item = new Item();
               item.setItemId(rs.getInt(1));
               item.setItemName(rs.getString(2));
               item.setItemPrice(rs.getInt(3));
               items.add(item);
            }
		} catch (SQLException e) {
			 throw new RuntimeException(e);
		}
		return items;
	}
	public Item getItemById(int id) {
		Item item = new Item();
		String query = "select * from items where item_id = " + id;
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
               item.setItemId(rs.getInt(1));
               item.setItemName(rs.getString(2));
               item.setItemPrice(rs.getInt(3));
            }
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return item;
	}
	public void addItem(Item item) {
		String query = "INSERT into ITEMS (item_name, item_price) VALUES (?, ?)";
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, item.getItemName());
			pst.setInt(2, item.getItemPrice());
			pst.executeUpdate();
			
			try(ResultSet generatedKeys = pst.getGeneratedKeys()){
				if(generatedKeys.next()) {
					item.setItemId(generatedKeys.getInt(1));
				}
				else {
					throw new SQLException("No ID Obtained");
				}
				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
    }
	public void deleteItemById(int id) {
    	String query = "DELETE FROM items WHERE item_id = " + id;
    	try {
    		Connection con = Database.getConnection();
    		PreparedStatement stmt = con.prepareStatement(query);
    		stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("id is " + id);
			System.out.println("Delete Query Not Executed");
			throw new RuntimeException();
		}
    	
    }
	public int getItemPriceById(int id) {
		String query = "select item_price from items where item_id = " + id;
		int itemPrice = 0;
		try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				itemPrice = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("No item id is obtained");
		}
		return itemPrice;
		
	}
	
}
