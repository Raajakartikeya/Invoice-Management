package com.example.invoiceapp.model;

public class Item {
    private int itemId;
    private String itemName;
    private int itemPrice;

    public int getItemId(){
        return itemId;
    }
    public String getItemName(){
        return itemName;
    }
    public int getItemPrice(){
        return itemPrice;
    }
    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setItemPrice(int itemPrice){
        this.itemPrice = itemPrice;
    }


}
