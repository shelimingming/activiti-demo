package com.sheliming.service;

import com.sheliming.dao.LeaveFormDAO;
import com.sheliming.domain.LeaveFormDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LeaveFormService {
    public List<LeaveFormDO> findByUserId(Integer userId);

}