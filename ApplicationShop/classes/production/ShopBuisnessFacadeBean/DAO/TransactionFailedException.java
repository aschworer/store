package DAO;

/**
 * Throwed when cannot commit or even rollback transaction while using database by
 * {@link DAO.rdbDAO.RdbDAOFactory <code>DAO.rdbDAO.RdbDAOFactory</code>} instance
 *
 * @see DAO.rdbDAO.RdbDAOFactory
 */
public class TransactionFailedException extends Exception{
}
