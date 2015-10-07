import javax.ejb.*;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.ResourceBundle;

import exposed.BeanDatabaseException;

/**
 * @ejb.bean name="SendEmailMDB"
 * type="message-driven"
 */

public class SendEmailMDB implements MessageDrivenBean, MessageListener {
    protected MessageDrivenContext ctx;
    private ShopBuisnessFacadeLocal databaseBean;
//    private String email;

    public static final String SHOP_BEAN_JNDI_LOCAL = "ShopBeanJNDILocal";
    public static final String PROP_ORDER_ID = "orderID";
    public static final String PROP_PRODUCT_ID = "productID";
    public static final String PROCESS_ORDER = "processOrder";
    public static final String PROP_WAREHOUSE_AMOUNT = "warehouseAmount";

    public void ejbCreate() {
        System.out.println("MDBEmail .create() invoked");

//        ResourceBundle bundle = ResourceBundle.getBundle("email-ejb-config");
//        System.out.println("Email bundled: "+ bundle.getString("datasource.name"));
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
        System.out.println("MDBEmail .remove() invoked");
        try {
            databaseBean.remove();
        } catch (RemoveException e) {
            e.printStackTrace();
        }
    }

    public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException {
        System.out.println("MDBEmail .setMessageDrivenContext() invoked");
        this.ctx = ctx;
    }

    public void onMessage(javax.jms.Message message) {
        System.out.println("MDBEmail .onMessage() invoked");
        if (message instanceof ObjectMessage) {
            System.out.println("object msg received");
            ObjectMessage tm = (ObjectMessage) message;
            sendEmail(tm);
        }
    }

    private void sendEmail(ObjectMessage objectMessage) {
        try {
            Hashtable props = (Hashtable) objectMessage.getObject();
//            System.out.println("value: "+props.get(PROCESS_ORDER));
//            if (!(Boolean) props.get(PROCESS_ORDER)) {

            int productID = Integer.parseInt((String) props.get(PROP_PRODUCT_ID));
            if (databaseBean.mailNeededHib(productID)) {
                System.out.println("MDBEmail: Send email request received. product id: " + productID);
                databaseBean.sendEmailHib(/*productID, Integer.parseInt((String) props.get(PROP_WAREHOUSE_AMOUNT))*/props);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (BeanDatabaseException e) {
            e.printStackTrace();
        }
    }
}
