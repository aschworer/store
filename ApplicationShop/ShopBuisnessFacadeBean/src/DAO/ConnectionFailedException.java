package DAO;

/**
 * Throwed when cannot connect to DataBase if driver not found or login failed, etc.
 * Throwed by {@link DAO.rdbDAO.RdbDAOFactory <code>DAO.rdbDAO.RdbDAOFactory</code>} instance
 *
 * @see DAO.rdbDAO.RdbDAOFactory
 */
public class ConnectionFailedException extends Exception {
}
