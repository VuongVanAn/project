package vn.myclass.core.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dto.ExaminationDTO;
import vn.myclass.core.persistence.entity.ExaminationEntity;
import vn.myclass.core.persistence.entity.ExaminationQuestionEntity;
import vn.myclass.core.persistence.entity.ResultEntity;
import vn.myclass.core.service.ExaminationService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ExaminationBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationServiceImpl implements ExaminationService {
    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExaminationDTO> result = new ArrayList<ExaminationDTO>();
        Object[] objects = SingletonDaoUtil.getExaminationDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, null);
        for (ExaminationEntity item : (List<ExaminationEntity>)objects[1]) {
            ExaminationDTO dto = ExaminationBeanUtil.toDto(item);
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }

    public ExaminationDTO findByExaminationId(String property, Integer examinationId) {
        ExaminationEntity examEntity = SingletonDaoUtil.getExaminationDaoInstance().findEqualUnique(property, examinationId);
        ExaminationDTO dto = ExaminationBeanUtil.toDto(examEntity);
        return dto;
    }

    public ExaminationDTO findById(Integer examinationId) {
        ExaminationEntity entity = SingletonDaoUtil.getExaminationDaoInstance().findById(examinationId);
        ExaminationDTO dto = ExaminationBeanUtil.toDto(entity);
        return dto;
    }

    public void saveExamination(ExaminationDTO dto) throws ConstraintViolationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(timestamp);
        ExaminationEntity entity = ExaminationBeanUtil.toEntity(dto);
        SingletonDaoUtil.getExaminationDaoInstance().save(entity);
    }

    public ExaminationDTO updateExamination(ExaminationDTO dto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(timestamp);
        ExaminationEntity entity = ExaminationBeanUtil.toEntity(dto);
        entity = SingletonDaoUtil.getExaminationDaoInstance().update(entity);
        dto = ExaminationBeanUtil.toDto(entity);
        return dto;
    }

    public Integer delete(List<Integer> ids) {
        for (Integer id : ids) {
            List<ResultEntity> resultEntities = SingletonDaoUtil.getResultDaoInstance().findAll("examination.examinationId", id);
            for (ResultEntity item : resultEntities) {
                SingletonDaoUtil.getResultDaoInstance().deleteList(item);
            }
            List<ExaminationQuestionEntity> questionEntities = SingletonDaoUtil.getExaminationQuestionDaoInstance().findAll("examination.examinationId", id);
            for (ExaminationQuestionEntity item : questionEntities) {
                SingletonDaoUtil.getExaminationQuestionDaoInstance().deleteList(item);
            }
        }
        Integer result = SingletonDaoUtil.getExaminationDaoInstance().delete(ids);
        return result;
    }
}