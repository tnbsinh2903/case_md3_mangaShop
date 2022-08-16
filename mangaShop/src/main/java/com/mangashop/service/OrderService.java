package com.mangashop.service;

import com.mangashop.model.Order;
import com.mangashop.model.OrderItem;
import com.mangashop.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private String jdbcURL = "jdbc:mysql://localhost:3306/manga_shop?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456789";

    private static final String SELECT_ALL_ORDER = "select * from order ";
    private static final String SELECT_ORDER_BY_ID = "select id, name,price,quantity,total,userbuy from orderitem where id = ? ";
    private static final String ADD_ORDER = "insert into orderitem (name,price, quantity,total,userbuy) values (?,?,,?,?,?);";
//    private static final String DELETE_PRODUCT = "delete from product where idPro = ?";
//    private static final String UPDATE_Product = "update product set namePro = ?,image = ?,price = ? , quantity= ?, idCate = ?  where idPro = ?;";

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

//    public List<OrderItem> selectAllOrder(){
//        List<OrderItem> listOrder = new ArrayList<>();
//        try (Connection connection = connection();
//             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDER)){
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()){
//                int id = rs.getInt("id");
//                double price = rs.getDouble("price");
//                int quantity = rs.getInt("quantity");
//                int order = rs.getInt("idOrder");
//                double total = rs.getDouble("total");
//                listOrder.add(new OrderItem(id,price,quantity,order,total));
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return listOrder;
//    }

    public OrderItem selectById (int id){
        OrderItem orderItem = null;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID);){
           statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
//                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                int userBuy = rs.getInt("userbuy");
                orderItem = new OrderItem(id,name,price,quantity,userBuy,total);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderItem;
    }
    public void add_order(OrderItem orderItem) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(ADD_ORDER)) {
//            statement.setString(1, orderItem.getName());
//            statement.setString(2, orderItem.getImage());
            statement.setDouble(1, orderItem.getPrice());
            statement.setInt(2, orderItem.getQuantity());
            statement.setDouble(4, orderItem.getTotal());
            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
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


}
