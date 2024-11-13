package org.example.patterndao;

import org.example.patterndao.persistence.PiattoDao;
import org.example.patterndao.persistence.RistoranteDao;

import java.sql.*;

public class DBManager {
    private static DBManager instance;

    private DBManager() {};
    private RistoranteDao ristoranteDao = null;
    private PiattoDao piattoDao = null;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    Connection connection = null;

    public Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            }
            catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public PiattoDao getPiattoDao() {
        return piattoDao;
    }

    public static void main(String[] args) {
        Connection conn = DBManager.getInstance().getConnection();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from utenti");
            if (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
