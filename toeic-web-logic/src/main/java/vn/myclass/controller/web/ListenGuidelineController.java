package vn.myclass.controller.web;

import org.apache.commons.lang.StringUtils;
import vn.myclass.command.ListenGuidelineCommand;
import vn.myclass.core.dto.ListenGuidelineDTO;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FormUtil;
import vn.myclass.core.web.utils.RequestUtil;
import vn.myclass.core.web.utils.SingletonServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/danh-sach-huong-dan-nghe.html", "/noi-dung-bai-huong-dan-nghe.html"})
public class ListenGuidelineController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ListenGuidelineCommand command = FormUtil.populate(ListenGuidelineCommand.class, request);
        if (request.getParameter("listenguidelineid") != null) {
            String litenGuidelineStr = request.getParameter("listenguidelineid");
            ListenGuidelineDTO existListenGuideline = SingletonServiceUtil.getListenGuidelineServiceInstance().findByListenGuidelineId("listenGuidelineId", Integer.parseInt(litenGuidelineStr));
            command.setPojo(existListenGuideline);
            request.setAttribute(WebConstant.FORM_ITEM, command);
            request.getRequestDispatcher("/views/web/listenguideline/detail.jsp").forward(request, response);
        } else {
            executeSearchListenGuideline(request, command);
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            request.getRequestDispatcher("/views/web/listenguideline/list.jsp").forward(request, response);
        }
    }

    private void executeSearchListenGuideline(HttpServletRequest request, ListenGuidelineCommand command) {
        Map<String, Object> properties = buildMapProperties(command);
        command.setMaxPageItems(3);
        RequestUtil.initSearchBeanManual(command);
        Object[] objects = SingletonServiceUtil.getListenGuidelineServiceInstance().findByProperty(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ListenGuidelineDTO>)objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPages((int) Math.ceil((double) command.getTotalItems() / command.getMaxPageItems()));
    }

    private Map<String, Object> buildMapProperties(ListenGuidelineCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(command.getPojo().getTitle())) {
            properties.put("title", command.getPojo().getTitle());
        }
        return properties;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
