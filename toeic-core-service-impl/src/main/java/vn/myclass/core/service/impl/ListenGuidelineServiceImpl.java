package vn.myclass.core.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dto.ListenGuidelineDTO;
import vn.myclass.core.persistence.entity.ListenGuidelineEntity;
import vn.myclass.core.service.ListenGuidelineService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ListenGuidelineBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenGuidelineServiceImpl implements ListenGuidelineService {
    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ListenGuidelineDTO> result = new ArrayList<ListenGuidelineDTO>();
        Object[] objects = SingletonDaoUtil.getListenGuidelineDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, null);
        for (ListenGuidelineEntity item : (List<ListenGuidelineEntity>)objects[1]) {
            ListenGuidelineDTO dto = ListenGuidelineBeanUtil.toDto(item);
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }

    public ListenGuidelineDTO findByListenGuidelineId(String property, Integer listenGuidelineId) {
        ListenGuidelineEntity guidelineEntity = SingletonDaoUtil.getListenGuidelineDaoInstance().findEqualUnique(property, listenGuidelineId);
        ListenGuidelineDTO dto = ListenGuidelineBeanUtil.toDto(guidelineEntity);
        return dto;
    }

    public void saveListenGuideline(ListenGuidelineDTO dto) throws ConstraintViolationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(timestamp);
        ListenGuidelineEntity entity = ListenGuidelineBeanUtil.toEntity(dto);
        SingletonDaoUtil.getListenGuidelineDaoInstance().save(entity);
    }

    public ListenGuidelineDTO updateListenGuideline(ListenGuidelineDTO dto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(timestamp);
        ListenGuidelineEntity entity = ListenGuidelineBeanUtil.toEntity(dto);
        entity = SingletonDaoUtil.getListenGuidelineDaoInstance().update(entity);
        dto = ListenGuidelineBeanUtil.toDto(entity);
        return dto;
    }

    public Integer delete(List<Integer> ids) {
        Integer result = SingletonDaoUtil.getListenGuidelineDaoInstance().delete(ids);
        return result;
    }
}
