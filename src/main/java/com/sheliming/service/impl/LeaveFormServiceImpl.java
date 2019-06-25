package com.sheliming.service.impl;

import com.sheliming.dao.LeaveFormDAO;
import com.sheliming.domain.LeaveFormDO;
import com.sheliming.service.LeaveFormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveFormServiceImpl implements LeaveFormService {
    private Logger logger = LoggerFactory.getLogger(LeaveFormServiceImpl.class);

    @Autowired
    private LeaveFormDAO leaveFormDAO;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public List<LeaveFormDO> findByUserId(Integer userId) {
        List<LeaveFormDO> LeaveFormDOList = leaveFormDAO.findByUserId(userId);
        return LeaveFormDOList;
    }

    @Override
    public LeaveFormDO save(LeaveFormDO leaveFormDO) {
        LeaveFormDO savedLeaveFormDO = leaveFormDAO.save(leaveFormDO);
        return savedLeaveFormDO;
    }

    @Override
    public LeaveFormDO writeForm(LeaveFormDO leaveFormDO) {
        LeaveFormDO savedLeaveFormDO = leaveFormDAO.save(leaveFormDO);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", savedLeaveFormDO.getUserId());
        //开始请假流程，使用formId作为流程的businessKey
        ProcessInstance leaveProcessInstance = runtimeService.startProcessInstanceByKey("leave", savedLeaveFormDO.getId().toString(), variables);
        logger.info("创建流程:" + leaveProcessInstance);


        return savedLeaveFormDO;
    }
}
