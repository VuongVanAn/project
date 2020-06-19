package vn.myclass.core.service;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dto.ExaminationDTO;

import java.util.List;
import java.util.Map;

public interface ExaminationService {
    Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit);
    ExaminationDTO findByExaminationId(String property, Integer examinationId);
    ExaminationDTO findById(Integer examinationId);
    void saveExamination(ExaminationDTO dto) throws ConstraintViolationException;
    ExaminationDTO updateExamination(ExaminationDTO dto);
    Integer delete(List<Integer> ids);
}
