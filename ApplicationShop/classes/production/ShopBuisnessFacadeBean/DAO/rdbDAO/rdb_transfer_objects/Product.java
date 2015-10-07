package DAO.rdbDAO.rdb_transfer_objects;

import java.io.Serializable;

public class Product implements Serializable {
    private int productID;
    private int orderID;
    private String title;
    private Double price;
    private int amountIfOrdered =0;


    public Product(int productID, String title, Double price) {
        this.productID = productID;
        this.title = title;
        this.price = price;
    }

    public Product(int productID) {
        this.productID = productID;
    }


    public Product(String title, Double price, int orderID) {
        this.title = title;
        this.price = price;
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAmountIfOrdered() {
        return amountIfOrdered;
    }

    public void setAmountIfOrdered(int amountIfOrdered) {
        this.amountIfOrdered = amountIfOrdered;
    }
}
