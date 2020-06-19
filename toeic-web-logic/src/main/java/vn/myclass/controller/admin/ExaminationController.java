package vn.myclass.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import vn.myclass.command.ExaminationCommand;
import vn.myclass.core.common.utils.ExcelPoiUtil;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.common.utils.UploadUtil;
import vn.myclass.core.dto.ExaminationDTO;
import vn.myclass.core.dto.ExaminationQuestionDTO;;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FormUtil;
import vn.myclass.core.web.utils.RequestUtil;
import vn.myclass.core.web.utils.SingletonServiceUtil;
import vn.myclass.core.web.utils.WebCommonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/admin-examination-list.html", "/ajax-admin-examination-edit.html",
        "/admin-examination-import-validate.html", "/admin-examination-import.html"})
public class ExaminationController extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());
    private final String SHOW_IMPORT_EXAMINATION = "show_import_examination";
    private final String READ_EXCEL = "read_excel";
    private final String VALIDATE_IMPORT = "validate_import";
    private final String LIST_EXAMINATION_IMPORT = "list_examination_import";
    private final String IMPORT_DATA = "import_data";
    ResourceBundle bundle = ResourceBundle.getBundle("ResourcesBundle");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExaminationCommand command = FormUtil.populate(ExaminationCommand.class, request);
        ExaminationDTO pojo = command.getPojo();
        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.REDIRECT_DELETE)) {
                List<Integer> ids = new ArrayList<Integer>();
                for (String item : command.getCheckList()) {
                    ids.add(Integer.parseInt(item));
                }
                Integer result = SingletonServiceUtil.getExaminationServiceInstance().delete(ids);
                if (result != ids.size()) {
                    command.setCrudaction(WebConstant.REDIRECT_ERROR);
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            RequestUtil.initSearchBean(request, command);
            Object[] objects = SingletonServiceUtil.getExaminationServiceInstance().findByProperty(map, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
            command.setListResult((List<ExaminationDTO>) objects[1]);
            command.setTotalItems(Integer.parseInt(objects[0].toString()));
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buildMapRedirectMessage(bundle);
                WebCommonUtil.addRedirectMessage(request, command.getCrudaction(), mapMessage);
            }
            request.getRequestDispatcher("/views/admin/examination/list.jsp").forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if (pojo != null && pojo.getExaminationId() != null) {
                command.setPojo(SingletonServiceUtil.getExaminationServiceInstance().findById(pojo.getExaminationId()));
            }
            request.setAttribute(WebConstant.FORM_ITEM, command);
            request.getRequestDispatcher("/views/admin/examination/edit.jsp").forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(SHOW_IMPORT_EXAMINATION)) {
            request.getRequestDispatcher("/views/admin/examination/importexamination.jsp").forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(VALIDATE_IMPORT)) {
            List<ExaminationQuestionDTO> examinationQuestionDTOS = (List<ExaminationQuestionDTO>) SessionUtil.getInstance().getValue(request, LIST_EXAMINATION_IMPORT);
            command.setExaminationQuestionDTOS(returnListExaminationImport(command, examinationQuestionDTOS, request));
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            request.getRequestDispatcher("/views/admin/examination/importexamination.jsp").forward(request, response);
        }
    }

    private List<ExaminationQuestionDTO> returnListExaminationImport(ExaminationCommand command, List<ExaminationQuestionDTO> examinationQuestionDTOS, HttpServletRequest request) {
        command.setMaxPageItems(10);
        RequestUtil.initSearchBean(request, command);
        command.setTotalItems(examinationQuestionDTOS.size());
        int formIndex = command.getFirstItem();
        if (formIndex > command.getTotalItems()) {
            formIndex = 0;
            command.setFirstItem(0);
        }
        int toIndex = command.getFirstItem() + command.getMaxPageItems();
        if (examinationQuestionDTOS.size() > 0) {
            if (toIndex > examinationQuestionDTOS.size()) {
                toIndex = examinationQuestionDTOS.size();
            }
        }
        return examinationQuestionDTOS.subList(formIndex, toIndex);
    }

    private Map<String, String> buildMapRedirectMessage(ResourceBundle bundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, "Add examination successfully");
        mapMessage.put(WebConstant.REDIRECT_UPDATE, "Update examination successfully");
        mapMessage.put(WebConstant.REDIRECT_DELETE, "Delete examination successfully");
        mapMessage.put(WebConstant.REDIRECT_ERROR, "Error System");
        return mapMessage;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UploadUtil uploadUtil = new UploadUtil();
        Set<String> value = new HashSet<String>();
        value.add("urlType");
        Object[] objects = uploadUtil.writeOrUpdateFile(request,value,"excel");
        try {
            ExaminationCommand command = FormUtil.populate(ExaminationCommand.class, request);
            ExaminationDTO pojo = command.getPojo();
            if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
                if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                    if (pojo != null && pojo.getExaminationId() != null) {
                        ExaminationDTO examinationDTO = SingletonServiceUtil.getExaminationServiceInstance().findByExaminationId("examinationId", pojo.getExaminationId());
                        pojo.setCreatedDate(examinationDTO.getCreatedDate());
                        ExaminationDTO dto = SingletonServiceUtil.getExaminationServiceInstance().updateExamination(pojo);
                        if (dto != null) {
                            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_UPDATE);
                        }
                    } else {
                        SingletonServiceUtil.getExaminationServiceInstance().saveExamination(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_INSERT);
                    }
                }
                request.getRequestDispatcher("/views/admin/examination/edit.jsp").forward(request, response);
            }
            if(objects != null) {
                String urlType = null;
                Map<String, String> mapValue = (Map<String, String>)objects[3];
                for(Map.Entry<String, String> item : mapValue.entrySet()) {
                    if (item.getKey().equals("urlType")) {
                        urlType = item.getValue();
                    }
                }
                if(urlType != null && urlType.equals(READ_EXCEL)) {
                    String fileLocation = objects[1].toString();
                    String fileName = objects[2].toString();
                    List<ExaminationQuestionDTO> excelValues = returnValueFromExcel(fileName, fileLocation);
                    validateData(excelValues);
                    SessionUtil.getInstance().putValue(request, LIST_EXAMINATION_IMPORT, excelValues);
                    response.sendRedirect("/admin-examination-import-validate.html?urlType=validate_import");
                    return;
                }
            }
            if (command.getUrlType() != null && command.getUrlType().equals(IMPORT_DATA));
            List<ExaminationQuestionDTO> examinationQuestionDTOS = (List<ExaminationQuestionDTO>)SessionUtil.getInstance().getValue(request, LIST_EXAMINATION_IMPORT);
            SingletonServiceUtil.getExaminationQuestionServiceInstance().saveExaminationQuestionImport(examinationQuestionDTOS);
            SessionUtil.getInstance().remove(request, LIST_EXAMINATION_IMPORT);
            response.sendRedirect("/admin-examination-list.html?urlType=url_list");
            return;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);
        }
    }

    private void validateData(List<ExaminationQuestionDTO> excelValues) {
        for(ExaminationQuestionDTO item : excelValues) {
            checkRequireField(item);
        }
        SingletonServiceUtil.getExaminationQuestionServiceInstance().validateImportExamination(excelValues);
    }

    private void checkRequireField(ExaminationQuestionDTO item) {
        String message = "";
        if(item.getExaminationId() == null) {
            message += "<br/>";
            message += bundle.getString("label.examination.notempty");
        }
        if(StringUtils.isNotBlank(message)) {
            item.setValid(false);
        }
        item.setError(message);
    }

    private List<ExaminationQuestionDTO> returnValueFromExcel(String fileName, String fileLocation) throws IOException {
        Workbook workbook = ExcelPoiUtil.getWorkBook(fileName, fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        List<ExaminationQuestionDTO> excelValues = new ArrayList<ExaminationQuestionDTO>();
        for (int i=1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            ExaminationQuestionDTO examinationQuestionDTO = readDataFromExcel(row);
            excelValues.add(examinationQuestionDTO);
        }
        return excelValues;
    }

    private ExaminationQuestionDTO readDataFromExcel(Row row) {
        ExaminationQuestionDTO questionDTO = new ExaminationQuestionDTO();
        questionDTO.setImage(ExcelPoiUtil.getCellValue(row.getCell(0)));
        questionDTO.setAudio(ExcelPoiUtil.getCellValue(row.getCell(1)));
        questionDTO.setParagraph(ExcelPoiUtil.getCellValue(row.getCell(2)));
        questionDTO.setQuestion(ExcelPoiUtil.getCellValue(row.getCell(3)));
        questionDTO.setOption1(ExcelPoiUtil.getCellValue(row.getCell(4)));
        questionDTO.setOption2(ExcelPoiUtil.getCellValue(row.getCell(5)));
        questionDTO.setOption3(ExcelPoiUtil.getCellValue(row.getCell(6)));
        questionDTO.setOption4(ExcelPoiUtil.getCellValue(row.getCell(7)));
        questionDTO.setCorrectAnswer(ExcelPoiUtil.getCellValue(row.getCell(8)));
        questionDTO.setExaminationId(Integer.parseInt(ExcelPoiUtil.getCellValue(row.getCell(9))));
        questionDTO.setType(ExcelPoiUtil.getCellValue(row.getCell(10)));
        return questionDTO;
    }
}