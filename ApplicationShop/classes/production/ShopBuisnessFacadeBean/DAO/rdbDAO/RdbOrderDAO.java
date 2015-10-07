package DAO.rdbDAO;

import DAO.*;

import java.sql.*;
import java.util.ArrayList;

import exposed.transfer_objects.*;
import DAO.rdbDAO.rdb_transfer_objects.Order;
import DAO.rdbDAO.rdb_transfer_objects.Person;
import DAO.rdbDAO.rdb_transfer_objects.Product;
import DAO.ConnectionFailedException;
import DAO.GettingDataFailedException;

/**
 * Class represents implementation of Transfer Object pattern
 * for working with concrete DB entry - Order
 */
public class RdbOrderDAO extends OrderDAO {
    private Connection conn;
    private PreparedStatement insertOrder;
    private PreparedStatement insertOrderProduct;
    private PreparedStatement selectOrders;
    private PreparedStatement stmtInner;
    private PreparedStatement innerSelect;
    private PreparedStatement selectOrder;

    /**
     * Constructs an instance of <code>RdbOrderDAO</code> object with given connection and
     * <code>Statement</code> object for Statement creation
     *
     * @param conn Connection with database
     */
    public RdbOrderDAO(Connection conn) throws GettingDataFailedException {
        this.conn = conn;
        try {
            insertOrder = conn.prepareStatement("INSERT INTO orders" +
                    "(order_date, order_status, person_id) VALUES(current_date,?,?);");
            insertOrderProduct = conn.prepareStatement("INSERT INTO ordered_products(product_id, order_id, amount)" +
                    "VALUES(?,?,?)");
            selectOrders = conn.prepareStatement("SELECT orders.order_id, orders.order_status," +
                    "per.id, per.lastname, per.firstname, orders.order_date" +
                    " FROM orders" +
                    " JOIN customers per ON orders.person_id=per.id" +
                    " ORDER BY orders.order_id DESC LIMIT 10;");
            stmtInner = conn.prepareStatement("SELECT *" +
                    " from ordered_products op " +
                    "JOIN products p ON p.product_id=op.product_id " +
                    "AND op.order_id=?");
            innerSelect = conn.prepareStatement("SELECT order_id FROM orders ORDER " +
                    "BY order_id DESC LIMIT 1");
            selectOrder = conn.prepareStatement("SELECT * FROM orders WHERE order_id=?",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GettingDataFailedException();
        }
    }

    public void changeStatus(int orderID, Status newStatus) throws GettingDataFailedException{
        try {
            selectOrder.setInt(1, orderID);
            ResultSet r = selectOrder.executeQuery();
            r.next();
            r.updateString("order_status", Status.convertToString(newStatus));
            r.updateRow();
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }

    public Order getOrder(int orderID) throws GettingDataFailedException{
        System.out.println("DAO: getting order id "+orderID);

        Order ord = null;
        try {
            selectOrder.setInt(1, orderID);
            ResultSet r = selectOrder.executeQuery();
            ord = new Order(orderID);
//            ord.setOrderDate(r.getDate("order_date"));
//            ord.setOrderID(orderID);
            r.next();
            ord.setOrderStatus(Status.getStatus(r.getString("order_status")));

            stmtInner.setInt(1, orderID);
            ResultSet rsInner = stmtInner.executeQuery();
            ArrayList products = new ArrayList();
                while (rsInner.next()) {
                    Product op = new Product(rsInner.getString("title"), rsInner.getDouble("price"), orderID);
                    op.setProductID(rsInner.getInt("product_id"));
                    op.setAmountIfOrdered(rsInner.getInt("amount"));
                    products.add(op);
                }
            ord.setOrderedProducts(products);
            rsInner.close();
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
         return ord;
    }

    /**
     * Method creates records in table orders and ordered_products in according with given person id (order client)
     * All information are generated casually.
     *
     * @return id of the order created
     * @throws TransactionRollbackedException if transaction rollbacked
     * @throws TransactionFailedException     if transaction failed
     */
    public int insertOrder(Order order)
            throws TransactionRollbackedException, TransactionFailedException {
        int orderID = 0;
//        boolean wasAutoCommit = true;
        try {
            insertOrder.setString(1, Status.convertToString(order.getOrderStatus()));
            insertOrder.setInt(2, order.getClient().getPersonID());
//            insertOrder.setDate(1,new Date(order.getOrderDate().getTime()));

//            if (conn.getAutoCommit()) {
//                conn.setAutoCommit(false);
//                wasAutoCommit = true;
//            } else {
//                wasAutoCommit = false;
//            }
            insertOrder.executeUpdate();
            ResultSet r = innerSelect.executeQuery();
            r.next();
            orderID = r.getInt("order_id");
            r.close();
//            conn.commit();
//            if (wasAutoCommit) {
//                conn.setAutoCommit(true);
//            }

        }
        catch (SQLException e) {
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
        return orderID;
    }


    /**
     * Method adds information about products and their amounts
     * belinging to order with given order id in database
     *
     * @param orderID
     * @param product
     * @param amount
     * @throws TransactionFailedException
     * @throws TransactionRollbackedException
     */
    public void addProductOrder(int orderID, Product product, int amount) throws TransactionFailedException, TransactionRollbackedException {
        boolean wasAutoCommit = true;
        try {
//            if (conn.getAutoCommit()) {
//                conn.setAutoCommit(false);
//                wasAutoCommit = true;
//            } else {
//                wasAutoCommit = false;
//            }
            insertOrderProduct.setInt(1, product.getProductID());
            insertOrderProduct.setInt(2, orderID);
            insertOrderProduct.setInt(3, amount);
            insertOrderProduct.executeUpdate();
//            conn.commit();
//            if (wasAutoCommit) {
//                conn.setAutoCommit(true);
//            }
        } catch (SQLException e) {
//            try {
            e.printStackTrace();
//                conn.rollback();
//                if (wasAutoCommit) {
//                    conn.setAutoCommit(true);
//                }
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
//                throw new TransactionRollbackedException();
//            } catch (SQLException ex) {
//                throw new TransactionFailedException();
//            }
        }
    }

    /**
     * Creates ans <code>String</code> value for information about all orders exesting.
     * Information includes order id, client lastname and firstname, date of order and
     * all products ordered with its price and amount.
     *
     * @return <code>String</code> value with information
     */

    public ArrayList getOrders() throws GettingDataFailedException {
        ArrayList orders = new ArrayList();
        try {
            ResultSet rs = selectOrders.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                Order ord = new Order(new Person(rs.getInt("id"),
                        rs.getString("firstname"), rs.getString("lastname")));
                ord.setOrderDate(rs.getDate("order_date"));
                ord.setOrderID(orderID);
                ord.setOrderStatus(Status.getStatus(rs.getString("order_status")));
                stmtInner.setInt(1, orderID);
                ResultSet rsInner = stmtInner.executeQuery();
                ArrayList products = new ArrayList();
                while (rsInner.next()) {
                    Product op = new Product(rsInner.getString("title"), rsInner.getDouble("price"), orderID);
                    op.setAmountIfOrdered(rsInner.getInt("amount"));
                    op.setProductID(rsInner.getInt("product_id"));
                    products.add(op);
                }
                ord.setOrderedProducts(products);
                rsInner.close();
                orders.add(ord);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
        return orders;
    }

    public void close() throws ConnectionFailedException {
        try {
            selectOrders.close();
            insertOrder.close();
            insertOrderProduct.close();
            selectOrder.close();
            conn.close();
        } catch (SQLException e) {
            throw new ConnectionFailedException();
        }
    }

}

