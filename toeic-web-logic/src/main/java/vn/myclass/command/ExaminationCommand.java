package vn.myclass.command;

import vn.myclass.core.dto.ExaminationDTO;
import vn.myclass.core.dto.ExaminationQuestionDTO;
import vn.myclass.core.web.command.AbstractCommand;

import java.util.List;

public class ExaminationCommand extends AbstractCommand<ExaminationDTO> {
    public ExaminationCommand() {
        this.pojo = new ExaminationDTO();
    }

    private List<ExaminationQuestionDTO> examinationQuestionDTOS;

    public List<ExaminationQuestionDTO> getExaminationQuestionDTOS() {
        return examinationQuestionDTOS;
    }

    public void setExaminationQuestionDTOS(List<ExaminationQuestionDTO> examinationQuestionDTOS) {
        this.examinationQuestionDTOS = examinationQuestionDTOS;
    }
}
