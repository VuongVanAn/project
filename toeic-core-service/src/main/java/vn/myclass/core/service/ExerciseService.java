package vn.myclass.core.service;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dto.ExerciseDTO;

import java.util.List;
import java.util.Map;

public interface ExerciseService {
    Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit);
    ExerciseDTO findByExerciseId(String property, Integer exerciseId);
    ExerciseDTO findById(Integer exerciseId);
    void saveExercise(ExerciseDTO dto) throws ConstraintViolationException;
    ExerciseDTO updateExercise(ExerciseDTO dto);
    Integer delete(List<Integer> ids);
}
