package com.sheliming.service.impl;

import com.sheliming.dao.LeaveFormDAO;
import com.sheliming.dao.UserDAO;
import com.sheliming.domain.LeaveFormDO;
import com.sheliming.domain.RoleDO;
import com.sheliming.service.LeaveFormService;
import com.sheliming.service.UserService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveFormServiceImpl implements LeaveFormService {
    private Logger logger = LoggerFactory.getLogger(LeaveFormServiceImpl.class);

    @Autowired
    private LeaveFormDAO leaveFormDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

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

        //流程实例启动后，流程会跳转到请假申请节点
        Task leaveApply = taskService.createTaskQuery().processInstanceId(leaveProcessInstance.getId()).singleResult();
        logger.info("leaveApply:" + leaveApply);
        //设置请假申请任务的执行人
        taskService.setAssignee(leaveApply.getId(), savedLeaveFormDO.getUserId().toString());

        //设置流程参数：请假天数和表单ID
        //流程引擎会根据请假天数days>3判断流程走向
        //formId是用来将流程数据和表单数据关联起来
        Map<String, Object> args = new HashMap<>();
        args.put("days", savedLeaveFormDO.getDays());
        args.put("formId", savedLeaveFormDO.getId());

        //完成请假申请任务
        taskService.complete(leaveApply.getId(), args);

        return savedLeaveFormDO;
    }

    @Override
    public List<LeaveFormDO> getUserLeaveForm(Integer userId,int pageNum,int pageSize) {
        RoleDO userRole = userService.getUserRole(userId);

        List<String> groupNames = new ArrayList<>();
        groupNames.add(userRole.getName());

        //查询用户组的待审批请假流程列表
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("leave")
                .taskCandidateGroupIn(groupNames)
                .listPage(pageNum - 1, pageSize);

        //根据流程实例ID查询请假申请表单数据
        List<String> processInstanceIds = tasks.stream()
                .map(task -> task.getProcessInstanceId())
                .collect(Collectors.toList());

        System.out.println(processInstanceIds);
//        List<VacationApplyBasicPO> vacationApplyList =
//                vacationRepository.getVacationApplyList(processInstanceIds);
        return null;
    }
}
