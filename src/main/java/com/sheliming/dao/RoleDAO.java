package com.sheliming.dao;

import com.sheliming.domain.RoleDO;
import com.sheliming.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleDAO extends JpaRepository<RoleDO, Integer>{
	public List<RoleDO> findByName(String name);

	List<RoleDO> findById(Integer id);
}