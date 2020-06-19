package vn.myclass.core.service.impl;

import vn.myclass.core.dto.ExaminationQuestionDTO;
import vn.myclass.core.dto.ResultDTO;
import vn.myclass.core.persistence.entity.ExaminationEntity;
import vn.myclass.core.persistence.entity.ResultEntity;
import vn.myclass.core.persistence.entity.UserEntity;
import vn.myclass.core.service.ResultService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ResultBeanUtil;

import java.sql.Timestamp;
import java.util.List;

public class ResultServiceImpl implements ResultService {
    public ResultDTO saveResult(String userName, Integer examinationId, List<ExaminationQuestionDTO> examinationQuestions) {
        ResultDTO resultDTO = new ResultDTO();
        if (userName != null && examinationId != null && examinationQuestions != null) {
            UserEntity userEntity = SingletonDaoUtil.getUserDaoInstance().findEqualUnique("name", userName);
            ExaminationEntity examinationEntity = SingletonDaoUtil.getExaminationDaoInstance().findById(examinationId);
            ResultEntity resultEntity = new ResultEntity();
            calculateScoreExamination(resultEntity, examinationQuestions);
            initDataToResultEntity(resultEntity, userEntity, examinationEntity);
            resultEntity = SingletonDaoUtil.getResultDaoInstance().save(resultEntity);
            resultDTO = ResultBeanUtil.toDto(resultEntity);
        }
        return resultDTO;
    }

    private void initDataToResultEntity(ResultEntity resultEntity, UserEntity userEntity, ExaminationEntity examinationEntity) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resultEntity.setUser(userEntity);
        resultEntity.setExamination(examinationEntity);
        resultEntity.setCreatedDate(timestamp);
    }

    private void calculateScoreExamination(ResultEntity resultEntity, List<ExaminationQuestionDTO> examinationQuestions) {
        int listenScore = 0;
        int readingScore = 0;
        for (ExaminationQuestionDTO item : examinationQuestions) {
            if (item.getAnswerUser() != null) {
                if (item.getAnswerUser().equals(item.getCorrectAnswer())) {
                    if (item.getNumber() <= 10) {
                        listenScore++;
                    } else {
                        readingScore++;
                    }
                }
            }
        }
        resultEntity.setListenScore(listenScore);
        resultEntity.setReadingScore(readingScore);
    }
}
