package DAO;

import DAO.GettingDataFailedException;
import DAO.rdbDAO.rdb_transfer_objects.Product;
import DAO.TransactionRollbackedException;
import DAO.TransactionFailedException;

import java.util.Collection;

/**
 * Interface for productDAO
 */
public interface OrderedProductDAOInterface {
    /**
     * Method inserts object <code>Product</code> in base where data stored
     * @param orderProduct
     * @return product id
     * @throws TransactionRollbackedException if transaction rollbacked
     * @throws TransactionFailedException if transaction failed
     */
    public abstract int insertOrderedProduct(Product orderProduct) throws TransactionFailedException,
            TransactionRollbackedException;
    public abstract Collection<Product> getOrderedProducts() throws GettingDataFailedException;

    public int getWarehouseAmount(int productID) throws GettingDataFailedException;
}
