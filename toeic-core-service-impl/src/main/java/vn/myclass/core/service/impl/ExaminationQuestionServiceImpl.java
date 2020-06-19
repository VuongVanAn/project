package vn.myclass.core.service.impl;

import org.apache.commons.lang.StringUtils;
import vn.myclass.core.dto.ExaminationQuestionDTO;
import vn.myclass.core.persistence.entity.ExaminationEntity;
import vn.myclass.core.persistence.entity.ExaminationQuestionEntity;
import vn.myclass.core.service.ExaminationQuestionService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ExaminationBeanUtil;
import vn.myclass.core.utils.ExaminationQuestionBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationQuestionServiceImpl implements ExaminationQuestionService {
    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, Integer examinationId) {
        List<ExaminationQuestionDTO> result = new ArrayList<ExaminationQuestionDTO>();
        Object[] objects = SingletonDaoUtil.getExaminationQuestionDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, examinationId);
        int count = 1;
        for (ExaminationQuestionEntity item: (List<ExaminationQuestionEntity>)objects[1]) {
            ExaminationQuestionDTO dto = ExaminationQuestionBeanUtil.toDto(item);
            if (StringUtils.isBlank(item.getParagraph())) {
                dto.setNumber(count);
                count++;
            }
            dto.setExamination(ExaminationBeanUtil.toDto(item.getExamination()));
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }

    public void saveExaminationQuestionImport(List<ExaminationQuestionDTO> examinationQuestionDTOS) {
        for (ExaminationQuestionDTO item : examinationQuestionDTOS) {
            if (item.isValid()) {
                ExaminationQuestionEntity entity = new ExaminationQuestionEntity();
                entity.setAudio(item.getAudio());
                entity.setType(item.getType());
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
                ExaminationEntity examEntity = SingletonDaoUtil.getExaminationDaoInstance().findEqualUnique("examinationId", item.getExaminationId());
                entity.setExamination(examEntity);
                SingletonDaoUtil.getExaminationQuestionDaoInstance().save(entity);
            }
        }
    }

    public void validateImportExamination(List<ExaminationQuestionDTO> examinationQuestionImportDTOS) {
    }
}
