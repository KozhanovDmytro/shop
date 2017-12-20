package ua.khpi.kozhanov.servlets;

import ua.khpi.kozhanov.servies.dao.impl.ProductDaoImpl;
import ua.khpi.kozhanov.servies.util.JdbcUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Created by PC on 11.12.2017.
 */
@WebServlet("/basket")
public class ShowBasket extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("idUser") == null) {
            response.sendRedirect("/login");
            return;
        }
        int count = 0;
        int amount = 0;
        String countQuery = "SELECT count(*) FROM internet_shop.basket;";
        String queryBasket = "select basket.idUser, products.id, products.`name`, products.description, products.popular, products.discount_amount, products.price, products.image\n" +
                "\tfrom basket\n" +
                "    inner join products\n" +
                "\t\ton basket.idProduct = products.id\n" +
                "\tWHERE idUser = "+ request.getSession().getAttribute("idUser") + ";";
        LinkedList<ProductDaoImpl> products = new LinkedList();
        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryBasket);

            while(resultSet.next()){
                products.add(new ProductDaoImpl(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("popular"),
                        resultSet.getInt("price"),
                        resultSet.getInt("discount_amount"),
                        resultSet.getString("image")));

                    if(resultSet.getInt("discount_amount") != 0)
                        amount += resultSet.getInt("discount_amount");
                    else
                        amount += resultSet.getInt("price");

            }
            resultSet.close();

            resultSet = statement.executeQuery(countQuery);
            resultSet.next();
            count = resultSet.getInt("count(*)");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(count < 100)
            request.setAttribute("count", count);
        else
            request.setAttribute("count", "+99");
        request.setAttribute("products", products);
        request.setAttribute("amount", amount);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/basket.jsp");
        dispatcher.forward(request, response);

    }
}
