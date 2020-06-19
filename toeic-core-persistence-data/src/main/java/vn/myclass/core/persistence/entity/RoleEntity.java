package vn.myclass.core.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity implements Serializable {
    @Id
    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "roleEntity", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.REMOVE})
    private List<UserEntity> userEntityList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }
}
