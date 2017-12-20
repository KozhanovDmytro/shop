package ua.khpi.kozhanov.servies.dao;

/**
 * Created by PC on 09.12.2017.
 */
public interface UserDao {

    boolean save(String login, String password, String email);

    int check(String login, String password);

    void delete(Long id);

    boolean isExist(final String login);

    int getId();

    String getLogin();

}