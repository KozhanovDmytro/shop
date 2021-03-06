package ua.khpi.kozhanov.servlets.User;

import ua.khpi.kozhanov.servies.util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by PC on 10.12.2017.
 */
@WebServlet("/addToBasket")
public class AddToBasket extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String index = request.getParameter("index");
        String query = "INSERT INTO internet_shop.basket SET idUser=" +
                request.getSession().getAttribute("idUser") + ", idProduct=" + index + ";";
        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/products");
    }
}
