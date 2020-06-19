package vn.myclass.core.web.utils;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import vn.myclass.core.web.command.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static void initSearchBean(HttpServletRequest request, AbstractCommand bean) {
        if(bean != null) {
            String sortExpression = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_SORT));
            String sortDirection = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            String pageStr = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer page = 1;
            if(StringUtils.isNotBlank(pageStr)){
                try {
                    page = Integer.valueOf(pageStr);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            bean.setPage(page);
            bean.setSortExpression(sortExpression);
            bean.setSortDirection(sortDirection);
            bean.setFirstItem((bean.getPage() - 1) * bean.getMaxPageItems());
        }
    }

    public static void initSearchBeanManual(AbstractCommand command) {
        if (command != null) {
            Integer page = 1;
            if (command.getPage() != 0) {
                page = command.getPage();
            }
            command.setPage(page);
            command.setFirstItem((command.getPage() - 1) * command.getMaxPageItems());
        }
    }
}
