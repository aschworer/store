package DAO.rdb_DAO_hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;

public class HibernateUtil {
    static {
        try {
            new Configuration().
//                    setInterceptor(new OrderInterceptor()).
                    configure("/DAO/rdb_DAO_hibernate/util/hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessions = null;
        try {
            Context ctx = new InitialContext();
            String jndiName = "HibernateFactory";
            sessions = (SessionFactory) ctx.lookup(jndiName);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return sessions;
    }

    public static Session getSession() {
        try {
//            System.out.println("\n\n\n\nTRYING TO GET CURRENT SESSION");
            return getSessionFactory().getCurrentSession();
        } catch (IllegalStateException e) {
            System.out.println("\n\n\n\nEXCEPTION GETTING SESSION");
            return getSessionFactory().openSession();
        }
    }

//    public static Session getNewSession() {
//        return getSessionFactory().openSession();
//    }
}
