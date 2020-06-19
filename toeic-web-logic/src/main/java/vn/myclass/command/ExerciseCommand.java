package vn.myclass.command;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.ExerciseQuestionDTO;
import vn.myclass.core.web.command.AbstractCommand;

import java.util.List;

public class ExerciseCommand extends AbstractCommand<ExerciseDTO> {
    public ExerciseCommand() {
        this.pojo = new ExerciseDTO();
    }

    private List<ExerciseQuestionDTO> exerciseQuestionDTOS;

    public List<ExerciseQuestionDTO> getExerciseQuestionDTOS() {
        return exerciseQuestionDTOS;
    }

    public void setExerciseQuestionDTOS(List<ExerciseQuestionDTO> exerciseQuestionDTOS) {
        this.exerciseQuestionDTOS = exerciseQuestionDTOS;
    }
}
