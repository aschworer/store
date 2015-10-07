package exposed.transfer_objects;

import exposed.transfer_objects.Status;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import java.util.HashSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OrderDTO implements Serializable {
    protected int orderID;
    protected java.sql.Date orderDate ;
    protected Status orderStatus;
    protected CustomerDTO client;
    private Set<OrderedProductDTO> orderedProducts = new HashSet<OrderedProductDTO>();

    OrderDTO() {
    }

    public OrderDTO(CustomerDTO client) {
        this.client = client;
    }

    public OrderDTO(Date orderDate, Status orderStatus, CustomerDTO client, Set<OrderedProductDTO> orderedProducts) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.client = client;
        this.orderedProducts = orderedProducts;
    }


    public Set<OrderedProductDTO> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(Set<OrderedProductDTO> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public CustomerDTO getClient() {
        return client;
    }

    public void setClient(CustomerDTO client) {
        this.client = client;
    }

//    public Collection<Product> getOrderedProducts() {
//        return orderedProducts;
//    }
//
//    public void setOrderedProducts(Collection<Product> orderedProducts) {
//        this.orderedProducts = orderedProducts;
//    }

//    public void setCurrentDate(boolean currentDate) {
//        this.currentDate = currentDate;
//    }

    /**
     * Hash function.
     */
    public static String hash(String str) {

        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();

            byte[] hash = algorithm.digest(str.getBytes());
            hash = algorithm.digest(hash);

            StringBuffer buf = new StringBuffer();
            for (byte aHash : hash) {
                buf.append(Byte.toString(aHash));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return str;
        }
    }

//     public int hashCode() {
//        int result;
//        result = orderID;
//        result = 29 * result + Integer.parseInt(
//                this.hash(client.toString()));
//        return result;
//    }



    }