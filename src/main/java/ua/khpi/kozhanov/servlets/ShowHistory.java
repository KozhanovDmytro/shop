package ua.khpi.kozhanov.servlets;

import ua.khpi.kozhanov.servies.dao.impl.HistoryElement;
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
 * Created by PC on 16.12.2017.
 */
@WebServlet("/history")
public class ShowHistory extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("idUser") == null) {
            response.sendRedirect("/login");
            return;
        }
        int count = 0;
        String countQuery = "SELECT count(*) FROM internet_shop.basket;";

        String query = "select history.id, history.idUser, history.assessment, history.price, history.`time`, history.idProduct, products.`name`, products.description, products.image\n" +
                "\tfrom history\n" +
                "    inner join products\n" +
                "\t\ton history.idProduct = products.id\n" +
                "\tWHERE idUser = "+ request.getSession().getAttribute("idUser") + ";";
        LinkedList<HistoryElement> products = new LinkedList<HistoryElement>();

        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                products.add(new HistoryElement(resultSet.getInt("id"),
                        resultSet.getInt("idUser"),
                        resultSet.getInt("price"),
                        resultSet.getDate("time"),
                        resultSet.getInt("idProduct"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("image"),
                        resultSet.getBoolean("assessment")));
            }
            resultSet = statement.executeQuery(countQuery);
            resultSet.next();
            count = resultSet.getInt("count(*)");
            resultSet.close();
            connection.close();
            if(count < 100)
                request.setAttribute("count", count);
            else
                request.setAttribute("count", "+99");
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/history.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String estimate = request.getParameter("estimate");
        String index =  request.getParameter("index");

        if(Integer.parseInt(estimate) > 5) return;
        if(Integer.parseInt(estimate) < 1) return;

        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM history WHERE id=" + index + ";");
            resultSet.next();
            int product = resultSet.getInt("idProduct");
            statement.execute("INSERT INTO assessments SET idUser=" + request.getSession().getAttribute("idUser") +
                    ", idProduct=" + product + ", assessment=" + estimate + ";");

            resultSet = statement.executeQuery("SELECT * FROM assessments WHERE idProduct=" + product + ";");
            int sumProduct = 0;
            while (resultSet.next()){
                sumProduct += resultSet.getInt("assessment");
            }
            resultSet = statement.executeQuery("SELECT count(*)FROM assessments WHERE idProduct=" + product + ";");
            resultSet.next();
            int count = resultSet.getInt("count(*)");
            int result = sumProduct/count;
            statement.execute("UPDATE products SET popular=" + result + " WHERE id=" + product + ";");
            statement.execute("UPDATE history SET assessment=1 WHERE id=" + index + " and idUser=" + request
                    .getSession()
                                                            .getAttribute("idUser"));
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/history");
    }
}
