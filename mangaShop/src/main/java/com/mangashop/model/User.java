package com.mangashop.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class User {

    private int id;
    //    private String userName;
//    private String password;
    private String name;
    private String phone;
    private String password;
    private String email;
    private String address;
    private int Role;

    public User() {
    }


    public User(int id, String name, String phone, String password, String email, String address, int role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.address = address;
        Role = role;
    }

    public User(String name, String phone, String password, String email, String address, int role) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.address = address;
        Role = role;
    }

//     public User(String phone) {
//        this.phone = phone;
//    }

    public User(String name,String phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "Name not empty")
    @Pattern(regexp = "^([A-Z]+[a-z]*[ ]?)+$", message = "Format name not right")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Phone not empty")
    @Pattern(regexp = "((84|0[1|2|3|4|5|6|7|8|9])+([0-9]{8})\\b)", message = "Format phone not right")
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotEmpty(message = "Password not empty")
    @Pattern(regexp = "^([a-zA-Z0-9]{8,})", message = "Format password not right")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Email not empty")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$", message = "Format mail not right")
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Address not empty")
    @Pattern(regexp = "^([A-Z]+[a-z]*[ ]?)+$", message = "Format Address not right")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", Role=" + Role +
                '}';
    }
}
