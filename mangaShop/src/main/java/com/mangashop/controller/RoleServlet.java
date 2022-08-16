package com.mangashop.controller;

import com.mangashop.model.Role;
import com.mangashop.service.RoleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoleServlet extends HttpServlet {
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        roleService = new RoleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = " ";
        }
        try {
            switch (action) {
                case "create":
                    break;
                default:
                    listRole(req, resp);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
    private void listRole(HttpServletRequest req ,HttpServletResponse resp ) throws ServletException, IOException {
        List<Role> roleList = roleService.selectRole();
        req.setAttribute("roleList",roleList);

    }


}
