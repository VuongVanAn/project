package vn.myclass.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import vn.myclass.command.ExerciseCommand;
import vn.myclass.core.common.utils.ExcelPoiUtil;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.common.utils.UploadUtil;
import vn.myclass.core.dto.*;
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

@WebServlet(urlPatterns = {"/admin-exercise-list.html", "/ajax-admin-exercise-edit.html",
        "/admin-exercise-import-validate.html", "/admin-exercise-import.html"})
public class ExerciseController extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());
    private final String SHOW_IMPORT_EXERCISE = "show_import_exercise";
    private final String READ_EXCEL = "read_excel";
    private final String VALIDATE_IMPORT = "validate_import";
    private final String LIST_EXERCISE_IMPORT = "list_exercise_import";
    private final String IMPORT_DATA = "import_data";
    ResourceBundle bundle = ResourceBundle.getBundle("ResourcesBundle");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExerciseCommand command = FormUtil.populate(ExerciseCommand.class, request);
        ExerciseDTO pojo = command.getPojo();
        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.REDIRECT_DELETE)) {
                List<Integer> ids = new ArrayList<Integer>();
                for (String item : command.getCheckList()) {
                    ids.add(Integer.parseInt(item));
                }
                Integer result = SingletonServiceUtil.getExerciseServiceInstance().delete(ids);
                if (result != ids.size()) {
                    command.setCrudaction(WebConstant.REDIRECT_ERROR);
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            RequestUtil.initSearchBean(request, command);
            Object[] objects = SingletonServiceUtil.getExerciseServiceInstance().findByProperty(map, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
            command.setListResult((List<ExerciseDTO>) objects[1]);
            command.setTotalItems(Integer.parseInt(objects[0].toString()));
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buildMapRedirectMessage(bundle);
                WebCommonUtil.addRedirectMessage(request, command.getCrudaction(), mapMessage);
            }
            request.getRequestDispatcher("/views/admin/exercise/list.jsp").forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if (pojo != null && pojo.getExerciseId() != null) {
                command.setPojo(SingletonServiceUtil.getExerciseServiceInstance().findById(pojo.getExerciseId()));
            }
            request.setAttribute(WebConstant.FORM_ITEM, command);
            request.getRequestDispatcher("/views/admin/exercise/edit.jsp").forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(SHOW_IMPORT_EXERCISE)) {
            request.getRequestDispatcher("/views/admin/exercise/importexercise.jsp").forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(VALIDATE_IMPORT)) {
            List<ExerciseQuestionDTO> exerciseQuestionDTOS = (List<ExerciseQuestionDTO>)SessionUtil.getInstance().getValue(request, LIST_EXERCISE_IMPORT);
            command.setExerciseQuestionDTOS(returnListExerciseImport(command, exerciseQuestionDTOS, request));
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            request.getRequestDispatcher("/views/admin/exercise/importexercise.jsp").forward(request, response);
        }
    }

    private List<ExerciseQuestionDTO> returnListExerciseImport(ExerciseCommand command, List<ExerciseQuestionDTO> exerciseQuestionDTOS, HttpServletRequest request) {
        command.setMaxPageItems(4);
        RequestUtil.initSearchBean(request, command);
        command.setTotalItems(exerciseQuestionDTOS.size());
        int formIndex = command.getFirstItem();
        if (formIndex > command.getTotalItems()) {
            formIndex = 0;
            command.setFirstItem(0);
        }
        int toIndex = command.getFirstItem() + command.getMaxPageItems();
        if (exerciseQuestionDTOS.size() > 0) {
            if (toIndex > exerciseQuestionDTOS.size()) {
                toIndex = exerciseQuestionDTOS.size();
            }
        }
        return exerciseQuestionDTOS.subList(formIndex, toIndex);
    }

    private Map<String, String> buildMapRedirectMessage(ResourceBundle bundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, "Add exercise successfully");
        mapMessage.put(WebConstant.REDIRECT_UPDATE, "Update exercise successfully");
        mapMessage.put(WebConstant.REDIRECT_DELETE, "Delete exercise successfully");
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
            ExerciseCommand command = FormUtil.populate(ExerciseCommand.class, request);
            ExerciseDTO pojo = command.getPojo();
            if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
                if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                    if (pojo != null && pojo.getExerciseId() != null) {
                        ExerciseDTO exerciseDTO = SingletonServiceUtil.getExerciseServiceInstance().findByExerciseId("exerciseId", pojo.getExerciseId());
                        pojo.setCreatedDate(exerciseDTO.getCreatedDate());
                        ExerciseDTO dto = SingletonServiceUtil.getExerciseServiceInstance().updateExercise(pojo);
                        if (dto != null) {
                            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_UPDATE);
                        }
                    } else {
                        SingletonServiceUtil.getExerciseServiceInstance().saveExercise(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_INSERT);
                    }
                }
                request.getRequestDispatcher("/views/admin/exercise/edit.jsp").forward(request, response);
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
                    List<ExerciseQuestionDTO> excelValues = returnValueFromExcel(fileName, fileLocation);
                    validateData(excelValues);
                    SessionUtil.getInstance().putValue(request, LIST_EXERCISE_IMPORT, excelValues);
                    response.sendRedirect("/admin-exercise-import-validate.html?urlType=validate_import");
                    return;
                }
            }
            if (command.getUrlType() != null && command.getUrlType().equals(IMPORT_DATA));
            List<ExerciseQuestionDTO> exerciseQuestionImportDTOS = (List<ExerciseQuestionDTO>)SessionUtil.getInstance().getValue(request, LIST_EXERCISE_IMPORT);
            SingletonServiceUtil.getExerciseQuestionServiceInstance().saveExerciseQuestionImport(exerciseQuestionImportDTOS);
            SessionUtil.getInstance().remove(request, LIST_EXERCISE_IMPORT);
            response.sendRedirect("/admin-exercise-list.html?urlType=url_list");
            return;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);
        }
    }

    private void validateData(List<ExerciseQuestionDTO> excelValues) {
        for(ExerciseQuestionDTO item : excelValues) {
            checkRequireField(item);
        }
    }

    private void checkRequireField(ExerciseQuestionDTO item) {
        String message = "";
        if(item.getExerciseId() == null) {
            message += "<br/>";
            message += bundle.getString("label.exercise.notempty");
        }
        if(StringUtils.isNotBlank(message)) {
            item.setValid(false);
        }
        item.setError(message);
    }

    private List<ExerciseQuestionDTO> returnValueFromExcel(String fileName, String fileLocation) throws IOException {
        Workbook workbook = ExcelPoiUtil.getWorkBook(fileName, fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        List<ExerciseQuestionDTO> excelValues = new ArrayList<ExerciseQuestionDTO>();
        for (int i=1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            ExerciseQuestionDTO exerciseQuestionDTO = readDataFromExcel(row);
            excelValues.add(exerciseQuestionDTO);
        }
        return excelValues;
    }

    private ExerciseQuestionDTO readDataFromExcel(Row row) {
        ExerciseQuestionDTO questionDTO = new ExerciseQuestionDTO();
        questionDTO.setImage(ExcelPoiUtil.getCellValue(row.getCell(0)));
        questionDTO.setAudio(ExcelPoiUtil.getCellValue(row.getCell(1)));
        questionDTO.setParagraph(ExcelPoiUtil.getCellValue(row.getCell(2)));
        questionDTO.setQuestion(ExcelPoiUtil.getCellValue(row.getCell(3)));
        questionDTO.setOption1(ExcelPoiUtil.getCellValue(row.getCell(4)));
        questionDTO.setOption2(ExcelPoiUtil.getCellValue(row.getCell(5)));
        questionDTO.setOption3(ExcelPoiUtil.getCellValue(row.getCell(6)));
        questionDTO.setOption4(ExcelPoiUtil.getCellValue(row.getCell(7)));
        questionDTO.setCorrectAnswer(ExcelPoiUtil.getCellValue(row.getCell(8)));
        questionDTO.setExerciseId(Integer.parseInt(ExcelPoiUtil.getCellValue(row.getCell(9))));
        return questionDTO;
    }
}