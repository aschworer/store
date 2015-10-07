package exposed.transfer_objects;

import java.sql.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReportItemDTO {
    private String customerType = "none";
//    private java.sql.Date date = new Date(System.currentTimeMillis());
    private java.util.Date date = new Date(System.currentTimeMillis());
    private long numberOfOrders= 0;
    private java.lang.Double ordersSum = new java.lang.Double(0);


    public ReportItemDTO(String customerType, Date date, long numberOfOrders, java.lang.Double ordersSum) {
        this.customerType = customerType;
        this.date = date;
        this.numberOfOrders = numberOfOrders;
        this.ordersSum = ordersSum;
    }

    public ReportItemDTO(String customerType, java.util.Date date, long numberOfOrders) {
        this.customerType = customerType;
        this.date = date;
        this.numberOfOrders = numberOfOrders;
    }

//    public ReportItemDTO(String customerType, Date date, long numberOfOrders, double ordersSum) {
//        this.customerType = customerType;
//        this.date = date;
//        this.numberOfOrders = numberOfOrders;
//        this.ordersSum = ordersSum;
//    }
//
//    public ReportItemDTO(String customerType, Date date, long numberOfOrders, long ordersSum) {
//        this.customerType = customerType;
//        this.date = date;
//        this.numberOfOrders = numberOfOrders;
//        this.ordersSum = ordersSum;
//    }
//
//    public ReportItemDTO(String customerType, Date date, long numberOfOrders, int ordersSum) {
//        this.customerType = customerType;
//        this.date = date;
//        this.numberOfOrders = numberOfOrders;
//        this.ordersSum = ordersSum;
//    }

//    public ReportItemDTO(String customerType, Date date, long numberOfOrders, long ordersSum) {
//        this.customerType = customerType;
//        this.date = date;
//        this.numberOfOrders = numberOfOrders;
//        this.ordersSum = ordersSum;
//    }
//
//
//
//    public ReportItemDTO(String customerType) {
//        this.customerType = customerType;
//    }


    public ReportItemDTO(Date date) {
        this.date = date;
    }


    public ReportItemDTO(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }


    public ReportItemDTO(Double ordersSum) {
        this.ordersSum = ordersSum;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }


    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public double getOrdersSum() {
        return ordersSum;
    }

    public void setOrdersSum(Double ordersSum) {
        this.ordersSum = ordersSum;
    }


    public String toString() {
        return customerType + " " + date.toString() + " " + numberOfOrders + " " + ordersSum;
    }

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
//        result = this.numberOfOrders;
//        result = 29 * result + Integer.parseInt(
//                this.hash(this.toString()));
//        return result;
//    }
}
