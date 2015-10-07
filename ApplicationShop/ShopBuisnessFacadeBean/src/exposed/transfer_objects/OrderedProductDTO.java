package exposed.transfer_objects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OrderedProductDTO {
    private int id;
    private OrderDTO order;
    private ProductDTO product;
    private int amount;


    OrderedProductDTO() {
    }

    public OrderedProductDTO(OrderDTO order, ProductDTO product, int amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
//        result = id;
//        result = 29 * result + Integer.parseInt(
//                this.hash(product.toString()));
//        return result;
//    }
}
