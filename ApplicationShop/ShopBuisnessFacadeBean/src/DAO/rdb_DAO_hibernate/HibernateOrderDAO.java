package DAO.rdb_DAO_hibernate;

import exposed.transfer_objects.OrderDTO;
import exposed.transfer_objects.OrderedProductDTO;

import java.util.*;
import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Criteria;


public class HibernateOrderDAO {
    private Session session;


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }


    public HibernateOrderDAO(Session session) {
        this.session = session;
    }

    public int save(OrderDTO order) {
        Serializable idS = session.save(order);
        Integer id = (Integer) idS;
        order.setOrderID(id);
        Iterator setI = order.getOrderedProducts().iterator();
        while (setI.hasNext()) {
            OrderedProductDTO opd = (OrderedProductDTO) setI.next();
//            System.out.println("saving... ordered products");
//            System.out.println(opd.getProduct().getProductID());
//            System.out.println(opd.getAmount());
//            System.out.println("----------------------AMOUNT: ----------------" + opd.getAmount());
            session.save(opd);
        }
        return id;
    }

    public void update(OrderDTO order) {
//        System.out.println("----------------------AMOUNT: ----------------" + oProduct.getAmount());
        session.update(order);
    }

    public OrderDTO getOrder(Integer orderID) {
//        OrderDTO o = (OrderDTO) ;
//        Hibernate.initialize(o.getClient());
        return (OrderDTO)session.load(OrderDTO.class, orderID);
    }

    public Collection<Date> getDates() {
        return session.createQuery("select distinct o.orderDate from OrderDTO o").list();
    }


    public Collection<OrderDTO> getOrders(HashMap filter) {

        ArrayList<OrderDTO> orders;
        Query q = session.createQuery("from OrderDTO");

        if (!"0".equals(filter.get("date")) && null != filter.get("date")) {
            try {
                String dateStr = (String) filter.get("date");
                Date date = new java.sql.Date(java.sql.Date.valueOf(dateStr).getTime());
                session.enableFilter("filterByDate").setParameter("date", date);
            } catch (IllegalArgumentException e) {
                System.err.println("No filter date");
            }
        }
//        q.setFirstResult((Integer) filter.get("pageNum") * (Integer) filter.get("step"));
//        q.setMaxResults((Integer) filter.get("step"));

        orders = (ArrayList<OrderDTO>) q.list();

//            session.createFilter(OrderDTO.class, "where this.orderID > 2").list();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            System.err.println("No filter date");
//            orders = (ArrayList<OrderDTO>)
//                    session.createQuery("from OrderDTO")
//                            .list();
//        }

//        Hibernate.initialize(orders);
        Iterator i = orders.iterator();
        while (i.hasNext()) {
            OrderDTO order = (OrderDTO) i.next();
            Hibernate.initialize(order.getClient());
            Hibernate.initialize(order.getOrderedProducts());
//                System.out.println("order loaded: " + order.getOrderID());
//                System.out.println("order loaded: " + order.getClient());
//                System.out.println("order products size: " + order.getOrderedProducts().size());
            Iterator prod = order.getOrderedProducts().iterator();
            while (prod.hasNext()) {
                OrderedProductDTO prDTO = (OrderedProductDTO) prod.next();
                Hibernate.initialize(prDTO.getProduct());
//                    System.out.println("--------" + prDTO.getProduct().getTitle());
//                    System.out.println("--------" + prDTO.getProduct().getProductID());
//                    System.out.println("--------" + prDTO.getProduct().getPrice());
            }
        }

//        session.createFilter(orders, "where ty")
        return orders;
    }


}
