package com.mangashop.service;

import com.mangashop.model.Product;
import com.mangashop.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {

    private String jdbcURL = "jdbc:mysql://localhost:3306/manga_shop?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456789";

    private static final String SELECT_ALL_PRODUCT = "select * from product order by idPro desc ";
    private static final String SELECT_PRODUCT_BY_ID = "select idPro, namePro, image,price,quantity,idCate from product where idPro = ? ";
    private static final String INSERT_PRODUCT = "insert into product (namePro, image,price,quantity,idCate) values (?,?,?,?,?);";
    private static final String DELETE_PRODUCT = "delete from product where idPro = ?";
    private static final String UPDATE_Product = "update product set namePro = ?,image = ?,price = ? , quantity= ?, idCate = ?  where idPro = ?;";
    private static final String EXIST_BY_NAME = " select p.idPro,p.namePro,p.image,p.price,p.quantity,p.idCate from product as p inner join category as c where p.namePro = ? and p.idCate = c.id";
    private int noOfRecords;

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


    public List<Product> selectAllProduct() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idPro");
                String name = rs.getString("namePro");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                int category = rs.getInt("idCate");
                productList.add(new Product(id, name, price, quantity, image, category));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return productList;
    }



    public Product selectProductById(int id) {
        Product product = null;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("namePro");
                String image = rs.getString("image");
                double price = Double.parseDouble(rs.getString("price"));
                int quantity = Integer.parseInt(rs.getString("quantity"));
                int category = Integer.parseInt(rs.getString("idCate"));
                product = new Product(id,name, price, quantity,image, category);
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertProduct(Product product) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getImage());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getCategory());
            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public boolean deleteProduct(int id) throws SQLException {
        boolean delete ;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT)) {
            statement.setInt(1, id);
            delete = statement.executeUpdate() > 0;
        }
        return delete ;
    }


    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdate;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_Product);) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getImage());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getCategory());
            statement.setInt(6, product.getId());
            rowUpdate = statement.executeUpdate() > 0;
        }
        return rowUpdate;
    }

    @Override
    public Product existByName(String names) throws SQLException {
        Product product = null;
        try (Connection connection = connection();
        PreparedStatement statement = connection.prepareStatement(EXIST_BY_NAME)){
            statement.setString(1,names);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("idPro");
                String name = rs.getString("namePro");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                int category = rs.getInt("idCate");
                product = new Product(id,name,image,price,quantity,category);
                return product;
            }
            return null;
        }catch (SQLException e){
            printSQLException(e);
            return null;
        }

    }

    @Override
    public Product existByImage(String images) throws SQLException {
        Product product = null;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(EXIST_BY_NAME)){
            statement.setString(1,images);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("idPro");
                String name = rs.getString("namePro");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                int category = rs.getInt("idCate");
                product = new Product(id,name,image,price,quantity,category);
                return product;
            }
            return null;
        }catch (SQLException e){
            printSQLException(e);
            return null;
        }
    }

    public Product getName(int id) throws SQLException {
        Connection connection = connection();
        String query = "select * from product where idPro = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            return new Product(rs.getString("namePro"),
                    rs.getString("image"));
        }
        connection.close();
        return null;
    }

    public List<Product> selectUsersPaging(int offset, int noOfRecords) {
        String query = "select SQL_CALC_FOUND_ROWS * from product limit " + offset + ", " + noOfRecords;
        List<Product> productList = new ArrayList<Product>();
        Product product = null;
        Statement stmt = null;
        Connection connection = null;
        try {
            connection = connection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("idPro"));
                product.setName(rs.getString("namePro"));
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity (rs.getInt("quantity"));
                product.setCategory(rs.getInt("idCate"));
                productList.add(product);
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
        return productList;
    }

    public List<Product> selectUsersPaging(int offset, int noOfRecords, String q, int idCate) {
        List<Product> productList = new ArrayList<Product>();
        Product product = null;
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = connection();
            if (idCate != -1) {
                String query = "select SQL_CALC_FOUND_ROWS * from product where namePro like ? and idCate = ? limit "
                        + offset + ", " + noOfRecords;
                stmt = connection.prepareStatement(query);
                stmt.setString(1, '%' + q + '%');
                stmt.setInt(2, idCate);
            } else {
                if (idCate == -1) {
                    String query = "select SQL_CALC_FOUND_ROWS * from product where namePro like ? limit "
                            + offset + ", " + noOfRecords;
                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, '%' + q + '%');
                }
            }
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("idPro"));
                product.setName(rs.getString("namePro"));
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity (rs.getInt("quantity"));
                product.setCategory(rs.getInt("idCate"));
                productList.add(product);
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
        return productList;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public Product searchByName(String namePro) {
        Product product = null;
        String query = "SELECT * FROM product where namePro like ?";
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, "%" + namePro + "%");
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("idPro");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                int category = rs.getInt("idCate");
                product = new Product(id, namePro, price, quantity,image, category);
                return product;
            }
            return null;
        } catch (SQLException e) {
            printSQLException(e);
            return null;

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
