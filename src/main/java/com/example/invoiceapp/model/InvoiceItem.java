package com.example.invoiceapp.model;

public class InvoiceItem {
    private int invoiceItemId;
    private int invoiceId;
    private int itemId;
    private int quantity;
    private int totalPrice;

    public int getInvoiceItemId(){
        return invoiceItemId;
    }
    public int getInvoiceId(){
        return invoiceId;
    }
    public int getItemId(){
        return itemId;
    }
    public int getQuantity(){
        return quantity;
    }
    public int getTotalPrice(){
        return totalPrice;
    }
    public void setInvoiceItemId(int invoiceItemId){
        this.invoiceItemId = invoiceItemId;
    }
    public void setInvoiceId(int invoiceId){
        this.invoiceId = invoiceId;
    }
    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setTotalPrice(int totalPrice){
        this.totalPrice = totalPrice;
    }
}
