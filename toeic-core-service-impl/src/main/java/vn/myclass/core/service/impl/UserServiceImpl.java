package vn.myclass.core.service.impl;

import org.apache.commons.lang.StringUtils;
import vn.myclass.core.dto.CheckLogin;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.dto.UserImportDTO;
import vn.myclass.core.persistence.entity.ResultEntity;
import vn.myclass.core.persistence.entity.RoleEntity;
import vn.myclass.core.persistence.entity.UserEntity;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.UserBeanUtil;

import java.sql.Timestamp;
import java.util.*;

public class UserServiceImpl implements UserService {

    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        Object[] objects = SingletonDaoUtil.getUserDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, null);
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        for(UserEntity item : (List<UserEntity>) objects[1]) {
            UserDTO userDTO = UserBeanUtil.toDto(item);
            userDTOList.add(userDTO);
        }
        objects[1] = userDTOList;
        return objects;
    }

    public UserDTO findById(Integer userId) {
        UserEntity entity = SingletonDaoUtil.getUserDaoInstance().findById(userId);
        UserDTO dto = UserBeanUtil.toDto(entity);
        return dto;
    }

    public UserDTO findByUserId(String property, Integer userId) {
        UserEntity userEntity = SingletonDaoUtil.getUserDaoInstance().findEqualUnique(property, userId);
        UserDTO dto = UserBeanUtil.toDto(userEntity);
        return dto;
    }

    public void saveUser(UserDTO userDTO) {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        userDTO.setCreatedDate(createdDate);
        UserEntity entity = UserBeanUtil.toEntity(userDTO);
        SingletonDaoUtil.getUserDaoInstance().save(entity);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        userDTO.setModifiedDate(timestamp);
        UserEntity entity = UserBeanUtil.toEntity(userDTO);
        entity = SingletonDaoUtil.getUserDaoInstance().update(entity);
        userDTO = UserBeanUtil.toDto(entity);
        return userDTO;
    }

    public CheckLogin checkLogin(String name, String password) {
        CheckLogin checkLogin = new CheckLogin();
        if (name != null && password != null) {
            Object[] objects = SingletonDaoUtil.getUserDaoInstance().checkLogin(name,password);
            checkLogin.setUserExist((Boolean) objects[0]);
            if (checkLogin.isUserExist()) {
                checkLogin.setRoleName(objects[1].toString());
            }
        }
        return checkLogin;
    }

    public void validateImportUser(List<UserImportDTO> userImportDTOS) {
        List<String> names = new ArrayList<String>();
        List<String> roles = new ArrayList<String>();
        for (UserImportDTO item : userImportDTOS) {
            if (item.isValid()) {
                names.add(item.getUserName());
                if (!roles.contains(item.getRoleName())) {
                    roles.add(item.getRoleName());
                }
            }
        }
        Map<String, UserEntity> userEntityMap = new HashMap<String, UserEntity>();
        Map<String, RoleEntity> roleEntityMap = new HashMap<String, RoleEntity>();
        if (names.size() > 0) {
            List<UserEntity> userEntities = SingletonDaoUtil.getUserDaoInstance().findByUsers(names);
            for (UserEntity item : userEntities) {
                userEntityMap.put(item.getName().toUpperCase(), item);
            }
        }
        if (roles.size() > 0) {
            List<RoleEntity> roleEntities = SingletonDaoUtil.getRoleDaoInstance().findByRoles(roles);
            for (RoleEntity item : roleEntities) {
                roleEntityMap.put(item.getName().toUpperCase(), item);
            }
        }
        for (UserImportDTO item : userImportDTOS) {
            String message = item.getError();
            if (item.isValid()) {
                UserEntity userEntity = userEntityMap.get(item.getUserName().toUpperCase());
                if (userEntity != null) {
                    message += "<br/>";
                    message += "Tên đăng nhập đã tồn tại";
                }
                RoleEntity roleEntity = roleEntityMap.get(item.getRoleName().toUpperCase());
                if (roleEntity == null) {
                    message += "<br/>";
                    message += "Vai trò không tồn tại";
                }
                if (StringUtils.isNotBlank(message)) {
                    item.setValid(false);
                    item.setError(message.substring(5));
                }
            }
        }
    }

    public void saveUserImport(List<UserImportDTO> userImportDTOS) {
        for (UserImportDTO item : userImportDTOS) {
            if (item.isValid()) {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(item.getUserName());
                userEntity.setFullName(item.getFullName());
                userEntity.setPassword(item.getPassword());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                userEntity.setCreatedDate(timestamp);
                RoleEntity roleEntity = SingletonDaoUtil.getRoleDaoInstance().findEqualUnique("name", item.getRoleName().toUpperCase());
                userEntity.setRoleEntity(roleEntity);
                SingletonDaoUtil.getUserDaoInstance().save(userEntity);
            }
        }
    }

    public Integer delete(List<Integer> ids) {
        for (Integer id : ids) {
            List<ResultEntity> resultEntity = SingletonDaoUtil.getResultDaoInstance().findAll("user.userId", id);
            for (ResultEntity entity : resultEntity) {
                SingletonDaoUtil.getResultDaoInstance().deleteList(entity);
            }
        }
        Integer result = SingletonDaoUtil.getUserDaoInstance().delete(ids);
        return result;
    }
}
