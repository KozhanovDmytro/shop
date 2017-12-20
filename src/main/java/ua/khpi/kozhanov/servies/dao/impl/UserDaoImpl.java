package ua.khpi.kozhanov.servies.dao.impl;

import ua.khpi.kozhanov.servies.dao.UserDao;
import ua.khpi.kozhanov.servies.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {
    private int idUser;
    @Override
    public boolean save(String login, String password, String email) {
        boolean result;
        String addUser = "INSERT INTO users SET " +
                "login='" + login + "', password='" + password + "', email='" + email + "'";
        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            result = statement.execute(addUser);
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int check(String login, String password) {
        idUser = -1;
        String query = "select * from users;";
        try {
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultFromSelect = statement.executeQuery(query);

            //checking student in DB
            while(resultFromSelect.next()) {
                int numUser = resultFromSelect.getInt("id");
                String loginUser = resultFromSelect.getString("login");
                String passwordUser = resultFromSelect.getString("password");

                try {
                    if (loginUser.equals(login) && passwordUser.equals(password)) {
                        idUser = numUser;
                        resultFromSelect.close();
                        break;
                    }
                } catch(NullPointerException e){}
            }

            connection.close();
            if (connection.isClosed()) {
                System.out.println("The connection with DB is closed!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return idUser;
        }
        return idUser;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean isExist(String login) {
        String query = "SELECT * FROM users;";
        boolean result = false;
        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                if(resultSet.getString("login").equals(login))
                    result = true;
            }
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getId() {
        return idUser;
    }

    @Override
    public String getLogin() {
        String query = "SELECT * FROM users WHERE id=" + idUser + ";";
        String login = "";
        try{
            Connection connection = JdbcUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            login = resultSet.getString("login");
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return login;
    }
}
