package com.mangashop.controller;

import com.mangashop.model.Category;
import com.mangashop.model.Role;
import com.mangashop.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    break;
                default:
                    listCategory(req, resp);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    private void listCategory(HttpServletRequest req ,HttpServletResponse resp ) throws ServletException, IOException {
        List<Category> categoryList = categoryService.selectCategory();
        req.setAttribute("categoryList",categoryList);

    }

}
