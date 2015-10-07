package exposed.transfer_objects;

import java.io.Serializable;
/**
 * @hibernate.class
 *  table="PRODUCTS"
 *  usage="read-only"
 */
public class ProductDTO implements Serializable {
    private int productID;
    private String title;
    private int warehouseAmount;
    private Double price;
    private Long lastFill;
    private Long lastMail;

    ProductDTO() {
    }


    public ProductDTO(int productID) {
        this.productID = productID;
    }

    public ProductDTO(String title, int warehouseAmount, Double price, Long lastFill, Long lastMail) {
        this.title = title;
        this.warehouseAmount = warehouseAmount;
        this.price = price;
        this.lastFill = lastFill;
        this.lastMail = lastMail;
    }

    /**
	 * @hibernate.property
	 **/
    public Long getLastFill() {
        return lastFill;
    }

    public void setLastFill(Long lastFill) {
        this.lastFill = lastFill;
    }

    public Long getLastMail() {
        return lastMail;
    }

    public void setLastMail(Long lastMail) {
        this.lastMail = lastMail;
    }

    public int getWarehouseAmount() {
        return warehouseAmount;
    }

    public void setWarehouseAmount(int warehouseAmount) {
        this.warehouseAmount = warehouseAmount;
    }

    /**
    * @hibernate.id
     * column="product_id"
	 *  generator-class="sequence"
     **/
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
