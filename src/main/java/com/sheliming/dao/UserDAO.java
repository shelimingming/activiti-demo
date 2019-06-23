package com.sheliming.dao;

import com.sheliming.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<UserDO, Integer>{
	public List<UserDO> findByName(String name);
}