package DAO;

import DAO.rdbDAO.rdb_transfer_objects.Order;
import DAO.rdbDAO.rdb_transfer_objects.Product;
import DAO.GettingDataFailedException;
import DAO.ConnectionFailedException;
import DAO.TransactionFailedException;
import DAO.TransactionRollbackedException;

import java.util.ArrayList;

/**
 * Intefrace for order DAO
 */
public interface OrderDAOInterface {
    /**
     * Method inserts object <code>Order</code> in base where data stored
     * @param order
     * @return order id
     * @throws TransactionRollbackedException if transaction rollbacked
     * @throws TransactionFailedException if transaction failed
     */
    public int insertOrder(Order order) throws TransactionFailedException,
            TransactionRollbackedException;
    public abstract void addProductOrder(int orderID, Product product, int amount)
            throws TransactionFailedException, TransactionRollbackedException;

    public abstract ArrayList getOrders() throws GettingDataFailedException;

    public abstract void close() throws ConnectionFailedException;

}
