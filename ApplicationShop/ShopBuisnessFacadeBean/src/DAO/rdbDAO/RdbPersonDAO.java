package DAO.rdbDAO;

import DAO.*;
import DAO.rdbDAO.rdb_transfer_objects.Person;
import DAO.GettingDataFailedException;
import DAO.ConnectionFailedException;

import java.sql.*;
import java.util.Collection;

/**
 * Class represents implementation of Transfer Object pattern
 * for working with concrete DB entry - Order
 */
public class RdbPersonDAO extends PersonDAO {
    private Connection conn;
    private PreparedStatement selectPer;
    private PreparedStatement insertPer;
    private PreparedStatement insertAdv;
    private PreparedStatement selectPerID;
    //--------------------------------------------------------------------
//    public void getPerson(Message mes) {
//
//        Session session = DAO.rdb_DAO_hibernate.util.HibernateUtil.getSession();
//        Transaction tx = session.beginTransaction();
//        Transaction newTransaction = newSession.beginTransaction();
//        List messages =
//                newSession.find("from DAO.rdbDAO.Message as m order by m.text asc");
//        System.out.println(messages.size() + " message(s) found:");
//        for (Iterator iter = messages.iterator(); iter.hasNext();) {
//            DAO.rdbDAO.Message message = (DAO.rdbDAO.Message) iter.next();
//            System.out.println(message.getText());
//        }
//        newTransaction.commit();
//        newSession.close();
//
//
//    }
    //--------------------------------------------------------------------


    /**
     * Constructs an instance of <code>RdbPersonDAO</code> object with given connection and
     * <code>Statement</code> object for Statement creation
     *
     * @param conn Connection with database
     */
    public RdbPersonDAO(Connection conn) throws GettingDataFailedException {
        try {
            selectPer = conn.prepareStatement(
                    "SELECT p1.person_id person_id, p1.firstname person_firstname, p1.lastname person_lastname, " +
                            "p2.person_id advicer_id, p2.firstname advicer_firstname, p2.lastname advicer_lastname " +
                            "from persons p1 left join persons p2 on p1.advicer=p2.person_id group by p1.person_id order by person_id desc;");
            insertAdv = conn.prepareStatement("INSERT INTO persons (lastname, firstname) VALUES (?,?)", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            insertPer = conn.prepareStatement("INSERT INTO persons (lastname, firstname, advicer) VALUES (?,?,?)");
            selectPerID = conn.prepareStatement("SELECT * from persons where person_id=?");
        } catch (SQLException e) {
            throw new GettingDataFailedException();
        }
        this.conn = conn;
    }

    /**
     * Method constructs String value with needed information about all persons
     * stored in database (lastname, firstname and advicer)
     *
     * @return String value for needed information
     * @throws DAO.GettingDataFailedException when SQLError appears
     */
    public void getPersonsAndAdvicers(Collection<Person> persons)
            throws GettingDataFailedException {
        try {
            ResultSet rs = selectPer.executeQuery();
            while (rs.next()) {
                int personID = rs.getInt("person_id");
                String personFirstname = rs.getString("person_firstname");
                String personLastname = rs.getString("person_lastname");
                int advicerID = rs.getInt("advicer_id");
                String advicerFirstname = rs.getString("advicer_firstname");
                String advicerLastname = rs.getString("advicer_lastname");
                Person person = new Person(personID, personLastname, personFirstname, advicerID);
                person.setAdvicer(new Person(advicerID, advicerLastname, advicerFirstname));
                persons.add(person);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
    }

    /**
     * Method adds information about person to database
     *
     * @return id of the entry that was created with information about person added
     * @throws TransactionRollbackedException if transaction rollbacked
     * @throws TransactionFailedException     if transaction failed
     */
    public int insertPerson(Person person, int idAdv) throws TransactionRollbackedException, TransactionFailedException {
        int id=0;
        boolean wasAutoCommit = true;
        try {
//            if (conn.getAutoCommit()) {
//                conn.setAutoCommit(false);
//                wasAutoCommit = true;
//            } else {
//                wasAutoCommit = false;
//            }

            if (idAdv == 0) {
                insertAdv.setString(1, person.getLastname());
                insertAdv.setString(2, person.getFirstname());
                insertAdv.executeUpdate();
                ResultSet adv = insertAdv.executeQuery("SELECT * FROM persons ORDER BY person_id DESC LIMIT 1");
                adv.next();
                id = adv.getInt("person_id");
                adv.close();
            } else {
                insertPer.setString(1, person.getLastname());
                insertPer.setString(2, person.getFirstname());
                insertPer.setInt(3, idAdv);
                insertPer.executeUpdate();
                ResultSet r = insertPer.executeQuery("SELECT * FROM persons ORDER BY person_id DESC LIMIT 1");
                r.next();
                id = r.getInt("person_id");
                r.close();
            }

//            conn.commit();
//            if (wasAutoCommit) {
//                conn.setAutoCommit(true);
//            }

        } catch (SQLException e) {
//            try {
//                conn.rollback();
//                if (wasAutoCommit) {
//                    conn.setAutoCommit(true);
//                }
//                System.err.println("\n---SQLException caught---\n" + e.getMessage());
//                throw new TransactionRollbackedException();
//            } catch (SQLException ex) {
//                throw new TransactionFailedException();
//            }
        }
        return id;
    }

    /**
     * @param personID
     * @return null if not found
     * @throws DAO.GettingDataFailedException
     */
    public Person getPerson(int personID) throws GettingDataFailedException {
        Person newPer = null;
        try {
            selectPerID.setInt(1, personID);
            ResultSet rs = selectPerID.executeQuery();
            while (rs.next()) {
                newPer = new Person(rs.getInt("person_id"),
                        rs.getString("firstname"), rs.getString("lastname"),
                        rs.getInt("advicer"));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("\n---SQLException caught---\n" + e.getMessage());
            throw new GettingDataFailedException();
        }
        return newPer;
    }

    public void close() throws ConnectionFailedException {
        try {
            insertPer.close();
            selectPer.close();
            selectPerID.close();
            conn.close();
        } catch (SQLException e) {
            throw new ConnectionFailedException();
        }
    }

}
