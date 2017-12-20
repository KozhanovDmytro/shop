package ua.khpi.kozhanov.servlets.User;

import ua.khpi.kozhanov.servies.dao.UserDao;
import ua.khpi.kozhanov.servies.dao.impl.UserDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class UserRegistration extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("idUser") == null) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/registration.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("/products");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserDao userDao = new UserDaoImpl();
        String login = request.getParameter("login");
        if(!login.matches("\\w+")) {
            request.setAttribute("error", "The username must contain only English letters!");
            doGet(request, response);
            return;
        }
        if(!userDao.isExist(login)){
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String result = validation(email, password);
            request.setAttribute("error", result);
            if(result.equals("")){
                if(userDao.save(login, password, email)){
                    request.setAttribute("message", "Ok!");
                } else {
                    request.setAttribute("error", "Unknown error!");
                }
                response.sendRedirect("/login");
            } else {
                doGet(request, response);
            }
        } else{
            request.setAttribute("error", "This user is exist!");
            doGet(request, response);
        }
    }
    private String validation(String email, String password) {
        StringBuilder result = new StringBuilder();

//        check password
        if(!password.matches("\\w+"))
            result.append("The password must contain only English letters!\n");

//        check email
        if(!email.matches("([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)"))
            result.append("Incorrect email!\n");
        return result.toString();
    }
}
