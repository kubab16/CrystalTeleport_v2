package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.configuration.LocalConfiguration;
import server.database.table.CrystalHome;
import server.database.table.CrystalSpawn;
import server.database.table.CrystalWorld;
import server.printmessage.Message;
import server.throwExemption.DatabaseConnectionException;
import server.throwExemption.ExceptionHandler;
import server.throwExemption.RuntimeSQLException;

import java.util.logging.Level;


public class Database {

	private static Connection connection = null;
    private static double last_reconnect = 0;
    private static boolean already_reconnecting = false;

    /**
     * Default constructor. Connects to the remote database, performs patches if necessary, and holds to the DB info.<br />
     *
     * @throws DatabaseConnectionException Thrown if the plugin could not connect to the database
     */
    public Database() throws DatabaseConnectionException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new DatabaseConnectionException("MySQL driver was not found!");
        }

        try {
            connection = DriverManager.getConnection(
                    LocalConfiguration.DBConnect.toString(),
                    LocalConfiguration.DBUser.toString(),
                    LocalConfiguration.DBPass.toString()
            );
           
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e);
        }

        try {
            if (!connection.getAutoCommit()) connection.setAutoCommit(true);
        } catch (Throwable t) {
            throw new RuntimeSQLException("Could not set AutoCommit to false. Cause: " + t, t);
        }
        try {
        	int tableCreate = 0;
        	Statement statment = CreateStatement();
        	tableCreate += statment.executeUpdate(CrystalHome.getTable());
        	tableCreate += statment.executeUpdate(CrystalHome.GetTableUser());
        	tableCreate += statment.executeUpdate(CrystalSpawn.getTable());
        	tableCreate += statment.executeUpdate(CrystalWorld.getTable());
        	statment.close();
        	if (tableCreate > 0)
        	{
        		Message.log("Create table in database.");
        	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }


    /**
     * Attempts to reconnect to the remote server
     *
     * @return <b>true</b> if the connection was present, or reconnect is successful. <b>false</b> otherwise.
     */
    public static boolean reconnect() {
        try {
            if (connection.isValid(10)) {
                Message.log("Connection is still present. Malformed query detected.");
                return false;
            }

            if (System.currentTimeMillis() < last_reconnect + (LocalConfiguration.DBReconnect.toInteger() * 1000)) {
                return false;
            }

            if (already_reconnecting) {
                return false;
            } else {
                already_reconnecting = true;
            }


            Message.log(Level.WARNING, "Attempting to re-connect to the database");
            connection = DriverManager.getConnection(
                    LocalConfiguration.DBConnect.toString(),
                    LocalConfiguration.DBUser.toString(),
                    LocalConfiguration.DBPass.toString()
            );

            try {
                if (connection.getAutoCommit()) connection.setAutoCommit(false);
            } catch (Throwable t) {
                throw new RuntimeSQLException("Could not set AutoCommit to false. Cause: " + t, t);
            }

            Message.log("Connection re-established. No data is lost.");
            already_reconnecting = false;
            return true;
        } catch (Exception e) {
            already_reconnecting = false;
            Message.log(Level.SEVERE, "Failed to re-connect to the database. Data is being stored locally.");
            if (LocalConfiguration.Debug.toBoolean()) e.printStackTrace();
        }
        last_reconnect = System.currentTimeMillis();
        return false;
    }

    /**
     * Pushes data to the remote database.<br />
     * This is a raw method and should never be used by itself. Use the <b>QueryUtils</b> wrapper for more options
     * and proper error handling. This method is not to be used for regular commits to the database.
     *
     * @param query SQL query
     * @return <b>true</b> if the sync is successful, <b>false</b> otherwise
     */
    public static boolean executeUpdate(String query) {
        int rowsChanged = 0;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            rowsChanged = statement.executeUpdate(query);
            statement.close();
            connection.commit();
        } catch (Throwable t) {
            ExceptionHandler.handle(t);
            if (reconnect()) return executeUpdate(query);
            else return false;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    Message.log(Level.SEVERE, "Error closing database connection");
                }
            }
        }
        return rowsChanged > 0;
    }

    public static ResultSet executeQuery(String query) {
    	ResultSet result;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (Throwable t) {
        	t.printStackTrace();
            ExceptionHandler.handle(t);
            if (reconnect()) return executeQuery(query);
            else return null;
        }  
    return result;
    }
    
    public static Statement CreateStatement()
    {
    	try {
			return connection.createStatement();
		} catch (SQLException e) {
			Message.log(Level.SEVERE, "Error closing database connection");
		}
		return null;
    }

    /**
     * Closes the database connection and cleans up any leftover instances to prevent memory leaks
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            Message.log(Level.SEVERE, "Error closing database connection");
        }
        connection = null;
    }

    /**
     * Checks if the database connection is safe to use
     *
     * @return <b>true</b> if the connection is closed, <b>false</b> if it is open.
     */
    public static boolean isClosed() {
        return connection == null;
    }

    /**
     * Returns the connection instance
     *
     * @return Connection instance
     */
    public static Connection getConnection() {
        return connection;
    }
}