package com.mangashop.controller;

import com.mangashop.model.User;
import com.mangashop.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html/charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        RequestDispatcher dispatcher = req.getRequestDispatcher("login/login.jsp");
        dispatcher.forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
//        int id = Integer.parseInt(req.getParameter("id"));
        String phone = req.getParameter("phone");
        String pass = req.getParameter("pass");
        UserService userService = new UserService();
        try {
            User account = userService.login(phone,pass);

            if (account == null){
                req.setAttribute("message","Wrong !! Phone or password Incorrect !!!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("login/login.jsp");
                dispatcher.forward(req,resp);
            }else {

                HttpSession session = req.getSession();

                session.setAttribute("acc",account);
                resp.sendRedirect("/products");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
