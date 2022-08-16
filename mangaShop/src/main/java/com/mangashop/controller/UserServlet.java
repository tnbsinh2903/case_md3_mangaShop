package com.mangashop.controller;

import com.mangashop.model.Role;
import com.mangashop.model.User;
import com.mangashop.service.RoleService;
import com.mangashop.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiConsumer;

@WebServlet(urlPatterns = "/users")
public class UserServlet extends HttpServlet {


    private UserService userService;
    private RoleService roleService;
    private String error = "";

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        roleService = new RoleService();
        if (this.getServletContext().getAttribute("roleList") == null) {
            this.getServletContext().setAttribute("roleList", roleService.selectRole());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hello");

        resp.setContentType("text/html/charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewForm(req, resp);
                    break;
                case "edit":
                    showFormEdit(req, resp);
                    break;
                case "delete":
                    deleteUsers(req, resp);
                    break;
                default:
//                    listUser(req, resp);
                    listUserPaging(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create1.jsp");
        dispatcher.forward(request, response);
    }

    private void showFormEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User users = userService.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
        request.setAttribute("user", users);
        dispatcher.forward(request, response);
    }

    private void deleteUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect("/users");
//        List<User> listUser = userService.selectAllUsers();
//        request.setAttribute("listUser", listUser);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user/index.jsp");
//        dispatcher.forward(request, response);
    }

    public void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<User> listUser = userService.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html/charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(req, resp);
                    break;
                case "edit":
                    updateUser(req, resp);
                    break;
                default:
                    listUser(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    public void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        User user = new User();
        boolean flag = true;
        Map<String, String> hashMap = new HashMap<String, String>();  // Luu lai truong nao bi loi va loi gi
        try {
            user.setId(Integer.parseInt(request.getParameter("id")));
            String name = request.getParameter("name");
            user.setName(name);
            String phone = request.getParameter("phone");
            user.setPhone(phone);
            String password = request.getParameter("password");
            user.setPassword(password);

            String address = request.getParameter("address");
            user.setAddress(address);

            String email = request.getParameter("email");
            user.setEmail(email);
            int role = Integer.parseInt(request.getParameter("idRole"));
            user.setRole(role);
//            user.setId(Integer.parseInt(request.getParameter("idRole")));
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (!constraintViolations.isEmpty()) {
//                errors = "<ul>";
//                for (ConstraintViolation<User> constraintViolation : constraintViolations) {
//                    errors += "<li>"  + constraintViolation.getMessage() + "</li>";
////                    + constraintViolation.getPropertyPath() + " "
//                }
//                errors += "</ul>";
                request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                request.setAttribute("user", user);
//                request.setAttribute("errors", errors);
                List<Role> roleList = roleService.selectRole();
                request.setAttribute("roleList", roleList);
                System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                request.getRequestDispatcher("/user/create1.jsp").forward(request, response);
            } else {
                if (userService.existByName(name) != null) {
                    flag = false;
                    hashMap.put("name", "Name exits in Database !");
                }
                if (userService.existByPhone(phone) != null) {
                    flag = false;
                    hashMap.put("phone", "Phone exits in Database");
                }
                if (userService.exitsByEmail(email) != null) {
                    flag = false;
                    hashMap.put("email", "Email exits in Database");
                }
                if (flag) {
                    // Create user sussces
                    user = new User(name, phone, password, email, address, role);
                    userService.insertUser(user);
                    request.setAttribute("success", "Thêm User thành công");
                    User u = new User();
                    request.setAttribute("user", u);
                    request.getRequestDispatcher("user/create1.jsp").forward(request, response);
                } else {
                    error = "<ul>";
                    hashMap.forEach(new BiConsumer<String, String>() {
                        @Override
                        public void accept(String keyError, String valueError) {
                            error += "<li>" + valueError + "</li>";
                        }
                    });
                    error += "</ul>";
//                    request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                    request.setAttribute("user", user);
                    request.setAttribute("error", error);
                    System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                    request.getRequestDispatcher("/user/create1.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + user);
//            errors = "<ul>";
//            errors += "<li>" + "format not right" + "</li>";
//            errors += "</ul>";


            request.setAttribute("user", user);

//            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/user/create1.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        User user = new User();
        boolean flag = true;
        Map<String, String> hashMap = new HashMap<String, String>();  // Luu lai truong nao bi loi va loi gi
        try {

            int id = Integer.parseInt(request.getParameter("id"));
            user.setId(id);

            User userList = userService.getName(id);
            String temp1 = userList.getName();
            String name = request.getParameter("name");
            user.setName(name);

            String userList1 = userList.getPhone();
            String phone = request.getParameter("phone");
            user.setPhone(phone);

            String password = request.getParameter("password");
            user.setPassword(password);
//                user.setPassword(request.getParameter("password"));
            String address = request.getParameter("address");
            user.setAddress(address);
//                user.setAddress(request.getParameter("address"));
            String email = request.getParameter("email");
            user.setEmail(email);
            int role = Integer.parseInt(request.getParameter("idRole"));
            user.setRole(role);
//                user.setId(Integer.parseInt(request.getParameter("idRole")));
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (!constraintViolations.isEmpty()) {
//                errors = "<ul>";
//                for (ConstraintViolation<User> constraintViolation : constraintViolations) {
//                    errors += "<li>" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage() + "</li>";
//                }
//                errors += "</ul>";
                request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                request.setAttribute("user", user);

                List<Role> roleList = roleService.selectRole();
                request.setAttribute("roleList", roleList);
                request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
            } else {
                String name1 = request.getParameter("name");
                if (temp1.equals(name1)) {
                    user.setName(temp1);
                    flag = true ;
                } else {
                    if (userService.existByName(name) != null) {
                        flag = false;
                        hashMap.put("name", "Name exits in Database !");
                    }
                    String phone1 = request.getParameter("phone");
                    if (userList1.equals(phone1)) {
                        user.setPhone(userList1);
                        flag = true;
                    } else {
                        if (userService.existByPhone(phone) != null) {
                            flag = false;
                            hashMap.put("phone", "Phone exits in Database !");
                        }
                    }
                }
                if (flag) {
                    // update user susscess
                    user = new User(id, name, phone, password, email, address, role);
                    userService.updateUser(user);
//                        User u = new User();
                    System.out.println(user);
                    request.setAttribute("success", "Thay đổi thông tin User thành công");
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("user/edit.jsp").forward(request, response);
                } else {
                    // Error : Email exits in database
                    // Error: Country invalid
                    error = "<ul>";

                    hashMap.forEach(new BiConsumer<String, String>() {
                        @Override
                        public void accept(String keyError, String valueError) {
                            error += "<li>" + valueError + "</li>";
                        }
                    });
                    error += "</ul>";
                    request.setAttribute("error", error);
                    request.setAttribute("user", user);
                    System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                    request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException ex) {
//            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + user);
//            errors = "<ul>";
//            errors += "<li>" + "format not right" + "</li>";
//            errors += "</ul>";
//
//
//            request.setAttribute("errors", errors);
            request.setAttribute("user", user);

            request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
        } catch (Exception ex) {
        }

    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<User>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<User> c : constraintViolations) {
            if (hashMap.keySet().contains(c.getPropertyPath().toString())) {
                hashMap.get(c.getPropertyPath().toString()).add(c.getMessage());
            } else {
                List<String> listMessages = new ArrayList<>();
                listMessages.add(c.getMessage());
                hashMap.put(c.getPropertyPath().toString(), listMessages);
            }
        }
        return hashMap;
    }

    private void listUserPaging(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException   {
        int page = 1;
        int recordsPerPage = 5;
        String q = "";
        int idrole = -1;
        if (request.getParameter("q") != null) {
            q = request.getParameter("q");
        }
        if (request.getParameter("idrole") != null) {
            idrole = Integer.parseInt(request.getParameter("idrole"));
        }
        if (request.getParameter("page") != null) page = Integer.parseInt(request.getParameter("page"));
        List<User> listUser = userService.selectUsersPaging((page - 1) * recordsPerPage, recordsPerPage, q, idrole);
        int noOfRecords = userService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        System.out.println("sout" + noOfPages + "recod" + noOfRecords);
        request.setAttribute("listUser", listUser);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("q", q);
        request.setAttribute("idrole", idrole);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/index.jsp");
        dispatcher.forward(request, response);
    }
}
