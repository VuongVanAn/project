package vn.myclass.controller.admin;

import vn.myclass.core.common.utils.UploadUtil;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.WebCommonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/admin-upload.html")
public class UploadController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/upload.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ResourcesBundle");
        UploadUtil uploadUtil = new UploadUtil();
        Set<String> valueTitle = new HashSet<String>();
        Object[] objects = uploadUtil.writeOrUpdateFile(request, valueTitle, WebConstant.EXERCISE);
        boolean checkStatusUploadImage = (Boolean) objects[0];
        Map<String, String> mapMessage = buidMapRedirectMessage(resourceBundle);
        if (!checkStatusUploadImage) {
            WebCommonUtil.addRedirectMessage(request, "redirect_error", mapMessage);
        } else {
            WebCommonUtil.addRedirectMessage(request, "redirect_insert", mapMessage);
        }
        request.getRequestDispatcher("/views/admin/upload.jsp").forward(request, response);
    }

    private Map<String,String> buidMapRedirectMessage(ResourceBundle resourceBundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, "Upload file successfully");
        mapMessage.put(WebConstant.REDIRECT_ERROR, "Error Systems");
        return mapMessage;
    }
}
