package vn.myclass.core.service;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dto.ListenGuidelineDTO;

import java.util.List;
import java.util.Map;

public interface ListenGuidelineService {
    Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit);
    ListenGuidelineDTO findByListenGuidelineId(String property, Integer listenGuidelineId);
    void saveListenGuideline(ListenGuidelineDTO dto) throws ConstraintViolationException;
    ListenGuidelineDTO updateListenGuideline(ListenGuidelineDTO dto);
    Integer delete(List<Integer> ids);
}
