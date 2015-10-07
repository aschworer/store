package DAO.rdb_DAO_hibernate.user_types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.io.Serializable;
import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;
import exposed.transfer_objects.Status;

public class StatusUserType implements UserType {
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    public Class returnedClass() {
        return Status.class;
    }

    public boolean equals(Object x, Object y) {
        return (x == y)
                || (x != null
                && y != null
                && (x.equals(y)));
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {
        String statusDB = rs.getString(names[0]);
        return Status.getStatus(statusDB);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {
        st.setString(index, Status.convertToString((Status)value));
    }

    public Object deepCopy(Object value) {
        if (value == null) return null;
        return value;
    }

    public boolean isMutable() {
        return false;
    }

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