package vn.myclass.controller.admin;

import vn.myclass.command.UserCommand;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.dto.CheckLogin;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FormUtil;
import vn.myclass.core.web.utils.SingletonServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/login.html", "/logout.html"})
public class LoginController extends HttpServlet {
    ResourceBundle bundle = ResourceBundle.getBundle("ResourcesBundle");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals(WebConstant.LOGIN)) {
            request.getRequestDispatcher("/views/web/login.jsp").forward(request, response);
        } else if (action.equals(WebConstant.LOGOUT)) {
            SessionUtil.getInstance().remove(request, WebConstant.LOGIN_NAME);
           response.sendRedirect("/home.html");
           return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserCommand command = FormUtil.populate(UserCommand.class, request);
        UserDTO pojo = command.getPojo();
        if (pojo != null) {
            CheckLogin login = SingletonServiceUtil.getUserServiceInstance().checkLogin(pojo.getName(), pojo.getPassword());
            if (login.isUserExist()) {
                SessionUtil.getInstance().putValue(request, WebConstant.LOGIN_NAME, pojo.getName());
                if (login.getRoleName().equals(WebConstant.ROLE_ADMIN)) {
                    response.sendRedirect("/admin-home.html");
                    return;
                } else {
                    response.sendRedirect("/home.html");
                    return;
                }
            } else {
                request.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
                request.setAttribute(WebConstant.MESSAGE_RESPONSE, "Username or Password is invalid");
                request.getRequestDispatcher("/views/web/login.jsp").forward(request, response);
            }
        }
    }
}
