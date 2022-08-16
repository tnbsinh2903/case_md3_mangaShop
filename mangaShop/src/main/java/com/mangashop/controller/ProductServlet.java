package com.mangashop.controller;

import com.mangashop.model.*;
import com.mangashop.service.CategoryService;
import com.mangashop.service.OrderService;
import com.mangashop.service.ProductService;
import com.mangashop.service.RoleService;
import sun.misc.FloatingDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiConsumer;

@WebServlet(urlPatterns = "/products", name = "ProductServlet")
public class ProductServlet extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;
    private OrderService orderService;
    private String error = "";

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
        orderService = new OrderService();
        if (this.getServletContext().getAttribute("categoryList") == null) {
            this.getServletContext().setAttribute("categoryList", categoryService.selectCategory());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                    showFormInsert(req, resp);
                    break;
                case "edit":
                    showFormEdit(req, resp);
                    break;
                case "delete":
                    deleteProduct(req, resp);
                    break;
                case "addCart":
                    addCart(req, resp);
                default:
//                    listProduct(req, resp);
                    listUserPaging(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showFormInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        req.setAttribute("product", product);
        System.out.println(product);
        RequestDispatcher dispatcher = req.getRequestDispatcher("product/create.jsp");
        dispatcher.forward(req, resp);
    }

    private void showFormEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product products = productService.selectProductById(id);
        request.setAttribute("product", products);
        System.out.println(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
        dispatcher.forward(request, response);
    }


    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        productService.deleteProduct(id);
        resp.sendRedirect("/products");
    }

    public void listProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Product> productList = productService.selectAllProduct();
        request.setAttribute("productList", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product/list.jsp");
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
                    insertProduct(req, resp);
                    break;
                case "edit":
                    updateProduct(req, resp);
                    break;
//                case "addCart":
//                    addCart(req, resp);
                default:
                    listProduct(req, resp);
//                    listUserPaging(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    public void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Product product = new Product();
        boolean flag = true;
        Map<String, String> hashMap = new HashMap<String, String>();  // Luu lai truong nao bi loi va loi gi
        try {
            int id = Integer.parseInt(request.getParameter("idPro"));
            product.setId(id);

            String name = request.getParameter("namePro");
            product.setName(name);

            double price = Double.parseDouble(request.getParameter("price"));
            product.setPrice(price);

            int quantity = Integer.parseInt(request.getParameter("quantity"));
            product.setQuantity(quantity);

            String image = request.getParameter("image");
            product.setImage(image);

            product.setCategory(Integer.parseInt(request.getParameter("idCate")));
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
            if (!constraintViolations.isEmpty()) {
//                errors = "<ul>";
//                for (ConstraintViolation<Product> constraintViolation : constraintViolations) {
//                    errors += "<li>"  + constraintViolation.getMessage()
//                            + "</li>";
//                }
//                errors += "</ul>";
                request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                request.setAttribute("product", product);

                List<Category> categoryList = categoryService.selectCategory();
                request.setAttribute("categoryList", categoryList);
                System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                request.getRequestDispatcher("product/create.jsp").forward(request, response);
            } else {
                if (productService.existByName(name) != null) {
                    flag = false;
                    hashMap.put("namePro", "Name exits in Database !");
                }
                if (quantity > 1000 || quantity < 0) {
                    flag = false;
                    hashMap.put("quantity", "SỐ lượng phải lớn hơn 0 và bé hơn 1000");
                }
                if (price < 0 || price > 1000000) {
                    flag = false;
                    hashMap.put("price", "Gía Phải lớn hơn 0 và nhỏ hơn 1 triệu");
                }
                if (flag) {
                    // Create user susscess
//                    product = new Product(name, price, quantity, image, category);
                    productService.insertProduct(product);
                    Product p = new Product();
                    request.setAttribute("success", "Thêm mới thành công");
                    request.setAttribute("product", p);
                    request.getRequestDispatcher("product/create.jsp").forward(request, response);
                } else {
                    // Error : Email exits in database
                    // Error: Country invalid
                    error = "<ul>";
                    // One more field has error
                    hashMap.forEach(new BiConsumer<String, String>() {
                        @Override
                        public void accept(String keyError, String valueError) {
                            error += "<li>" + valueError + "</li>";
                        }
                    });
                    error += "</ul>";

                    request.setAttribute("product", product);
                    request.setAttribute("error", error);

                    System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                    request.getRequestDispatcher("product/create.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + product);
            error = "<ul>";
            error += "<li>" + " Input Not Empty" + "</li>";
            error += "</ul>";

            request.setAttribute("product", product);
            request.setAttribute("error", error);
            request.getRequestDispatcher("product/create.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }

    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws
            SQLException, IOException, ServletException {
        Product product = new Product();
        boolean flag = true;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        try {
            int id = Integer.parseInt(request.getParameter("idPro"));
//            product.setId(Integer.parseInt(request.getParameter("idPro")));


            Product listPro = productService.getName(id);
            String tem1 = listPro.getName();
            String name = request.getParameter("namePro");
            product.setName(name);

            double price = Double.parseDouble(request.getParameter("price"));
            product.setPrice(price);

            int quantity = Integer.parseInt(request.getParameter("quantity"));
            product.setQuantity(quantity);

            String tem2 = listPro.getImage();
            String image = request.getParameter("image");
            product.setImage(image);

            int category = Integer.parseInt(request.getParameter("idCate"));
            product.setCategory(category);
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

            if (!constraintViolations.isEmpty()) {
//                errors = "<ul>";
//                for (ConstraintViolation<Product> constraintViolation : constraintViolations) {
//                    errors += "<li>" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()
//                            + "</li>";
//                }
//                errors += "</ul>";
                request.setAttribute("errors", getErrorFromContraint(constraintViolations));
                request.setAttribute("product", product);
//                request.setAttribute("errors", errors);

                List<Category> categoryList = categoryService.selectCategory();
                request.setAttribute("categoryList", categoryList);
                System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                request.getRequestDispatcher("/product/edit.jsp").forward(request, response);
            } else {
                String name1 = request.getParameter("namePro");
                if (tem1.equals(name1)) {
                    product.setName(tem1);
                    flag = true;
                } else {
                    if (productService.existByName(name) != null) {
                        flag = false;
                        hashMap.put("namePro", "Name exits in Database !");
                    }
                }
                String image1 = request.getParameter("image");
                if (tem2.equals(image1)) {
                    product.setImage(tem2);
                    flag = true;
                } else {
                    if (productService.existByImage(image) != null) {
                        flag = false;
                        hashMap.put("image", "Image exits in Database !");
                    }
                }
                if (quantity < 0 || quantity > 1000) {
                    flag = false;
                    hashMap.put("quantity", "Quantity must than better 0 và less than 1000");
                }
                if (price < 0 || price > 1000000) {
                    flag = false;
                    hashMap.put("price", "Gía Phải lớn hơn 0 và nhỏ hơn 1 triệu");
                }
                if (flag) {
                    // update user susscess
                    product = new Product(id, name, price, quantity, image, category);
                    productService.updateProduct(product);
//                        User u = new User();
                    System.out.println(product);
                    request.setAttribute("success", "Thay đổi thành công");
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("product/edit.jsp").forward(request, response);
                } else {
                    error = "<ul>";
                    hashMap.forEach(new BiConsumer<String, String>() {
                        @Override
                        public void accept(String keyError, String valueError) {
                            error += "<li>" + valueError + "</li>";
                        }
                    });
                    error += "</ul>";
                    request.setAttribute("product", product);
                    request.setAttribute("error", error);

                    System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                    request.getRequestDispatcher("/product/edit.jsp").forward(request, response);
                }
            }
        } catch (
                NumberFormatException ex) {
            error = "<ul>";
            error += "<li>" + " Input Not Empty !" + "</li>";
            error += "</ul>";
            request.setAttribute("product", product);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/product/edit.jsp").forward(request, response);
        } catch (
                Exception ex) {
        }
    }

    private void addCart(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        int idCountry = Integer.parseInt(request.getParameter("idCountry"));
//        User newUser = new User(name, email, idCountry);
//        userDAO.insertUser(newUser);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
//        dispatcher.forward(request, response);
//        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.selectProductById(id);
        User user = new User();
//        if (session.getAttribute("order") == null) {
//            String name = req.getParameter("namePro");

            OrderItem orderItem = new OrderItem();
            String name = req.getParameter("namePro");
            double price = Double.parseDouble(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            double total = price * quantity;
            int userbuy =  Integer.parseInt(req.getParameter("name"));
            OrderItem newOrder = new OrderItem(name,price, quantity,userbuy,total);
            orderService.add_order(newOrder);
            req.setAttribute("order", orderItem);
            req.setAttribute("order", newOrder);
//        } else {
//
//        }
        req.getRequestDispatcher("cart/carts.jsp").forward(req, resp);

    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<Product>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<Product> c : constraintViolations) {
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
            ServletException, IOException {
        int page = 1;
        int recordsPerPage = 3;
        String q = "";
        int idCate = -1;
        if (request.getParameter("q") != null) {
            q = request.getParameter("q");
        }
        if (request.getParameter("idCate") != null) {
            idCate = Integer.parseInt(request.getParameter("idCate"));
        }
        if (request.getParameter("page") != null) page = Integer.parseInt(request.getParameter("page"));
        List<Product> productList = productService.selectUsersPaging((page - 1) * recordsPerPage, recordsPerPage, q, idCate);
        int noOfRecords = productService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        System.out.println("sout" + noOfPages + "recod" + noOfRecords);
        request.setAttribute("productList", productList);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("q", q);
//        request.setAttribute("idrole", idrole);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }
}
