package org.example.patterndao.controller.servelet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/doLogin")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username.equals("ciao@unical.it") && password.equals("ciao") ){
            req.getSession(true).setAttribute("username", username);
            resp.sendRedirect("/");
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("views/loginErrato.html");
            dispatcher.forward(req, resp);
        }

    }
}