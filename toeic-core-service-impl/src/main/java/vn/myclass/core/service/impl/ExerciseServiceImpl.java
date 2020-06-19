package vn.myclass.core.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.persistence.entity.ExerciseEntity;
import vn.myclass.core.persistence.entity.ExerciseQuestionEntity;
import vn.myclass.core.service.ExerciseService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ExerciseBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseServiceImpl implements ExerciseService {
    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExerciseDTO> result = new ArrayList<ExerciseDTO>();
        Object[] objects = SingletonDaoUtil.getExerciseDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, null);
        for (ExerciseEntity item : (List<ExerciseEntity>)objects[1]) {
            ExerciseDTO dto = ExerciseBeanUtil.toDto(item);
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }

    public ExerciseDTO findByExerciseId(String property, Integer exerciseId) {
        ExerciseEntity exerciseEntity = SingletonDaoUtil.getExerciseDaoInstance().findEqualUnique(property, exerciseId);
        ExerciseDTO dto = ExerciseBeanUtil.toDto(exerciseEntity);
        return dto;
    }

    public ExerciseDTO findById(Integer exerciseId) {
        ExerciseEntity entity = SingletonDaoUtil.getExerciseDaoInstance().findById(exerciseId);
        ExerciseDTO dto = ExerciseBeanUtil.toDto(entity);
        return dto;
    }

    public void saveExercise(ExerciseDTO dto) throws ConstraintViolationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(timestamp);
        ExerciseEntity entity = ExerciseBeanUtil.toEntity(dto);
        SingletonDaoUtil.getExerciseDaoInstance().save(entity);
    }

    public ExerciseDTO updateExercise(ExerciseDTO dto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(timestamp);
        ExerciseEntity entity = ExerciseBeanUtil.toEntity(dto);
        entity = SingletonDaoUtil.getExerciseDaoInstance().update(entity);
        dto = ExerciseBeanUtil.toDto(entity);
        return dto;
    }

    public Integer delete(List<Integer> ids) {
        for (Integer id : ids) {
            List<ExerciseQuestionEntity> questionEntities = SingletonDaoUtil.getExerciseQuestionDaoInstance().findAll("exerciseEntity.exerciseId", id);
            for (ExerciseQuestionEntity item : questionEntities) {
                SingletonDaoUtil.getExerciseQuestionDaoInstance().deleteList(item);
            }
        }
        Integer result = SingletonDaoUtil.getExerciseDaoInstance().delete(ids);
        return result;
    }
}
