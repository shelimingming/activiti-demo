package com.sheliming.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//用户信息表
@Entity
@Table(name = "user")
public class UserDO {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    //用户角色
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}