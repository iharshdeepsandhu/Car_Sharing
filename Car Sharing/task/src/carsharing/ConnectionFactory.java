package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    String dbName;
    static Statement statement;
    static Connection connection;

    public ConnectionFactory(String dbName) {
        this.dbName = dbName;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        ConnectionFactory.statement = statement;
    }

    public static void setConnection(Connection connection) {
        ConnectionFactory.connection = connection;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public Statement getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName ("org.h2.Driver");
            Connection connection = DriverManager.getConnection ("jdbc:h2:.\\src\\carsharing\\db\\" + dbName);
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            return statement;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public static void closeConnection() throws SQLException {
        statement.close();
        connection.close();


    }
}
