/*
 * Generated by XDoclet - Do not edit!
 */

/**
 * Local interface for ShopBuisnessFacade.
 */
public interface ShopBuisnessFacadeLocal
   extends javax.ejb.EJBLocalObject
{

   public exposed.transfer_objects.OrderDTO processOrderHib( int orderID ) throws exposed.BeanDatabaseException;

   public boolean mailNeededHib( int productID ) throws exposed.BeanDatabaseException;

   public void sendEmailHib( java.util.Hashtable props ) throws exposed.BeanDatabaseException;

   public void refillWarehouseHib( int productID,int amount ) throws exposed.BeanDatabaseException;

   public java.util.Collection getReport(  ) ;

   public java.util.Collection getOrdersDates(  ) ;

   public void decreaseProductsHib( exposed.transfer_objects.OrderDTO orderDB ) throws exposed.BeanDatabaseException;

   public void addOrderHib( java.lang.String clientID,exposed.transfer_objects.Status status,java.util.HashMap productsHashMap ) throws exposed.BeanDatabaseException;

   public void addCustomerHib( java.lang.String lastname,java.lang.String firstname,java.lang.String advicerID,java.lang.String discription,java.lang.String company ) ;

   public java.util.ArrayList getCustomersHib( int step,int page ) ;

   public java.math.BigInteger getCustomersCount(  ) ;

   public java.util.ArrayList getCustomersHib(  ) ;

   public java.util.ArrayList getOrdersHib( java.util.HashMap filter ) ;

   public java.util.ArrayList getProductsHib(  ) ;

}
