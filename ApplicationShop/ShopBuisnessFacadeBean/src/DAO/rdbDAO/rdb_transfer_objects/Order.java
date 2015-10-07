package DAO.rdbDAO.rdb_transfer_objects;

import exposed.transfer_objects.Status;

import java.util.Collection;
import java.io.Serializable;

public class Order implements Serializable {
    protected boolean currentDate;
    protected int orderID;
    protected java.util.Date orderDate;
    protected Status orderStatus;
    protected Person client;
    protected Collection<Product> orderedProducts;

    public Order(Person client) {
        this.client = client;
    }

    public Order(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public java.util.Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = orderDate;
    }

        public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    public Collection<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(Collection<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public void setCurrentDate(boolean currentDate) {
        this.currentDate = currentDate;
    }
}