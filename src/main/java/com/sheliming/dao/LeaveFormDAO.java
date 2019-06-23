package com.sheliming.dao;

import com.sheliming.domain.LeaveFormDO;
import com.sheliming.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveFormDAO extends JpaRepository<LeaveFormDO, Integer>{
    public List<LeaveFormDO> findByUserId(Integer userId);
}