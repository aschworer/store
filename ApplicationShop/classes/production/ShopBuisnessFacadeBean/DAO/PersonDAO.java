package DAO;

import DAO.rdbDAO.rdb_transfer_objects.Person;
import DAO.GettingDataFailedException;
import DAO.TransactionFailedException;
import DAO.ConnectionFailedException;
import DAO.TransactionRollbackedException;

import java.util.Collection;

public abstract class PersonDAO implements PersonDAOInterface{
    public abstract int insertPerson(Person person, int idAdv)
            throws TransactionRollbackedException, TransactionFailedException;
    public abstract void getPersonsAndAdvicers(Collection<Person> personsTargetList)
            throws GettingDataFailedException;
    public abstract Person getPerson(int personID) throws GettingDataFailedException;
     public abstract void close() throws ConnectionFailedException;
}
