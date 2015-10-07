package DAO;

import DAO.rdbDAO.RdbDAOFactory;
import DAO.ConnectionFailedException;
import DAO.GettingDataFailedException;

import java.util.Properties;

/**
 * Represents DAOFactory pattern which constructs some concrete DAOFactory depending on
 * the way of data stored
 *
 * @see DAO.rdbDAO.RdbDAOFactory example
 */
public abstract class DAOFactory {

    protected Properties databaseConnectionProperties;

    /**
     * Static constant for Relation databases
     */
    public static final int REL_DB = 1;
//    public static final int HIBERNATE = 2;

    /**
     * Establishes connection
     *
     * @return
     * @throws ConnectionFailedException
     */
    // public abstract void createConnection(ServletConfig sc) throws ConnectionFailedException;
    public void setProperties(Properties databaseConnectionProperties) throws ConnectionFailedException {
        this.databaseConnectionProperties = databaseConnectionProperties;
    }

    public Properties getDatabaseConnectionProperties() {
        return databaseConnectionProperties;
    }

    /**
     * Creates new PersonDAO object
     *
     * @return PersonDAO object
     * @see DAO.rdbDAO.RdbPersonDAO example
     */
    public abstract PersonDAO getPersonDAO() throws GettingDataFailedException, ConnectionFailedException;

    /**
     * Creates new OrderDAO object
     *
     * @return OrderDAO object
     * @see DAO.rdbDAO.RdbOrderDAO example
     */
    public abstract OrderDAO getOrderDAO() throws GettingDataFailedException, ConnectionFailedException;

    /**
     * Creates new OrderProductDAO object
     *
     * @return
     * @see DAO.rdbDAO.RdbOrderedProductDAO
     */
    public abstract OrderedProductDAO getOrderProductDAO() throws GettingDataFailedException, ConnectionFailedException;

    /**
     * Method creates concrete DAOFactory choosing optional from constants
     * stores as static fields in class
     *
     * @param factory
     * @return DAOFactory
     */
    public static DAOFactory getDAOFactory(int factory) {
        switch (factory) {
            case REL_DB:
                return new RdbDAOFactory();
//            case HIBERNATE:
//                return new HibernateDAOFactory();
            default:
                return null;
        }
    }
    //  public abstract void closeConnection() throws ConnectionFailedException;

}
