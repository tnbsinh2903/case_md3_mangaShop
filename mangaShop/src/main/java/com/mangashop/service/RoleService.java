package com.mangashop.service;

import com.mangashop.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleService {

    public static final String SELECT_ALL_ROLE = "select * from role";

    private String jdbcURL = "jdbc:mysql://localhost:3306/manga_shop?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456789";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Role> selectRole() {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLE)) {
            System.out.println(this.getClass() + " selectCountry: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                roles.add(new Role(id,name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return roles;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
