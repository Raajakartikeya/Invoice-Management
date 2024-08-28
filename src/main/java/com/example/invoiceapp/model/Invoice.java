package com.example.invoiceapp.model;
import java.util.ArrayList;

public class Invoice {
    private int invoiceId;
    private int customerId;
    private int totalAmount;
    private String status;
    private ArrayList<InvoiceItem> invoiceItems;
    public int getInvoiceId(){
        return invoiceId;
    }
    public int getCustomerId(){
        return customerId;
    }
    public int getTotalAmount(){
        return totalAmount;
    }
    public String getStatus(){
        return status;
    }
    public ArrayList<InvoiceItem> getInvoiceItems(){
        return invoiceItems;
    }
    public void setInvoiceId(int invoiceId){
        this.invoiceId = invoiceId;
    }
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    public void setTotalAmount(int totalAmount){
        this.totalAmount = totalAmount;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setInvoiceItems(ArrayList<InvoiceItem> invoiceItems){
        this.invoiceItems = invoiceItems;
    }
}
