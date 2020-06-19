package vn.myclass.core.service.impl;

import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.persistence.entity.RoleEntity;
import vn.myclass.core.service.RoleService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.RoleBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    public List<RoleDTO> findAll() {
        List<RoleDTO> dtos = new ArrayList<RoleDTO>();
        List<RoleEntity> entities = SingletonDaoUtil.getRoleDaoInstance().findAll();
        for(RoleEntity item : entities) {
            RoleDTO dto = RoleBeanUtil.toDto(item);
            dtos.add(dto);
        }
        return dtos;
    }
}
