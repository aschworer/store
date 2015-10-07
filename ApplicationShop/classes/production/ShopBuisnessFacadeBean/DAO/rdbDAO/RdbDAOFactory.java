package DAO.rdbDAO;

import DAO.*;
import DAO.ConnectionFailedException;
import DAO.GettingDataFailedException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class represents DAO Factory Pattern for Relational Database wich allows you to use
 * DB "Shop" with given name and login information
 */
public class RdbDAOFactory extends DAOFactory {
    /**
     * Creates new <code>RdbOrderDAO</code> object with current connection settings
     *
     * @return new <code>RdbOrderDAO</code> object
     */
    public OrderDAO getOrderDAO() throws GettingDataFailedException, ConnectionFailedException {
        Connection c = this.createConnection(databaseConnectionProperties);
        return new RdbOrderDAO(c);
    }

    /**
     * Creates new <code>RdbPersonDAO</code> object with current connection settings
     *
     * @return new <code>RdbPersonDAO</code> object
     */
    public PersonDAO getPersonDAO() throws GettingDataFailedException,ConnectionFailedException{
        Connection c = this.createConnection(databaseConnectionProperties);
        return new RdbPersonDAO(c);
    }

    /**
     * Creates new <code>RdbProductDAO</code> object with current connection settings
     *
     * @return new <code>RdbProductDAO</code> object
     */
    public OrderedProductDAO getOrderProductDAO() throws GettingDataFailedException,ConnectionFailedException{
        Connection c = this.createConnection(databaseConnectionProperties);
        return new RdbOrderedProductDAO(c);
    }

    /**
     * Method establishes connection with database
     *
     * @throws ConnectionFailedException if faile to connect
     */
    private Connection createConnection(Properties props) throws ConnectionFailedException {
//        String user = props.getProperty("user");
//        String pass = props.getProperty("pass");
        String shopDataSource = props.getProperty("dataSource");
//        String shopDataSource = "ShopDataSourse";
//        servletContext = sc;
//        String user = sc.getInitParameter("weblogicUser");
//        String pass = sc.getInitParameter("weblogicPass");
//        String shopDataSource = sc.getInitParameter("weblogicShopDataSource");
//        String url = sc.getInitParameter("url");
        Connection c;
        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            c = DriverManager.getConnection(url, userName, password);
//            c.createStatement().execute("use " + database + ";");
            Context ctx = new InitialContext();
//            System.out.println(user + pass + shopDataSource);
            javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup(shopDataSource);
            //c = ds.getConnection(userName, password);
            c = ds.getConnection();
//            System.out.println(userName+"-"+password);
//        } catch (ClassNotFoundException e) {
//            System.err.println("Driver not found\n" + e.getMessage());
//            throw new ConnectionFailedException();
//        }
//        catch (InstantiationException e) {
//            System.err.println("InstallationException\n" + e.getMessage());
//            throw new ConnectionFailedException();
//        }
//        catch (IllegalAccessException e) {
//            System.err.println("Illegal Access\n" + e.getMessage());
//            throw new ConnectionFailedException();
        }
        catch (SQLException e) {
            System.err.println("\nSQLException caught\n" + e.getMessage());
            throw new ConnectionFailedException();
        } catch(NamingException e){
            System.err.println("JNDI lookup database failed\n" + e.getMessage());
            throw new ConnectionFailedException();
        }
       // conn = c;
        return c;
    }

//    public Connection getConn() {
//        return conn;
//    }
//
//    public void setConn(Connection conn) {
//        this.conn = conn;
//    }


//    public void closeConnection() throws ConnectionFailedException {
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            throw new ConnectionFailedException();
//        }
//    }
}
