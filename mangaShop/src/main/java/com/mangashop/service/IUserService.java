package com.mangashop.service;

import com.mangashop.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    public void insertUser(User user) throws SQLException;

    public User selectUser(int id);

    public List<User> selectAllUsers();
//    public User loginAdmin(String phone, String password);
    public boolean deleteUser(int id) throws SQLException;
    public boolean updateUser(User user) throws SQLException;
    public User existByPhone (String phone) throws  SQLException ;
    public User exitsByEmail (String email) throws  SQLException ;
    public User existByName (String name) throws SQLException ;

}
