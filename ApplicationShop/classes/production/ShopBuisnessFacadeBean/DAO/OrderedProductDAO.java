package DAO;

import DAO.ConnectionFailedException;
import DAO.GettingDataFailedException;
import DAO.rdbDAO.rdb_transfer_objects.Product;
import DAO.TransactionFailedException;
import DAO.TransactionRollbackedException;

import java.util.Collection;

public abstract class OrderedProductDAO implements OrderedProductDAOInterface{
     public abstract int insertOrderedProduct(Product orderProduct) throws TransactionFailedException,
             TransactionRollbackedException;
     public abstract void close() throws ConnectionFailedException;

    public abstract Collection<Product> getOrderedProducts() throws GettingDataFailedException;
//    public abstract void changeWarehouseAmount(int productID, int decrementNumber)
//            throws GettingDataFailedException, WarehouseProductAmountException;

    public abstract void setWarehouseAmount(int productID, int newAmount) throws GettingDataFailedException;

    public abstract void setLastFill(int productID, long lastFill) throws GettingDataFailedException;
    public abstract void setLastMail(int productID, long lastFill) throws GettingDataFailedException;
    public abstract long getLastFill(int productID) throws GettingDataFailedException;
    public abstract long getLastMail(int productID) throws GettingDataFailedException;

}
