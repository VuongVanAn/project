package vn.myclass.core.utils;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.persistence.entity.ExerciseEntity;

public class ExerciseBeanUtil {
    public static ExerciseDTO toDto(ExerciseEntity entity) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setExerciseId(entity.getExerciseId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ExerciseEntity toEntity(ExerciseDTO dto) {
        ExerciseEntity entity = new ExerciseEntity();
        entity.setExerciseId(dto.getExerciseId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
