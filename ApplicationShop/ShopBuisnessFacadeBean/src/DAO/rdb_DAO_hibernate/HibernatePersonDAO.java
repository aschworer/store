package DAO.rdb_DAO_hibernate;

import exposed.transfer_objects.CustomerDTO;
import exposed.transfer_objects.ReportItemDTO;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigInteger;

import org.hibernate.*;

public class HibernatePersonDAO {
    private Session session;


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public HibernatePersonDAO(Session session) {
        this.session = session;
    }

//    public void save(PersonDTO person) {
//        org.hibernate.Session session = HibernateUtil.getSession();
//        session.save(person);
//    }

    public void save(CustomerDTO customer) {
        session.save(customer);
    }

//    public PersonDTO getPerson(Integer personID) {
//        org.hibernate.Session session = HibernateUtil.getSession();
//        return (PersonDTO)session.load(PersonDTO.class, personID);
//    }

    public BigInteger countCustomers() {
        SQLQuery q = session.createSQLQuery("select count(*) from customers");
        ScrollableResults sc = q.scroll();
        sc.first();
        BigInteger l = (BigInteger) sc.get(0);
//        System.out.println("------------------"+sc.get(0));
//        System.out.println("------------------"+sc.get(0).getClass());
        sc.close();
        return l;
    }

    public CustomerDTO getCustomer(Integer customerID) {
        return (CustomerDTO) session.load(CustomerDTO.class, customerID);
    }

    public Collection<CustomerDTO> getCustomers(int step, int page) {

//        CustomerDTO c = (CustomerDTO)session.load(CustomerDTO.class, 1);
//        System.out.println(c.getPersonID());
//        System.out.println(c.getFirstname());
//        System.out.println(c.getLastname());
//        System.out.println(c.getDiscription());
//        System.out.println(c.getType());



//        System.out.println("SBFB: HIBERNATE TRYING TO GET customers");
//        int s = step * page;
//        if (s == step) {
//            step++;
//        }
//        System.out.println(page + page * step);
//        System.out.println(page * step + step);

//        if (page + page*step == page*step + step){
//
//        }
        int first = page*step;
        int max = step;

//        System.out.println(first);
//        System.out.println(max);

        Query q = session.createQuery("from CustomerDTO")
                .setFirstResult(first)
                .setMaxResults(max)
                ;
        Collection<CustomerDTO> persons = q.list();
        Iterator i = persons.iterator();

        while (i.hasNext()) {
            Hibernate.initialize(i.next());
        }
        return persons;
    }

    public Collection<CustomerDTO> getAllCustomers() {
//        System.out.println("SBFB: HIBERNATE TRYING TO GET customers");
        Query q = session.createQuery("from CustomerDTO");
        return q.list();
    }

//    public Collection<PersonDTO> getAllPersons() {
//        org.hibernate.Session session = HibernateUtil.getSession();
//        System.out.println("SBFB: HIBERNATE TRYING TO GET persons");
//        Collection<PersonDTO> persons = (Collection<PersonDTO>)
//                session.createQuery("from PersonDTO").list();
//        return persons;
//    }

    public Collection<ReportItemDTO> getReport() {
//        ArrayList<ReportItemDTO> items = new ArrayList<ReportItemDTO>();
        Collection<ReportItemDTO> items;
//        System.out.println("SBFB: HIBERNATE TRYING TO GET REPORT");
//        Iterator i = session.createQuery("select distinct customer.type from CustomerDTO customer").list().iterator();
//        Iterator i = session.createSQLQuery("SELECT distinct discriminator from CUSTOMER").list().iterator();
//        while (i.hasNext()) {
//            String discr = (String) i.next();
//            System.out.println("DISCR: " + discr);

//            String hglQuery = "select o.orderDate, count(o.orderID), sum(p.price * op.amount) " +
//                                                "from CustomerDTO c " +
//                                                "join OrderDTO o where c.id=o.personID " +
//                                                "join o.OrderedProductDTO op where op.orderID=o.orderID " +
//                                                "join ProductDTO p where p.productID=op.productID " +
//                                                "where c.type like '" + discr +
//                                                "' group by o.orderDate";

//            String hglQuery = "select count(o.orderID) " +
//                                                "from exposed.transfer_objects.CustomerDTO c" +
//                    " left join fetch exposed.transfer_objects.OrderDTO as o"+
//                     " where o.client=c"+
//                    " and c.type='BS'";

//            String hglQuery = "select o.orderDate, count(o.id) " +
//                                                            "from exposed.transfer_objects.CustomerDTO c " +
//                                                            "left join fetch exposed.transfer_objects.OrderDTO o " +
//                                                            "left join fetch exposed.transfer_objects.OrderedProductDTO op " +
//                                                            "left join fetch exposed.transfer_objects.ProductDTO p where op.orderID=o.id and c.id=o.client.personID and p.productID=op.product.productID " +
//                                                            "and c.type like 'BS'" +
//                                                            " group by o.orderDate";

//            String hqlQuery = "select c.type, o.orderDate, " +
//                    "count(o.orderID), " +
//                    "sum(p.price*op.amount) from exposed.transfer_objects.OrderedProductDTO op " +
//                    "left inner join op.order o " +
//                    "left inner join op.product p " +
//                    "left inner join exposed.transfer_objects.CustomerDTO c " +
//                    "where c.id=o.client.personID and op.order.orderID=o.orderID and " +
//                    "p.productID=op.product.productID and c.type like '" +discr+
//                    "' group by op.id";

//            String hqlQuery = "select o.orderID " +
//                    "from exposed.transfer_objects.OrderDTO o " +
//                    "left join fetch exposed.transfer_objects.CustomerDTO";

//            HibernateOrderDAO hod = new HibernateOrderDAO();
//            Collection<OrderDTO> c =  hod.getOrders();
//            Iterator iterator =c.iterator();
//            while(iterator.hasNext()){
//                OrderDTO order = (OrderDTO) iterator.next();
//                if(order.getClient()==null){
//                    System.out.println();
//                    System.out.println("FUCK! "+order.getOrderID());
//                    System.out.println();
//                }
//            }

//            where c.personID=o.client.personID " +
//                    "and c.type like '"+discr+"'

//            session.createQuery(hqlQuery);

        //WORKS
//        String sqlQuery = "select o.order_date, count(o.order_id), sum(p.price * op.amount) " +
//                    "from customers c " +
//                    "join orders o on c.id=o.person_id " +
//                    "join ordered_products op on op.order_id=o.order_id " +
//                    "join products p on p.product_id=op.product_id " +
//                    "where c.discriminator like '" + discr +
//                    "' group by c.discriminator;";

        //, o.orderDate, count(o.orderID), sum(line.amount*line.product.price)

//            String hqlQuery = "select o.client.type, o.orderDate, count(o.orderID), sum(line.amount*line.product.price) " +
//                    "from exposed.transfer_objects.OrderDTO o " +
//                    "join o.orderedProducts line join line.product " +
//                    "group by o.client, o.orderDate " +
//                    "order by o.orderID desc";

//            ArrayList clients = (ArrayList)
//                    session.createQuery(hqlQuery).list();

//            ArrayList<ReportItemDTO> clients = (ArrayList<ReportItemDTO>)
//                    session.createQuery(hqlQuery).list();

//            ArrayList<ReportItemDTO> clients = (ArrayList<ReportItemDTO>)
//                    session.createQuery(hqlQuery).list();
//                                              , sum(line.amount*line.product.price)
        items = (ArrayList<ReportItemDTO>)
                session.createQuery(
                        "select new exposed.transfer_objects.ReportItemDTO(o.client.type, o.orderDate, count(o.orderID)) " +
                                "from exposed.transfer_objects.OrderDTO o " +
                                "join o.orderedProducts line join line.product " +
                                "group by o.client.type, o.orderDate " +
                                "order by o.orderID desc").list();
        Iterator it = items.iterator();
        Iterator itd = session.createQuery(
                "select new java.lang.Double(sum(line.amount*line.product.price)) " +
                        "from exposed.transfer_objects.OrderDTO o " +
                        "join o.orderedProducts line join line.product " +
                        "group by o.client.type, o.orderDate " +
                        "order by o.orderID desc"
        ).list()
                .iterator();


        while (it.hasNext()) {
//                System.out.println("");
            ReportItemDTO r = (ReportItemDTO) it.next();
            r.setOrdersSum((Double) itd.next());
//            System.out.println("EL: " + r);
//                ReportItemDTO reportItemDTO = (ReportItemDTO) it.next();
//                System.out.println(reportItemDTO);
        }

//            System.out.println("CLIENTS TYPE FOUND SIZE "+clients.size());

//            String hqlQuery = "select o.orderDate, count(o.orderID), sum(p.price * op.amount) " +
//                                                            "from exposed.transfer_objects.CustomerDTO c " +
//                                                            "join exposed.transfer_objects.OrderDTO o " +
//                                                            "join exposed.transfer_objects.OrderedProductDTO op " +
//                                                            "join exposed.transfer_objects.ProductDTO p where op.orderID=o.orderID and c.personID=o.client.personID and p.productID=op.product.productID " +
//                                                            "and c.personID like '"+ 1 +
//                                                            "' group by c.personID";

//            SQLQuery q = session.createSQLQuery(sqlQuery);
//            ScrollableResults sc = q.scroll();
//            sc.first();
//            Date date;
//            Integer count;
//            Double sum;
//            try {
//                do {
//                    date = Date.valueOf(sc.get(0).toString());
//                    count = Integer.parseInt(sc.get(1).toString());
//                    sum = Double.parseDouble(sc.get(2).toString());
//                    items.add(new ReportItemDTO(discr, new Date(date.getTime()), count, sum));
//                    sc.next();
//                    System.out.println("Type " + discr);
//                    System.out.println("Date " + date);
//                    System.out.println("count " + count);
//                    System.out.println("sum " + sum);
//                } while (!sc.isLast());
//                sc.close();
//            } catch (NullPointerException e) {
//                continue;
//            }

//            Iterator qsIterator = session.createQuery(querySum).list().iterator();

//            session.add createCriteria(ReportItemDTO.class);
//            session.createCriteria(ReportItemDTO.class);
//            Iterator d = q.list().iterator();
//            SQLQuery
//            while (d.hasNext()){
//                System.out.println(d.next());

//                ReportItemDTO row = (ReportItemDTO) i.next();
//            }
        /*

select o.order_date,
count(o.order_id),
sum(p.price*op.amount)
from customer c
join orders o on c.id=o.person_id
join ordered_products op on op.order_id=o.order_id
join products p on p.product_id=op.product_id
where c.lastname='PS'
group by o.order_date;

        */

//        }

        return items;
    }
}
