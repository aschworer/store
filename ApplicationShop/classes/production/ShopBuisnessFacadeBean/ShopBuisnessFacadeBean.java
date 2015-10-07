import DAO.rdb_DAO_hibernate.HibernatePersonDAO;
import DAO.rdb_DAO_hibernate.HibernateOrderedProductDAO;
import DAO.rdb_DAO_hibernate.HibernateOrderDAO;
import DAO.rdb_DAO_hibernate.util.HibernateUtil;
import DAO.*;

import javax.ejb.SessionBean;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.jms.*;
import javax.jms.Queue;
import java.util.*;
import java.math.BigInteger;

import exposed.BeanDatabaseException;
import exposed.transfer_objects.*;

/**
 * @ejb.bean name="ShopBuisnessFacade"
 * type="Stateless"
 * transaction-type="Container"
 */
public class ShopBuisnessFacadeBean implements SessionBean {
    public static final String SHOP_DATASOURCE_JNDI = "ShopDataSource";
    public static final String SHOP_CONNECTION_FACTORY_JNDI = "ShopConnectionFactoryJNDI";
    public static final String QUEUE_JNDI = "shop";
    public static final String MAIL_QUEUE_JNDI = "mail";
    public static final String SHOP_MAIL_SESSION_JNDI = "ShopMailSessionJNDI";
    public static final String PROP_ORDER_ID = "orderID";
    public static final String PROP_PRODUCT_ID = "productID";
    public static final String PROP_WAREHOUSE_AMOUNT = "warehouseAmount";
    public static final String WAREHOUSE_EMAIL = "Aigul.Zainullina@school.starsoftlabs.com";

    //    private DAOFactory mysqlfactory;
    //    private DAOFactory hibernateFactory;
    private SessionContext ctx;
    private QueueSession session;
    private QueueSender sender;
    private QueueSender senderMail;
    private Session mailSession;

    //------------------------------HYBERNATE methods------------------------------

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public OrderDTO processOrderHib(int orderID) throws BeanDatabaseException {
        System.out.println("SBF: processing order: id " + orderID);

        OrderDTO orderDB;
        HibernateOrderDAO orderDao = new HibernateOrderDAO(HibernateUtil.getSession());
        orderDB = orderDao.getOrder(orderID);

        if (null == orderDB) {
            System.err.println("Order not found");
            throw new BeanDatabaseException();
        } else {
            if (!(orderDB.getOrderStatus().equals(Status.NEW) ||
                    orderDB.getOrderStatus().equals(Status.IN_PACK))) {
                System.out.println("not proper status");
            } else {
                orderDB.setOrderStatus(Status.IN_PACK);
                System.out.println("success. order id " + orderDB.getOrderID() + " status in_pack");
                orderDao.save(orderDB);
            }
        }
        return orderDB;
    }


    private void requestToEmailHib(int warehouseAmount, int productID) throws BeanDatabaseException {
        try {
            if (mailNeededHib(productID)) {
                System.out.println("SBF: request to email received. product id=" + productID);
                ObjectMessage message = session.createObjectMessage();
                Hashtable order = new Hashtable();
//                System.out.println("PROPS: " + String.valueOf(productID)
//                        + String.valueOf(warehouseAmount) + warehouseEmail);
                order.put(PROP_PRODUCT_ID, String.valueOf(productID));
//                order.put(PROCESS_ORDER, false);
                order.put(PROP_WAREHOUSE_AMOUNT, String.valueOf(warehouseAmount));
//                order.put("warehouseEmail", warehouseEmail);
                message.setObject(order);
                senderMail.send(message);
                //System.out.println("---------product requested id: " + productID);
            }
        } catch (JMSException e) {
            System.err.println("JMSException");
            e.printStackTrace();
            throw new BeanDatabaseException();
//        } catch (GettingDataFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
        }

    }

    private int countNeededProductsHib(int productID) throws GettingDataFailedException, ConnectionFailedException {
        HibernateOrderDAO hDAO = new HibernateOrderDAO(HibernateUtil.getSession());
        ArrayList orders = (ArrayList) hDAO.getOrders(new HashMap());
        Iterator ord = orders.iterator();
        int neededNumber = 0;
        while (ord.hasNext()) {
            OrderDTO order = (OrderDTO) ord.next();
            if (order.getOrderStatus() == Status.IN_PACK || order.getOrderStatus() == Status.NEW) {

                Iterator pro = order.getOrderedProducts().iterator();
                while (pro.hasNext()) {
                    OrderedProductDTO oProduct = (OrderedProductDTO) pro.next();
                    if (oProduct.getProduct().getProductID() == productID) {
                        neededNumber += oProduct.getAmount();
                    }
                }
            }
        }
        return neededNumber;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public boolean mailNeededHib(int productID) throws BeanDatabaseException{
        HibernateOrderedProductDAO hibernateOrderedProductDAO = new HibernateOrderedProductDAO(HibernateUtil.getSession());
        ProductDTO product = hibernateOrderedProductDAO.getProduct(productID);
        long lastFill;
        try {
            lastFill = product.getLastFill();
        } catch (NullPointerException e) {
            lastFill = 0;
        }
        long lastMail;
        try {
            lastMail = product.getLastMail();
        } catch (NullPointerException e) {
            lastMail = 0;
        }
//        System.out.println("last fill: " + lastFill);
//        System.out.println("last mail: " + lastMail);
        if (lastFill > lastMail) {
            System.out.println("Mail needed");
            return true;
        }
        System.out.println("Mail no needed");
        return false;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public void sendEmailHib(/*int productID, int warehouseAmount*/Hashtable props) throws BeanDatabaseException {
        int productID = Integer.parseInt((String) props.get(PROP_PRODUCT_ID));
        int warehouseAmount = Integer.parseInt((String) props.get(PROP_WAREHOUSE_AMOUNT));
        System.out.println("ShopBean: sendEmail invoked." + productID/*+" amount: "+warehouseAmount*/);
        try {
            int needed = countNeededProductsHib(productID);
            StringBuffer messageBody = new StringBuffer();
            messageBody.
                    append("needed products:\n").
                    append("---------------------------------\n").
                    append("products id: " + productID + "\n").
                    append("warehouse amount: " + warehouseAmount + "\n").
                    append("needed: " + needed + "\n").
                    append("---------------------------------\n");

            //String to = warehouseEmail;
//            String to = (String) props.get("warehouseEmail");

            Message msg = new MimeMessage(mailSession);
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(WAREHOUSE_EMAIL, false));
            msg.setSubject("Needed product");
            msg.setSentDate(new Date());
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(messageBody.toString());

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp);
            msg.setContent(mp);
            Transport.send(msg);
            System.out.println("Message sent "+ System.currentTimeMillis());

            HibernateOrderedProductDAO hop = new HibernateOrderedProductDAO(HibernateUtil.getSession());
            ProductDTO p = hop.getProduct(productID);
            p.setLastMail(System.currentTimeMillis());
            hop.update(p);

//            OrderedProductDAO opDAO = mysqlfactory.getOrderProductDAO();
//            opDAO.setLastMail(productID, System.currentTimeMillis());
//            opDAO.close();

        } catch (NullPointerException e) {
            System.err.println("Couldn't send mail");
            e.printStackTrace();
        } catch (MessagingException e) {
            System.err.println("Couldn't send mail");
            e.printStackTrace();
        } catch (ConnectionFailedException e) {
            System.err.println("Couldn't send mail");
            e.printStackTrace();
        } catch (GettingDataFailedException e) {
            System.err.println("Couldn't send mail");
            e.printStackTrace();
        }
    }

    //------------------------------rdbDAO methods------------------------------

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public void refillWarehouseHib(int productID, int amount) throws BeanDatabaseException {
//        try {
        HibernateOrderedProductDAO opDAO = new HibernateOrderedProductDAO(HibernateUtil.getSession());
        ProductDTO p = opDAO.getProduct(productID);
        p.setWarehouseAmount(p.getWarehouseAmount() + amount);
        p.setLastFill(System.currentTimeMillis());
        opDAO.update(p);
//            setWarehouseAmount(productID, opDAO.getWarehouseAmount(productID) + amount);
//            opDAO.setLastFill(productID, System.currentTimeMillis());
//            opDAO.close();

        HibernateOrderDAO hod = new HibernateOrderDAO(HibernateUtil.getSession());
        ArrayList<OrderDTO> orders = (ArrayList<OrderDTO>) hod.getOrders(new HashMap());

//            OrderDAO orderDAO = mysqlfactory.getOrderDAO();
//            ArrayList orders = orderDAO.getOrders();
        Iterator ord = orders.iterator();
        while (ord.hasNext()) {
            OrderDTO order = (OrderDTO) ord.next();
            if (order.getOrderStatus() == Status.IN_PACK || order.getOrderStatus() == Status.NEW) {
                Iterator pro = order.getOrderedProducts().iterator();
                while (pro.hasNext()) {
                    OrderedProductDTO product = (OrderedProductDTO) pro.next();
                    if (product.getProduct().getProductID() == productID) {
                        System.out.println("Request to process order, id=" + order.getOrderID());
                        requestToProcess(order.getOrderID());
                    }
                }
            }
        }
//            orderDAO.close();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        }
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public Collection<ReportItemDTO> getReport() {
        HibernatePersonDAO perDAO = new HibernatePersonDAO(HibernateUtil.getSession());
        return perDAO.getReport();
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public Collection<Date> getOrdersDates() {
        HibernateOrderDAO oDAO = new HibernateOrderDAO(HibernateUtil.getSession());
        return oDAO.getDates();
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public void decreaseProductsHib(OrderDTO orderDB) throws BeanDatabaseException {
//        try {
        HibernateOrderDAO h = new HibernateOrderDAO(HibernateUtil.getSession());
        orderDB = h.getOrder(orderDB.getOrderID());
        Set oProducts = orderDB.getOrderedProducts();
//                    System.out.println(oProducts.size());
        Iterator products = oProducts.iterator();
        HibernateOrderedProductDAO productDAO = new HibernateOrderedProductDAO(HibernateUtil.getSession());
        boolean amountError = false;
        while (products.hasNext()) {
            OrderedProductDTO pro = (OrderedProductDTO) products.next();
            if (pro.getAmount() > 0) {
                int warehouseAmount = productDAO.getProduct
                        (pro.getProduct().getProductID()).getWarehouseAmount();
                if (warehouseAmount < pro.getAmount()) {
                    //----------------------------
                    //sendEmail(warehouseAmount, pro.getProductID());
                    System.out.println("sending request to email. product id=" + pro.getProduct().getProductID());
                    requestToEmailHib(warehouseAmount, pro.getProduct().getProductID());
                    //----------------------------
                    amountError = true;
                } else {
                    ProductDTO product = /*pro.getProduct();*/
                            productDAO.getProduct(pro.getProduct().getProductID());
                    product.setWarehouseAmount(warehouseAmount - pro.getAmount());
                    productDAO.update(product);
                }
            }
        }
        if (!amountError) {
            HibernateOrderDAO hod = new HibernateOrderDAO(HibernateUtil.getSession());
            OrderDTO ordNew = hod.getOrder(orderDB.getOrderID());
            ordNew.setOrderStatus(Status.DELIVERING);
            HibernateOrderDAO ordDAO = new HibernateOrderDAO(HibernateUtil.getSession());
            ordDAO.update(ordNew);
            System.out.println("success. order id " + orderDB.getOrderID() + " delivering");
        } else {
            ctx.setRollbackOnly();
        }

//        } catch (ConnectionFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        } catch (GettingDataFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        }
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public void addOrderHib(String clientID, Status status, HashMap productsHashMap)
            throws BeanDatabaseException {
//        HibernatePersonDAO pDAO = new HibernatePersonDAO();
        OrderDTO newOrder = new OrderDTO(new CustomerDTO(Integer.parseInt(clientID)));
        newOrder.setOrderStatus(status);
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        newOrder.setOrderDate(currentDate);

//        Set set = new HashSet();
//        Set ids = productsHashMap.keySet();
//        Iterator itIDS = ids.iterator();
//        while (itIDS.hasNext()) {
//            String id = String.valueOf(itIDS.next());
//            String am = (String) productsHashMap.get(id);
//                        System.out.println("id: "+id);
//                        System.out.println("am: "+am);
//            set.
//            op.add(new OrderedProductDTO(newOrder, new ProductDTO(Integer.parseInt(id)),
//                    Integer.parseInt(am)));
//        }

        ArrayList op = new ArrayList();
        Set ids = productsHashMap.keySet();
        Iterator itIDS = ids.iterator();
        while (itIDS.hasNext()) {
            String id = String.valueOf(itIDS.next());
            String am = (String) productsHashMap.get(id);
//            System.out.println("id: " + id);
//            System.out.println("am: " + am);
            op.add(new OrderedProductDTO(newOrder, new ProductDTO(Integer.parseInt(id)),
                    Integer.parseInt(am)));
        }
        Set orderedProducts = new HashSet(op);
        newOrder.setOrderedProducts(orderedProducts);

//        System.out.println("---------------order inserting:---------------");
//        System.out.println(newOrder.getClient());
//        System.out.println(newOrder.getOrderDate());
//        System.out.println(newOrder.getOrderStatus());

//        System.out.println("---------------set:---------------");
        Iterator setI = newOrder.getOrderedProducts().iterator();
        while (setI.hasNext()) {
            OrderedProductDTO opd = (OrderedProductDTO) setI.next();
            System.out.println(opd.getProduct().getProductID());
            System.out.println(opd.getAmount());
        }
HibernateOrderDAO ordDAO = new HibernateOrderDAO(HibernateUtil.getSession());
        int id = ordDAO.save(newOrder);
        requestToProcess(id);
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public void addCustomerHib(String lastname, String firstname, String advicerID,
                               String discription, String company) {
        HibernatePersonDAO perDAO = new HibernatePersonDAO(HibernateUtil.getSession());
        CustomerDTO cus;

        if ("".equals(discription)) {
            discription = "--";
        }

        if (!"0".equals(advicerID)) {
            CustomerDTO advicer = perDAO.getCustomer(Integer.parseInt(advicerID));
            cus = new PersonalCustomerDTO(advicer, lastname, firstname, discription);
            if (company == null || "".equals(company)) {
                cus = new PersonalCustomerDTO(advicer, lastname, firstname, discription);
            } else {
                cus = new BuisnessCustomerDTO(advicer, lastname, firstname, discription, company);
            }
        } else {
            if (company == null || "".equals(company)) {
                cus = new PersonalCustomerDTO(lastname, firstname, discription);
            } else {
                cus = new BuisnessCustomerDTO(lastname, firstname, discription, company);
            }
        }

        CustomerDTO advicer = perDAO.getCustomer(Integer.parseInt(advicerID));

        perDAO.save(cus);
    }

//    public void addClientHib(String lastname, String firstname, String advicerID) {
//        HibernatePersonDAO perDAO = new HibernatePersonDAO();
//        PersonDTO newPerson;
//        if (!"0".equals(advicerID)) {
//            PersonDTO advicer = perDAO.getPerson(Integer.parseInt(advicerID));
//            newPerson = new PersonDTO(advicer, lastname, firstname);
//        } else {
//            newPerson = new PersonDTO(lastname, firstname);
//        }
//        perDAO.save(newPerson);
//    }

//    public ArrayList<PersonDTO> getPersonsHib() {
//        HibernatePersonDAO perDAO = new HibernatePersonDAO();
//        return (ArrayList<PersonDTO>) perDAO.getAllPersons();

    //    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public ArrayList<CustomerDTO> getCustomersHib(int step, int page) {
        HibernatePersonDAO perDAO = new HibernatePersonDAO(HibernateUtil.getSession());
        return (ArrayList<CustomerDTO>) perDAO.getCustomers(step, page);
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public BigInteger getCustomersCount() {
        HibernatePersonDAO perDAO = new HibernatePersonDAO(HibernateUtil.getSession());
        return perDAO.countCustomers();
    }


    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public ArrayList<CustomerDTO> getCustomersHib() {
        HibernatePersonDAO perDAO = new HibernatePersonDAO(HibernateUtil.getSession());
        return (ArrayList<CustomerDTO>) perDAO.getAllCustomers();
    }

//    public ArrayList<CustomerDTO> getCustomersHibPage(int step, int page) {
//        HibernatePersonDAO perDAO = new HibernatePersonDAO(HibernateUtil.getSession());
//        return (ArrayList<CustomerDTO>) perDAO.getCustomers(step, page);
//    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public ArrayList<OrderDTO> getOrdersHib(HashMap filter) {
        HibernateOrderDAO ordDAO = new HibernateOrderDAO(HibernateUtil.getSession());
        return (ArrayList<OrderDTO>) ordDAO.getOrders(filter);
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    public ArrayList<ProductDTO> getProductsHib() {
        HibernateOrderedProductDAO proDAO = new HibernateOrderedProductDAO(HibernateUtil.getSession());
        return (ArrayList<ProductDTO>) proDAO.getProducts();
    }

    //------------------------------GENERAL methods------------------------------

    private void requestToProcess(int orderID) throws BeanDatabaseException {
        try {
            TextMessage message = session.createTextMessage();
//            Hashtable order = new Hashtable();
//            order.put(PROP_ORDER_ID, String.valueOf(orderID));
//            order.put(PROCESS_ORDER, true);
            message.setText(String.valueOf(orderID));
            System.out.println("SBFB: Request to process: id: " + orderID);
            sender.send(message);
        } catch (JMSException e) {
            System.err.println("JMSException");
            e.printStackTrace();
            throw new BeanDatabaseException();
        }
    }

    //------------------------------OLD RDB methods------------------------------

//    private void requestToEmail(int warehouseAmount, int productID) throws BeanDatabaseException {
//        try {
//            if (mailNeededHib(productID)) {
//                System.out.println("SBF: request to email received. product id=" + productID);
//                ObjectMessage message = session.createObjectMessage();
//                Hashtable order = new Hashtable();
//                order.put(PROP_PRODUCT_ID, String.valueOf(productID));
//                order.put(PROCESS_ORDER, false);
//                order.put(PROP_WAREHOUSE_AMOUNT, String.valueOf(warehouseAmount));
//                message.setObject(order);
//                senderMail.send(message);
//            }
//        } catch (JMSException e) {
//            System.err.println("JMSException");
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        } catch (GettingDataFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        }
//
//    }

//    private int countNeededProducts(int productID) throws GettingDataFailedException, ConnectionFailedException {
//        OrderDAO orderDAO = mysqlfactory.getOrderDAO();
//        ArrayList orders = orderDAO.getOrders();
//        Iterator ord = orders.iterator();
//        int neededNumber = 0;
//        while (ord.hasNext()) {
//            Order order = (Order) ord.next();
//            if (order.getOrderStatus() == Status.IN_PACK || order.getOrderStatus() == Status.NEW) {
//                ArrayList products = (ArrayList) order.getOrderedProducts();
//                Iterator pro = products.iterator();
//                while (pro.hasNext()) {
//                    Product product = (Product) pro.next();
//                    if (product.getProductID() == productID) {
//                        neededNumber += product.getAmountIfOrdered();
//                    }
//                }
//            }
//        }
//        orderDAO.close();
//        return neededNumber;
//    }

//    private boolean mailNeeded(int productID) throws GettingDataFailedException, ConnectionFailedException {
//        OrderedProductDAO opDAO = mysqlfactory.getOrderProductDAO();
//        long lastFill = opDAO.getLastFill(productID);
//        long lastMail = opDAO.getLastMail(productID);
//        opDAO.close();
//        if (lastFill > lastMail) {
//            System.out.println("Mail needed");
//            return true;
//        }
//        System.out.println("Mail no needed");
//        return false;
//    }

//    public void sendEmail(/*int productID, int warehouseAmount*/Hashtable props) throws BeanDatabaseException {
//        int productID = Integer.parseInt((String) props.get(PROP_PRODUCT_ID));
//        int warehouseAmount = Integer.parseInt((String) props.get(PROP_WAREHOUSE_AMOUNT));
//        System.out.println("ShopBean: sendEmail invoked." + productID/*+" amount: "+warehouseAmount*/);
//        try {
//            int needed = countNeededProducts(productID);
//            StringBuffer messageBody = new StringBuffer();
//            messageBody.
//                    append("needed products:\n").
//                    append("---------------------------------\n").
//                    append("products id: " + productID + "\n").
//                    append("warehouse amount: " + warehouseAmount + "\n").
//                    append("needed: " + needed + "\n").
//                    append("---------------------------------\n");
//            Message msg = new MimeMessage(mailSession);
//            msg.setFrom();
//            msg.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(WAREHOUSE_EMAIL, false));
//            msg.setSubject("Needed product");
//            msg.setSentDate(new Date());
//            MimeBodyPart mbp = new MimeBodyPart();
//            mbp.setText(messageBody.toString());
//
//            Multipart mp = new MimeMultipart();
//            mp.addBodyPart(mbp);
//            msg.setContent(mp);
//            Transport.send(msg);
//
//            OrderedProductDAO opDAO = mysqlfactory.getOrderProductDAO();
//            opDAO.setLastMail(productID, System.currentTimeMillis());
//            opDAO.close();
//
//        } catch (NullPointerException e) {
//            System.err.println("Couldn't send mail");
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            System.err.println("Couldn't send mail");
//            e.printStackTrace();
//        } catch (ConnectionFailedException e) {
//            System.err.println("Couldn't send mail");
//            e.printStackTrace();
//        } catch (GettingDataFailedException e) {
//            System.err.println("Couldn't send mail");
//            e.printStackTrace();
//        }
//    }

//    public void refillWarehouse(int productID, int amount) throws BeanDatabaseException {
//        try {
//            OrderedProductDAO opDAO = mysqlfactory.getOrderProductDAO();
//            opDAO.setWarehouseAmount(productID, opDAO.getWarehouseAmount(productID) + amount);
//            opDAO.setLastFill(productID, System.currentTimeMillis());
//            opDAO.close();
//
//            OrderDAO orderDAO = mysqlfactory.getOrderDAO();
//            ArrayList orders = orderDAO.getOrders();
//            Iterator ord = orders.iterator();
//            while (ord.hasNext()) {
//                Order order = (Order) ord.next();
//                if (order.getOrderStatus() == Status.IN_PACK || order.getOrderStatus() == Status.NEW) {
//                    ArrayList products = (ArrayList) order.getOrderedProducts();
//                    Iterator pro = products.iterator();
//                    while (pro.hasNext()) {
//                        Product product = (Product) pro.next();
//                        if (product.getProductID() == productID) {
//                            System.out.println("Request to process order, id=" + order.getOrderID());
//                            requestToProcess(order.getOrderID());
//                        }
//                    }
//                }
//            }
//            orderDAO.close();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        }
//    }
//

//    public void decreaseProducts(Order orderDB) throws BeanDatabaseException {
//        try {
//            ArrayList oProducts = (ArrayList) orderDB.getOrderedProducts();
//            Iterator products = oProducts.iterator();
//            OrderedProductDAO productDAO = mysqlfactory.getOrderProductDAO();
//            boolean amountError = false;
//            while (products.hasNext()) {
//                Product pro = (Product) products.next();
//                if (pro.getAmountIfOrdered() > 0) {
//                    int warehouseAmount = productDAO.getWarehouseAmount(pro.getProductID());
//                    if (warehouseAmount < pro.getAmountIfOrdered()) {
//                        System.out.println("sending request to email. product id=" + pro.getProductID());
//                        requestToEmail(warehouseAmount, pro.getProductID());
//                        amountError = true;
//                    } else {
//                        productDAO.setWarehouseAmount(pro.getProductID(),
//                                warehouseAmount - pro.getAmountIfOrdered());
//                    }
//                }
//            }
//            productDAO.close();
//            if (!amountError) {
//                OrderDAO ordDAO = mysqlfactory.getOrderDAO();
//                ordDAO.changeStatus(orderDB.getOrderID(), Status.DELIVERING);
//                ordDAO.close();
//                System.out.println("success. order id " + orderDB.getOrderID() + " delivering");
//            } else {
//                ctx.setRollbackOnly();
//            }
//
//        } catch (ConnectionFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        } catch (GettingDataFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        }
//    }

//    public Order processOrder(int orderID) throws BeanDatabaseException {
//        System.out.println("SBF: processing order: id " + orderID);
//
//        Order orderDB;
//        try {
//            OrderDAO orderDao = mysqlfactory.getOrderDAO();
//            orderDB = orderDao.getOrder(orderID);
//
//            if (null == orderDB) {
//                System.err.println("Order not found");
//                throw new BeanDatabaseException();
//            } else {
//                if (!(Status.convertToString(orderDB.getOrderStatus()).equals("new") ||
//                        Status.convertToString(orderDB.getOrderStatus()).equals("in_pack"))) {
//                    System.out.println("not proper status");
//                } else {
//                    orderDao.changeStatus(orderDB.getOrderID(), Status.IN_PACK);
//                    System.out.println("success. order id " + orderDB.getOrderID() + " status in_pack");
//                    orderDao.close();
//                }
//            }
//            orderDao.close();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        }
//        return orderDB;
//    }

//    public void validateClientData(HttpServletRequest request) throws IllegalParamsException {
//        String lastname = request.getParameter(LASTNAME_PARAMETER);
//        String firstname = request.getParameter(FIRSTNAME_PARAMETER);
//        if ("".equals(lastname) || "".equals(firstname) ||
//                !isStringAllowed(lastname) || !isStringAllowed(firstname)) {
//            throw new IllegalParamsException();
//        }
//    }

//    public Collection<Product> getProducts()
//            throws BeanDatabaseException {
//        Collection<Product> products = new ArrayList();
//        try {
//            if (null == mysqlfactory) {
//                throw new EJBException("Initialize Servlet first");
//            }
//            OrderedProductDAO opDAO = mysqlfactory.getOrderProductDAO();
//            products = opDAO.getOrderedProducts();
//            opDAO.close();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        }
//        return products;
//    }

//    public Collection<Person> getPersons()
//            throws BeanDatabaseException {
//        ArrayList persons = new ArrayList();
//        try {
//            PersonDAO perDAO = mysqlfactory.getPersonDAO();
//            perDAO.getPersonsAndAdvicers(persons);
//            perDAO.close();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        }
//        return persons;
//    }

//    public Collection<Order> getOrders() throws BeanDatabaseException {
//        ArrayList<Order> orders;
//        try {
//            OrderDAO ordDAO = mysqlfactory.getOrderDAO();
//            orders = ordDAO.getOrders();
//            ordDAO.close();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        }
//        return orders;
//    }

//___________RDB!_____
//    public Collection<OrderDTO> getOrders() throws BeanDatabaseException {
//        ArrayList<OrderDTO> orders = new ArrayList<OrderDTO>();
//        try {
//            OrderDAO ordDAO = mysqlfactory.getOrderDAO();
//            Iterator i = ordDAO.getOrders().iterator();
//            while (i.hasNext()) {
//                Set<OrderedProductDTO> orderedProducts = new HashSet<OrderedProductDTO>();
//                Order order = (Order) i.next();
//                OrderDTO orderDTO = new OrderDTO(new java.sql.Date(order.getOrderDate().getTime()),
//                        order.getOrderStatus(), new CustomerDTO(order.getClient().getLastname(), order.getClient().getFirstname()));
//                orderDTO.setOrderID(order.getOrderID());
//                Iterator it = order.getOrderedProducts().iterator();
//                while (it.hasNext()) {
//                    Product pro = (Product) it.next();
//                    orderedProducts.add(new OrderedProductDTO
//                            (orderDTO, new ProductDTO(pro.getPrice(), pro.getTitle()), pro.getAmountIfOrdered()));
//                }
//                orderDTO.setOrderedProducts(orderedProducts);
//                orders.add(orderDTO);
//            }
//            ordDAO.close();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        }
//        return orders;
//    }

//    public void addClient(String lastname, String firstname, String advicer)
//            throws BeanDatabaseException {
//        try {
//            PersonDAO perDAO = mysqlfactory.getPersonDAO();
//            perDAO.insertPerson(new Person(lastname, firstname), Integer.parseInt(advicer));
//            perDAO.close();
//        } catch (TransactionFailedException e) {
//            e.printStackTrace();
//            throw new BeanDatabaseException();
//        } catch (TransactionRollbackedException er) {
//            er.printStackTrace();
//            throw new BeanDatabaseException();
//        } catch (GettingDataFailedException e) {
//            throw new BeanDatabaseException();
//        } catch (ConnectionFailedException e) {
//            throw new BeanDatabaseException();
//        }
//    }

//    public void addOrder(String clientID, exposed.transfer_objects.Status status, HashMap productsHashMap)
//            throws BeanDatabaseException {
//        {
//            try {
//                OrderDAO ordDAO = mysqlfactory.getOrderDAO();
//                PersonDAO clDAO = mysqlfactory.getPersonDAO();
//                Person client = clDAO.getPerson(
//                        Integer.parseInt(clientID));
//                if (client == null) {
//                    mysqlfactory.getPersonDAO().insertPerson(client, 0);
//                } else {
//                    Order newOrder = new Order(client);
//                    newOrder.setOrderStatus(status);
//                    int newOrderID = ordDAO.insertOrder(newOrder);
//                    newOrder.setOrderID(newOrderID);
//                    Set ids = productsHashMap.keySet();
//                    Iterator itIDS = ids.iterator();
//                    while (itIDS.hasNext()) {
//                        String id = String.valueOf(itIDS.next());
//                        String am = (String) productsHashMap.get(id);
//                        ordDAO.addProductOrder(newOrderID, new Product(Integer.parseInt(id)), Integer.parseInt(am));
//                    }
//                    ordDAO.close();
//                    requestToProcess(newOrderID);
//                }
//                clDAO.close();
//            } catch (TransactionFailedException e) {
//                throw new BeanDatabaseException();
//            } catch (TransactionRollbackedException er) {
//                throw new BeanDatabaseException();
//            } catch (GettingDataFailedException e) {
//                throw new BeanDatabaseException();
//            } catch (ConnectionFailedException e) {
//                throw new BeanDatabaseException();
//            }
//
//        }
//    }

    //------------------------------HOME methods------------------------------

    /**
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {
        System.out.println("ShopBuisnessFacadeBean create invoked");

        try {
            Properties databaseconnectionProperties = new Properties();
            databaseconnectionProperties.put("dataSource", SHOP_DATASOURCE_JNDI);

//            mysqlfactory = DAOFactory.getDAOFactory(DAOFactory.REL_DB);
//            mysqlfactory.setProperties(databaseconnectionProperties);

//            hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
//            hibernateFactory.setProperties(databaseconnectionProperties);

            Context ctx = new InitialContext();
            QueueConnectionFactory queueConnectionFactory =
                    (QueueConnectionFactory) ctx.lookup(SHOP_CONNECTION_FACTORY_JNDI);
            QueueConnection conn = queueConnectionFactory.createQueueConnection();
            session = conn.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) ctx.lookup(QUEUE_JNDI);
            Queue queueMail = (Queue) ctx.lookup(MAIL_QUEUE_JNDI);
            sender = session.createSender(queue);
            senderMail = session.createSender(queueMail);
            mailSession = (Session) ctx.lookup(SHOP_MAIL_SESSION_JNDI);
            System.out.println("SBFB: Queues initialized\n");

//        } catch (ConnectionFailedException e) {
//            e.printStackTrace();
//            throw new CreateException();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new CreateException();
        } catch (JMSException e) {
            e.printStackTrace();
            throw new CreateException();
        }
    }

    public void ejbActivate() throws EJBException {
        System.out.println("ShopBuisnessFacadeBean activate invoked");
    }

    public void ejbPassivate() throws EJBException {
        System.out.println("ShopBuisnessFacadeBean passivate invoked");
    }

    public void ejbRemove() throws EJBException {
        System.out.println("ShopBuisnessFacadeBean remove invoked");
    }

    public void setSessionContext(SessionContext sessionContext) throws EJBException {
        this.ctx = sessionContext;
    }

    /**
     * @ejb.interface-method
     * @ejb.transaction type="RequiresNew"
     */
    /*public void validateOrderData() throws IllegalParamsException,
            BeanDatabaseException {

        try {
            OrderedProductDAO opDAO = mysqlfactory.getOrderProductDAO();
            ArrayList<Product> products = (ArrayList<Product>) opDAO.getOrderedProducts();
            opDAO.close();
            HashMap productsHashMap = new HashMap();
            Iterator i = products.iterator();
            boolean error = false;
            while (i.hasNext()) {
                Product pro = (Product) i.next();
                String amount = request.getParameter(String.valueOf(pro.getProductID()));
                if (!"".equals(amount) && !"0".equals(amount)) {
                    try {
                        if (!(Integer.parseInt(amount) > 0 && Integer.parseInt(amount) < 100000)) {
                            error = true;
                        }
                    } catch (NumberFormatException nfe) {
                        error = true;
                    }
                    productsHashMap.put(pro.getProductID(), amount);
                }
            }
            if (productsHashMap.isEmpty()) {
                error = true;
            }
            request.setAttribute(AMOUNTS_PARAMETER, productsHashMap);
            if (error) {
                throw new IllegalParamsException();
            }
        } catch (GettingDataFailedException e) {
            throw new BeanDatabaseException();
        } catch (ConnectionFailedException e) {
            throw new BeanDatabaseException();
        }
    }*/

//    private boolean isStringAllowed(String str) {
//        if (str.length() >= 100) {
//            return false;
//        }
//        for (int h = 0; h < str.length(); h++) {
//            Pattern pattern = Pattern.compile(PATTERN_NAMES_CHECK);
//            Matcher matcher = pattern.matcher(String.valueOf(str.charAt(h)));
//            if (!matcher.matches()) {
//                return false;
//            }
//        }
//        return true;
//    }

//    public void setWarehouseEmail(String warehouseEmail) {
//        this.warehouseEmail = warehouseEmail;
//        System.out.println("email set: " + this.warehouseEmail);
//    }

}
