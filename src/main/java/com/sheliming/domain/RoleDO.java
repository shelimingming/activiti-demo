package com.sheliming.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//角色信息表
@Entity
@Table(name = "role")
public class RoleDO {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

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

    @Override
    public String toString() {
        return "RoleDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
