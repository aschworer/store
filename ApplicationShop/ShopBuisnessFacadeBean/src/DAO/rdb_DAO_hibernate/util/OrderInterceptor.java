package DAO.rdb_DAO_hibernate.util;

import org.hibernate.type.Type;
import org.hibernate.Interceptor;
import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.Iterator;

public class OrderInterceptor implements Interceptor, Serializable {


    public boolean onLoad(Object entity,
                          Serializable id,
                          Object[] state,
                          String[] propertyNames,
                          Type[] types) {
        System.out.println("-----------LOG: ORDER LOADED");
        return false;
    }

    public boolean onSave(Object entity,
                          Serializable id,
                          Object[] state,
                          String[] propertyNames,
                          Type[] types) {
        System.out.println("-----------LOG: ORDER SAVED");
        return false;
    }


    public boolean onFlushDirty(Object object, Serializable serializable, Object[] objects, Object[] objects1, String[] strings, Type[] types) throws CallbackException {
        return false;
    }

    public void onDelete(Object object, Serializable serializable, Object[] objects, String[] strings, Type[] types) throws CallbackException {

    }

    public void onCollectionRecreate(Object object, Serializable serializable) throws CallbackException {

    }

    public void onCollectionRemove(Object object, Serializable serializable) throws CallbackException {

    }

    public void onCollectionUpdate(Object object, Serializable serializable) throws CallbackException {

    }

    public void preFlush(Iterator iterator) throws CallbackException {

    }

    public void postFlush(Iterator iterator) throws CallbackException {

    }

    public Boolean isTransient(Object object) {
        return false;
    }

    public int[] findDirty(Object object, Serializable serializable, Object[] objects, Object[] objects1, String[] strings, Type[] types) {
        return new int[0];
    }

    public Object instantiate(String string, EntityMode entityMode, Serializable serializable) throws CallbackException {
        return serializable;
    }

    public String getEntityName(Object object) throws CallbackException {
        return object.getClass().toString();
    }

    public Object getEntity(String string, Serializable serializable) throws CallbackException {
        return serializable;
    }

    public void afterTransactionBegin(Transaction transaction) {

    }

    public void beforeTransactionCompletion(Transaction transaction) {

    }

    public void afterTransactionCompletion(Transaction transaction) {

    }

    public String onPrepareStatement(String string) {
        return string;
    }
}