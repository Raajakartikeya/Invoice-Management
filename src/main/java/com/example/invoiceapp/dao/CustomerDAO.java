package com.example.invoiceapp.dao;
import java.sql.*;
import java.util.ArrayList;


import com.example.invoiceapp.Database;
import com.example.invoiceapp.model.Customer;

public class CustomerDAO {
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers= new ArrayList<Customer>();
        String query = "select * from Customers";
        try {
            Connection con = Database.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt(1));
                customer.setCustomerName(rs.getString(2));
                customer.setMobileNo(rs.getString(3));
                customer.setAddress(rs.getString(4));
                customer.setEmailId(rs.getString(5));
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    public Customer getCustomerById(int id){
        Customer customer = new Customer();
        String query = "select * from customers where customer_id = " + id;
        try{
            Connection con = Database.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                customer.setCustomerId(rs.getInt(1));
                customer.setCustomerName(rs.getString(2));
                customer.setMobileNo(rs.getString(3));
                customer.setAddress(rs.getString(4));
                customer.setEmailId(rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }
    public void addCustomer(Customer customer) {
    	String query = "INSERT INTO customers (customer_name, mobile_no, address, email) VALUES (?, ?, ?, ?)";
    	try {
			Connection con = Database.getConnection();
			PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, customer.getCustomerName());
			pst.setString(2, customer.getMobileNo());
			pst.setString(3, customer.getAddress());
			pst.setString(4, customer.getEmailId());
			pst.executeUpdate();
			
			try(ResultSet generatedKeys = pst.getGeneratedKeys()){
				if(generatedKeys.next()) {
					customer.setCustomerId(generatedKeys.getInt(1));
				}
				else {
					throw new SQLException("No ID Obtained");
				}
				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
    }
    public void deleteCustomerById(int id) {
    	String query = "DELETE FROM customers WHERE customer_id = " + id;
    	
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
}
