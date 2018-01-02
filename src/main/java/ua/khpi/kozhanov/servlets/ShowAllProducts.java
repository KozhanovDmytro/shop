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
 * Created by PC on 10.12.2017.
 */
@WebServlet("/products")
public class ShowAllProducts extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("idUser") == null){
            response.sendRedirect("/login");
            return;
        }
        int count = 0;
        String countQuery = "SELECT count(*) FROM internet_shop.basket WHERE idUser=" + request.getSession().getAttribute("idUser") + ";";
        String productsQuery = "SELECT * FROM internet_shop.products;";

        LinkedList<ProductDaoImpl> products = new LinkedList();
        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(countQuery);
            resultSet.next();
            try {
                count = resultSet.getInt("count(*)");
            } catch (NullPointerException e){}
            resultSet.close();
            resultSet = statement.executeQuery(productsQuery);
            while(resultSet.next()){
                products.add(new ProductDaoImpl(resultSet.getInt("id"),
                                                resultSet.getString("name"),
                                                resultSet.getString("description"),
                                                resultSet.getInt("popular"),
                                                resultSet.getInt("price"),
                                                resultSet.getInt("discount_amount"),
                                                resultSet.getString("image")));
            }
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(count < 100)
            request.setAttribute("count", count);
        else
            request.setAttribute("count", "+99");
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/products.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
