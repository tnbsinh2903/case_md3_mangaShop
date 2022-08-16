package com.mangashop.service;

import com.mangashop.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    private String jdbcURL = "jdbc:mysql://localhost:3306/manga_shop?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456789";

    protected Connection connection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


    private int noOfRecords;

    private static final String INSERT_USERS_SQL = "insert into user (name,phone,password,email,address,idRole) VALUES (?,?,?,?,?,?);";
    private static final String SELECT_USER_BY_ID = "select id,name,phone,password,email,address,idRole from user where id = ?";
    private static final String SELECT_ALL_USERS = "select * from user";
    private static final String DELETE_USERS_SQL = "delete from user where id = ? ";
    private static final String UPDATE_USERS_SQL = "update user set name = ?,phone = ?,password = ? , email= ?, address = ?, idRole = ? where id = ?;";
    private static final String SELECT_USER_BY_EMAIL = "select u.id,u.name,u.phone,u.password,u.email,u.address,u.idRole \n" +
            "\tfrom user as u inner join role as r where u.email = ?  and u.idRole = r.id;";
    private static final String SELECT_USER_BY_PHONE = "select u.id,u.name,u.phone,u.password,u.email,u.address,u.idRole \n" +
            "\tfrom user as u inner join role as r where u.phone = ?  and u.idRole = r.id;";
    private static final String EXIST_BY_NAME = " select u.id,u.name,u.phone,u.password,u.email,u.address,u.idRole \n" +
            "from user as u inner join role as r where u.name = ? and u.idRole = r.id";

    private static final String SELECT_NAME = "select name from user where name = ? ";

    @Override
    public User selectUser(int id) {
        User user = null;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int role = Integer.parseInt(rs.getString("idRole"));
                user = new User(id, name, phone, password, email, address, role);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int role = rs.getInt("idRole");
                users.add(new User(id, name, phone, password, email, address, role));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());
                preparedStatement.setInt(6, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getAddress());
            statement.setInt(6, user.getRole());
            statement.setInt(7, user.getId());
            rowUpdate = statement.executeUpdate() > 0;
        }
        return rowUpdate;
    }

    @Override
    public User existByPhone(String phone) throws SQLException {
        User user = null;
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_PHONE);) {
            preparedStatement.setString(1, phone);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                int role = rs.getInt("idRole");
                user = new User(id, name, phone, password, email, address, role);
                return user;
            }
            return null;
        } catch (SQLException e) {
            printSQLException(e);
            return null;

        }
    }

    @Override
    public User exitsByEmail(String emails) throws SQLException {
        User user = null;
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);) {
            preparedStatement.setString(1, emails);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String password = rs.getString("password");
//                String email = rs.getString("email");
                String address = rs.getString("address");
                int role = rs.getInt("idRole");
                user = new User(id, name, phone, password, emails, address, role);

                return user;
            }
            return null;
        } catch (SQLException e) {
            printSQLException(e);
            return null;

        }
    }
    @Override
    public User existByName(String names) throws SQLException {
        User user = null;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(EXIST_BY_NAME)) {
            statement.setString(1, names);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int role = rs.getInt("idRole");
                user = new User(id, name, phone, password, email, address, role);
                return user;
            }
            return null;
        } catch (SQLException e) {
            printSQLException(e);
            return null;
        }
    }
    public List<User> selectUsersPaging(int offset, int noOfRecords) {
        String query = "select SQL_CALC_FOUND_ROWS * from user limit " + offset + ", " + noOfRecords;
        List<User> list = new ArrayList<User>();
        User user = null;
        Statement stmt = null;
        Connection connection = null;
        try {
            connection = connection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getInt("idRole"));
                list.add(user);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public List<User> selectUsersPaging(int offset, int noOfRecords, String q, int idrole) {
        List<User> list = new ArrayList<User>();
        User user = null;
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = connection();
            if (idrole != -1) {
                String query = "select SQL_CALC_FOUND_ROWS * from user where name like ? and idRole = ? limit "
                        + offset + ", " + noOfRecords;
                stmt = connection.prepareStatement(query);
                stmt.setString(1, '%' + q + '%');
                stmt.setInt(2, idrole);
            } else {
                if (idrole == -1) {
                    String query = "select SQL_CALC_FOUND_ROWS * from user where name like ? limit "
                            + offset + ", " + noOfRecords;
                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, '%' + q + '%');
                }
            }
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getInt("idRole"));
                list.add(user);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

//    public User searchByName(String name) {
//        User user = null;
//        String query = "SELECT * FROM user where name like ?";
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
//            preparedStatement.setString(1, "%" + name + "%");
//            System.out.println(preparedStatement);
//            // Step 3: Execute the query or update query
//            ResultSet rs = preparedStatement.executeQuery();
//            // Step 4: Process the ResultSet object.
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String email = rs.getString("email");
//                String password = rs.getString("password");
//                String address = rs.getString("address");
//                String phone = rs.getString("phone");
//                int role = rs.getInt("idRole");
//                user = new User(id, name, phone, password, email, address, role);
//                return user;
//            }
//            return null;
//        } catch (SQLException e) {
//            printSQLException(e);
//            return null;
//
//        }
//    }

//    public User getPhone (int id) throws SQLException {
//        Connection connection = connection();
//        String query = "select phone from user where id = ?" ;
//        PreparedStatement statement = connection.prepareStatement(query);
//        statement.setInt(1,id);
//        ResultSet rs = statement.executeQuery();
//        while (rs.next()){
//            return new User(rs.getString("phone")) ;
//        }
//        connection.close();
//        return null;
//    }


    public User getName(int id) throws SQLException {
        Connection connection = connection();
        String query = "select * from user where id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            return new User(rs.getString("name"),
                    rs.getString("phone"));
        }
        connection.close();
        return null;
    }

    public User login(String phone, String pass) throws SQLException {
        Connection connection = connection();
        String query = "Select * from user where phone = ? and password = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, phone);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    phone,
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getInt("idRole")
            );
        }
        return null;
    }
}
