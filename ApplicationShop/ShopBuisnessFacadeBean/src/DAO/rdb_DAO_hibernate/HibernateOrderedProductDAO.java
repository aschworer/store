package DAO.rdb_DAO_hibernate;

import exposed.transfer_objects.ProductDTO;
import exposed.transfer_objects.OrderedProductDTO;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Query;


public class HibernateOrderedProductDAO{
    private Session session;


    public HibernateOrderedProductDAO(Session session) {
        this.session = session;
    }


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void save(OrderedProductDTO oProduct) {

//        System.out.println("----------------------AMOUNT: ----------------" + oProduct.getAmount());
        session.save(oProduct);
    }

    public void save(ProductDTO product) {
//        System.out.println("----------------------AMOUNT: ----------------" + oProduct.getAmount());
        session.save(product);
    }

    public void update(ProductDTO product) {
//        System.out.println("Product last_mail_updated" + product.getLastMail());
        session.update(product);
        session.flush();
    }


    public Collection<ProductDTO> getProducts() {
//        System.out.println("SBFB: HIBERNATE TRYING TO GET products");
        Query q = session.createQuery("from ProductDTO").setCacheable(true)
                .setCacheRegion("query.Products");
        return q.list();


//        Collection<ProductDTO> products = (Collection<ProductDTO>)
//                .list();
//        Iterator i = q.iterate();
//
//        while(i.hasNext()){
//            ProductDTO p = (ProductDTO)i.next();
//            System.out.println(p.getTitle());
//            System.out.println(p.getPrice());
//        }
//        return new ArrayList<ProductDTO>();


    }


//    public Collection<ProductDTO> getProducts() {
//        System.out.println("SBFB: HIBERNATE TRYING TO GET products");
//        Collection<ProductDTO> products = (Collection<ProductDTO>)
//                session.createSQLQuery
//                        ("select {product.*} from products").
//                        addEntity("product", ProductDTO.class).list();
//        return products;
//    }


    public ProductDTO getProduct(Integer proInteger) {
//        session.createSQLQuery("select {product} from products").setEntity("product", ProductDTO.class);
//        return session

        return (ProductDTO)session.load(ProductDTO.class, proInteger);
    }

    
}
