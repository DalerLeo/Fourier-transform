/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sasv2.pkg1;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author dalerleo
 */
public class DataBase {
    private Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaTest","root", "");
    private static Connection externalConnection;
    private Statement statement;

    DataBase() throws SQLException {
        statement = connection.createStatement();
        externalConnection = connection;
    }

    int execute(String query, boolean ret_id) throws SQLException {
        statement = connection.createStatement();
        try {
            statement.execute(query);
            ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid()");
            if (ret_id) {
                resultSet.next();
                return resultSet.getInt(1);
            }
            return 0;
        } finally {
            closeConnection();
        }
    }


    ArrayList<String> get_data(String query, String col1, String col2) throws SQLException {
        statement = connection.createStatement();
        ArrayList<String> ids = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String firstCol = rs.getString(col1);
                ids.add(firstCol);
            }
        } finally {
            closeConnection();
        }
        return ids;

    }

    ArrayList<String> get_music(String query) throws SQLException {
        statement = connection.createStatement();
        ArrayList<String> ids = new ArrayList<String>();
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String firstCol = rs.getString("location");
                ids.add(firstCol);
            }
        } finally {
            closeConnection();
        }
        return ids;

    }

    @Override
    public String toString() {
        return "The database connection is successful";
    }

    private boolean closeConnection() throws SQLException {
        this.statement.close();
        return this.statement.isClosed();
    }

    public PreparedStatement preparedStatement(String preparedQuery) throws SQLException {
        return connection.prepareStatement(preparedQuery);
    }
}
