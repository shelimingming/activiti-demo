package com.sheliming.dao;

import com.sheliming.domain.LeaveFormDO;
import com.sheliming.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveFormDAO extends JpaRepository<LeaveFormDO, Integer>{
    List<LeaveFormDO> findById(Integer id);

    List<LeaveFormDO> findByUserId(Integer userId);

    List<LeaveFormDO> findByProcessInstanceId(String processInstanceId);
}