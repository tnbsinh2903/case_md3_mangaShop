package com.mangashop.service;

import com.mangashop.model.Product;
import com.mangashop.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IProductService {

    public void insertProduct(Product product) throws SQLException;

    public Product selectProductById(int id);

    public List<Product> selectAllProduct();
    //    public User loginAdmin(String phone, String password);
    public boolean deleteProduct(int id) throws SQLException;
    public boolean updateProduct(Product product) throws SQLException;
    public Product existByName (String name) throws SQLException;
    public Product existByImage (String images) throws  SQLException ;
//    public User exitsByEmail (String email) throws  SQLException ;
}
