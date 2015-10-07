package DAO;

import DAO.rdbDAO.rdb_transfer_objects.Person;
import DAO.TransactionFailedException;
import DAO.TransactionRollbackedException;

/**
 * Interface for PersonDAO
 */
public interface PersonDAOInterface {
    /**
     * Method inserts object <code>Person</code> in base where data stored
     * @param person
     * @return person id
     * @throws TransactionRollbackedException if transaction rollbacked
     * @throws TransactionFailedException if transaction failed
     */
    public int insertPerson(Person person, int idAdv) throws TransactionRollbackedException, TransactionFailedException;
}
