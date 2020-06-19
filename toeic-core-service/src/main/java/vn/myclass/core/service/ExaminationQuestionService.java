package vn.myclass.core.service;

import vn.myclass.core.dto.ExaminationQuestionDTO;

import java.util.List;
import java.util.Map;

public interface ExaminationQuestionService {
    Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, Integer examinationId);
    void saveExaminationQuestionImport(List<ExaminationQuestionDTO> examinationQuestionDTOS);
    void validateImportExamination(List<ExaminationQuestionDTO> examinationQuestionImportDTOS);
}
