package ua.khpi.kozhanov.servlets.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by PC on 13.12.2017.
 */
@WebServlet("/logOut")
public class LoginOut extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("idUser");
        request.getSession().removeAttribute("login");
        response.sendRedirect("/login");
    }
}
