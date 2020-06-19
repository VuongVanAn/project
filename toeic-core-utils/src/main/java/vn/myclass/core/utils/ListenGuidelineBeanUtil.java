package vn.myclass.core.utils;

import vn.myclass.core.dto.ListenGuidelineDTO;
import vn.myclass.core.persistence.entity.ListenGuidelineEntity;

public class ListenGuidelineBeanUtil {
    public static ListenGuidelineDTO toDto(ListenGuidelineEntity entity) {
        ListenGuidelineDTO dto = new ListenGuidelineDTO();
        dto.setListenGuidelineId(entity.getListenGuidelineId());
        dto.setContent(entity.getContent());
        dto.setImage(entity.getImage());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

    public static ListenGuidelineEntity toEntity(ListenGuidelineDTO dto) {
        ListenGuidelineEntity entity = new ListenGuidelineEntity();
        entity.setListenGuidelineId(dto.getListenGuidelineId());
        entity.setContent(dto.getContent());
        entity.setImage(dto.getImage());
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
