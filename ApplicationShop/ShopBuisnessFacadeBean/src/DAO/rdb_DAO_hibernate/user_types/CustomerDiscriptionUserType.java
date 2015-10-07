package DAO.rdb_DAO_hibernate.user_types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Clob;
import java.io.Serializable;

import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;
import org.hibernate.Hibernate;
import org.hibernate.engine.SessionImplementor;

public class CustomerDiscriptionUserType implements UserType {
    public int[] sqlTypes() {
        return new int[]{Types.CLOB};
    }

    public Class returnedClass() {
        return String.class;
    }

    public boolean equals(Object x, Object y) {
        return (x == y)
                || (x != null
                && y != null
                && (x.equals(y)));
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {

        Clob clob = rs.getClob(names[0]);
        return clob.getSubString(1, (int) clob.length());
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {
        st.setClob(index, Hibernate.createClob((String) value));
    }

    public Object deepCopy(Object value) {
        if (value == null) return null;
        return new String((String) value);
    }

    public boolean isMutable() {
        return false;
    }

//    public int hashCode(Object obj) {
//        int result;
//        result = obj.hashCode();
//        result = 29 * result/* + email.hashCode()*/;
//        return result;
//    }

    public int hashCode(Object x) {
        return x.hashCode();
    }


    public Serializable disassemble(Object value)
            throws HibernateException {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached,
                           Object owner)
            throws HibernateException {
        return cached;
    }


    public Object replace(Object object, Object object1, Object object2) throws HibernateException {
        return object;
    }
}