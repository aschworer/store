package DAO;

import DAO.TransactionRollbackedException;
import DAO.TransactionFailedException;

import java.util.ArrayList;
import java.io.Serializable;

import DAO.GettingDataFailedException;
import exposed.transfer_objects.*;
import DAO.rdbDAO.rdb_transfer_objects.Order;
import DAO.rdbDAO.rdb_transfer_objects.Product;
import DAO.ConnectionFailedException;

public abstract class OrderDAO implements OrderDAOInterface, Serializable {
    public abstract int insertOrder(Order order)
            throws TransactionFailedException, TransactionRollbackedException;
    
    public abstract void addProductOrder(int orderID, Product product, int amount)
            throws TransactionFailedException, TransactionRollbackedException;

    public abstract ArrayList getOrders() throws GettingDataFailedException;

    public abstract Order getOrder(int orderID) throws GettingDataFailedException;

    public abstract void changeStatus(int orderID, Status newStatus) throws GettingDataFailedException;

    public abstract void close() throws ConnectionFailedException;
}
