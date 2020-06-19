package vn.myclass.core.service.impl;

import vn.myclass.core.dto.ExerciseQuestionDTO;
import vn.myclass.core.persistence.entity.ExerciseEntity;
import vn.myclass.core.persistence.entity.ExerciseQuestionEntity;
import vn.myclass.core.service.ExerciseQuestionService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ExerciseBeanUtil;
import vn.myclass.core.utils.ExerciseQuestionBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseQuestionServiceImpl implements ExerciseQuestionService {
    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, Integer exerciseId) {
        List<ExerciseQuestionDTO> result = new ArrayList<ExerciseQuestionDTO>();
        String whereClause = null;
        if (exerciseId != null) {
            whereClause = " AND exerciseEntity.exerciseId = " + exerciseId + "";
        }
        Object[] objects = SingletonDaoUtil.getExerciseQuestionDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, whereClause);
        for (ExerciseQuestionEntity item : (List<ExerciseQuestionEntity>) objects[1]) {
            ExerciseQuestionDTO dto = ExerciseQuestionBeanUtil.toDto(item);
            dto.setExercise(ExerciseBeanUtil.toDto(item.getExerciseEntity()));
            result.add(dto);
        }
        objects[1] = result;
        return objects;
        //return new Object[]{};
    }

    public void saveExerciseQuestionImport(List<ExerciseQuestionDTO> exerciseQuestionImportDTOS) {
        for (ExerciseQuestionDTO item : exerciseQuestionImportDTOS) {
            if (item.isValid()) {
                ExerciseQuestionEntity entity = new ExerciseQuestionEntity();
                entity.setAudio(item.getAudio());
                entity.setImage(item.getImage());
                entity.setCorrectAnswer(item.getCorrectAnswer());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                entity.setCreatedDate(timestamp);
                entity.setQuestion(item.getQuestion());
                entity.setParagraph(item.getParagraph());
                entity.setOption1(item.getOption1());
                entity.setOption2(item.getOption2());
                entity.setOption3(item.getOption3());
                entity.setOption4(item.getOption4());
                ExerciseEntity exerciseEntity = SingletonDaoUtil.getExerciseDaoInstance().findEqualUnique("exerciseId", item.getExerciseId());
                entity.setExerciseEntity(exerciseEntity);
                SingletonDaoUtil.getExerciseQuestionDaoInstance().save(entity);
            }
        }
    }

}
