package ua.khpi.kozhanov.servlets;

import ua.khpi.kozhanov.servies.dao.UserDao;
import ua.khpi.kozhanov.servies.dao.impl.UserDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by PC on 09.12.2017.
 */
@WebServlet("/login")
public class SignInToSystem extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("idUser") == null) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("/products");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        UserDao userDao = new UserDaoImpl();

        HttpSession session = request.getSession();
        if (userDao.check(login, password) != -1) {
            session.setAttribute("idUser", userDao.getId());
            session.setAttribute("login", userDao.getLogin());
            request.setAttribute("message", "Ok!");

            response.sendRedirect("/products");
            return;
        } else {
            request.setAttribute("error", "access is not allowed");

            doGet(request, response);
        }
    }
}
