package ua.khpi.kozhanov.servlets.User;

import ua.khpi.kozhanov.servies.util.JdbcUtil;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by PC on 16.12.2017.
 */
@WebServlet("/addToHistory")
public class AddToHistory extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryBasket = "select basket.idUser, basket.idProduct, products.discount_amount, products.price" +
                "\tfrom basket\n inner join products " +
                "on basket.idProduct = products.id\n" +
                "\tWHERE idUser = "+ request.getSession().getAttribute("idUser") + ";";
        LinkedList products = new LinkedList();
        LinkedList prices = new LinkedList();
        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryBasket);

            while (resultSet.next()){
                products.add(resultSet.getInt("idProduct"));
                if(resultSet.getInt("discount_amount") == 0)
                    prices.add(resultSet.getInt("price"));
                else
                    prices.add(resultSet.getInt("discount_amount"));
            }
            for (int i = 0; i < products.size(); i++) {
                String deleteSelected = "DELETE FROM basket WHERE idProduct=" + products.get(i) + ";";
                statement.execute(deleteSelected);
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            for (int i = 0; i < products.size(); i++) {
                String addToHistory = "INSERT INTO history  (idUser, `time`, idProduct, price) VALUES" +
                        "("+ request.getSession().getAttribute("idUser") +", '" +
                        simpleDateFormat.format(date) + "', " + products.get(i) +", " + prices.get(i) + ");";
                        statement.execute(addToHistory);

            }
            resultSet.close();
            connection.close();
            response.sendRedirect("/products");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
