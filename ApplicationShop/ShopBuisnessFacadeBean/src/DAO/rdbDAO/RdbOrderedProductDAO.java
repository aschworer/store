package DAO.rdbDAO;

import DAO.GettingDataFailedException;
import DAO.ConnectionFailedException;
import DAO.rdbDAO.rdb_transfer_objects.Product;
import DAO.*;

import java.sql.*;
import java.util.Collection;
import java.util.ArrayList;

public class RdbOrderedProductDAO extends OrderedProductDAO {
    private Connection conn;
    private PreparedStatement insertProduct;
    private PreparedStatement selectProducts;
    private PreparedStatement selectProduct;


//    public Product getProduct() {
//        org.hibernate.Session session = HibernateUtil.getSession();
//        System.out.println("SBFB: HIBERNATE SESSION GET");
//        System.out.println("SBFB: HIBERNATE TRYING TO GET TEST (ID=1)");
//        return (Product) session.load(Product.class, new Long(1));
//    }


    public RdbOrderedProductDAO(Connection conn) throws GettingDataFailedException {
        this.conn = conn;
        try {
            insertProduct = conn.prepareStatement("INSERT INTO products (title, price) VALUES (?,?)",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            selectProducts = conn.prepareStatement("SELECT * FROM products");
            selectProduct = conn.prepareStatement("SELECT * FROM products WHERE product_id=?",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            throw new GettingDataFailedException();
        }
    }

    public void setLastFill(int productID, long lastFill) throws GettingDataFailedException {
        try {
            selectProduct.setInt(1, productID);
            ResultSet r = selectProduct.executeQuery();
            r.next();
            r.updateLong("last_fill", lastFill);
            r.updateRow();
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }

    public void setLastMail(int productID, long lastMail) throws GettingDataFailedException {
        try {
            System.out.println("Setting last mail.. product id=" + productID);
            System.out.println("Setting last mail.. date=" + lastMail);
            selectProduct.setInt(1, productID);
            ResultSet r = selectProduct.executeQuery();
            r.next();
            r.updateLong("last_mail", lastMail);
            r.updateRow();
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }


    public long getLastFill(int productID) throws GettingDataFailedException {
        try {
            selectProduct.setInt(1, productID);
            ResultSet r = selectProduct.executeQuery();
            r.next();
            return r.getLong("last_fill");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }

    public long getLastMail(int productID) throws GettingDataFailedException {
        try {
            selectProduct.setInt(1, productID);
            ResultSet r = selectProduct.executeQuery();
            r.next();
            return r.getLong("last_mail");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }


    public int getWarehouseAmount(int productID) throws GettingDataFailedException {
        try {
            selectProduct.setInt(1, productID);
            ResultSet r = selectProduct.executeQuery();
            r.next();
            return r.getInt("warehouse_amount");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }

    public void setWarehouseAmount(int productID, int newAmount) throws GettingDataFailedException {
        try {
            selectProduct.setInt(1, productID);
            ResultSet r = selectProduct.executeQuery();
            r.next();
            r.updateInt("warehouse_amount", newAmount);
            r.updateRow();
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }

//    public void changeWarehouseAmount(int productID, int decrementNumber)
//            throws GettingDataFailedException, WarehouseProductAmountException {
//        try {
//            selectProduct.setInt(1, productID);
//            ResultSet r = selectProduct.executeQuery();
//            r.next();
//            int amountDB = r.getInt("warehouse_amount");
//
//            System.err.println("Product id " + productID + "amount "
//                    + amountDB + "decrement " + decrementNumber);
//
//            if ((amountDB - decrementNumber) <= 0) {
//                System.err.println("Throwing Earehouse amount <0 " + (amountDB - decrementNumber));
////
////   r.updateInt("warehouse_amount", 0);
//                throw new WarehouseProductAmountException();
//            } else {
//                r.updateInt("warehouse_amount", amountDB - decrementNumber);
//            }
//            r.updateRow();
//            r.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("\n---SQLException caught---\n" + e.getMessage());
//            throw new GettingDataFailedException();
//        }
//    }

    /**
     * Method inserts information about product with given description in database
     *
     * @param orderProduct
     * @return id of inserted product
     * @throws TransactionRollbackedException
     * @throws TransactionFailedException
     */
    public int insertOrderedProduct(Product orderProduct) throws TransactionRollbackedException,
            TransactionFailedException {
        Integer id = 0;
        try {
            insertProduct.setString(1, orderProduct.getTitle());
            insertProduct.setDouble(2, orderProduct.getPrice());
//            if (conn.getAutoCommit()) {
//                conn.setAutoCommit(false);
//                wasAutoCommit = true;
//            } else {
//                wasAutoCommit = false;
//            }
            insertProduct.executeUpdate();
            ResultSet r = insertProduct.executeQuery("SELECT * FROM products ORDER BY product_id DESC LIMIT 1");
            r.next();
            id = r.getInt("product_id");
//            conn.commit();
//            if (wasAutoCommit) {
//                conn.setAutoCommit(true);
//            }
            r.close();
        } catch (SQLException e) {
//            try {
//                conn.rollback();
//                if (wasAutoCommit) {
//                    conn.setAutoCommit(true);
//                }
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
//                throw new TransactionRollbackedException();
//            } catch (SQLException ex) {
//                throw new TransactionFailedException();
//            }
        }
        return id;
    }


    public Collection<Product> getOrderedProducts() throws GettingDataFailedException {
        ArrayList products = new ArrayList();
        try {
            ResultSet rs = selectProducts.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("product_id");
                products.add(new Product(productID, rs.getString("title"), rs.getDouble("price")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
        return products;
    }

    public void close() throws ConnectionFailedException {
        try {
            insertProduct.close();
            conn.close();
        } catch (SQLException e) {
            throw new ConnectionFailedException();
        }
    }
}
