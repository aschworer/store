package DAO;
 /**
 * Throwed when cannot commit transaction while using database by
 * {@link DAO.rdbDAO.RdbDAOFactory <code>DAO.rdbDAO.RdbDAOFactory</code>} instance
  * Transaction was rollbacked.
 *
 * @see DAO.rdbDAO.RdbDAOFactory
  */
public class TransactionRollbackedException extends Exception{
}
