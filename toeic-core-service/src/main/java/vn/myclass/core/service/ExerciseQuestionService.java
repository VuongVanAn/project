package vn.myclass.core.service;

import vn.myclass.core.dto.ExerciseQuestionDTO;

import java.util.List;
import java.util.Map;

public interface ExerciseQuestionService {
    Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, Integer exerciseId);
    void saveExerciseQuestionImport(List<ExerciseQuestionDTO> exerciseQuestionImportDTOS);
}
