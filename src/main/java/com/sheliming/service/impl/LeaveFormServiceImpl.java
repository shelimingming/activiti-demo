package com.sheliming.service.impl;

import com.sheliming.dao.LeaveFormDAO;
import com.sheliming.domain.LeaveFormDO;
import com.sheliming.service.LeaveFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveFormServiceImpl implements LeaveFormService {
    @Autowired
    private LeaveFormDAO leaveFormDAO;

    @Override
    public List<LeaveFormDO> findByUserId(Integer userId) {
        List<LeaveFormDO> LeaveFormDOList = leaveFormDAO.findByUserId(userId);
        return LeaveFormDOList;
    }
}
