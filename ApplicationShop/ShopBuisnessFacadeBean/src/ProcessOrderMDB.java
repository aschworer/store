import exposed.transfer_objects.OrderDTO;

import javax.ejb.*;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import exposed.BeanDatabaseException;

/**
 * @ejb.bean name="ProcessOrderMDB"
 * type="message-driven"
 */

public class ProcessOrderMDB implements MessageDrivenBean, MessageListener {
    protected MessageDrivenContext ctx;
    private ShopBuisnessFacadeLocal databaseBean;

    public static final String SHOP_BEAN_JNDI_LOCAL = "ShopBeanJNDILocal";
    public static final String PROP_ORDER_ID = "orderID";
    public static final String PROP_PRODUCT_ID = "productID";
    public static final String PROCESS_ORDER = "processOrder";
    public static final String PROP_WAREHOUSE_AMOUNT = "warehouseAmount";

    public void ejbCreate() {
        System.out.println("MDB .create() invoked");
        try {
            Context ctx = new InitialContext();
            ShopBuisnessFacadeLocalHome loc =
                    (ShopBuisnessFacadeLocalHome) ctx.lookup(SHOP_BEAN_JNDI_LOCAL);
            databaseBean = loc.create();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (CreateException e) {
            e.printStackTrace();
        }
    }

    public void ejbRemove() throws EJBException {
        System.out.println("MDB .remove() invoked");
        try {
            databaseBean.remove();
        } catch (RemoveException e) {
            e.printStackTrace();
        }
    }

    public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException {
        System.out.println("MDB .setMessageDrivenContext() invoked");
        this.ctx = ctx;
    }

    public void onMessage(javax.jms.Message message) {
        System.out.println("MDB .onMessage() invoked");
        if (message instanceof TextMessage) {
            System.out.println("MDB: text mesg received");
            mdbProcessOrder((TextMessage) message);
        }
    }

    private void mdbProcessOrder(TextMessage tm) {
        try {
//            Hashtable props = (Hashtable) objectMessage.getObject();
//            if (!(Boolean) props.get(PROCESS_ORDER)) {
//                int productID = Integer.parseInt((String) props.get(PROP_PRODUCT_ID));
//                System.out.println("MDB: Send email request received. product id: " + productID);
//                databaseBean.sendEmail(productID, Integer.parseInt((String) props.get(PROP_WAREHOUSE_AMOUNT)));
//            } else {
//                int orderID = Integer.parseInt((String) props.get(PROP_ORDER_ID));
//            System.out.println("Text message: " + tm.getText());
            int orderID = Integer.parseInt(tm.getText());
            System.out.println("MDB: received message. order id: " + orderID);
//            databaseBean.processOrder(orderID);
            OrderDTO processingOrder = databaseBean.processOrderHib(orderID);
            System.out.println("MDB: order processed: " + orderID);
            System.out.println("MDB: decreasing...");
//            databaseBean.decreaseProducts(processingOrder);
            databaseBean.decreaseProductsHib(processingOrder);
//            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (BeanDatabaseException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("No proper message");
        }
    }
}
