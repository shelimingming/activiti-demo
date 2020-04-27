package com.sheliming.service.impl;

import com.sheliming.dao.LeaveFormDAO;
import com.sheliming.domain.LeaveFormDO;
import com.sheliming.domain.RoleDO;
import com.sheliming.service.LeaveFormService;
import com.sheliming.service.UserService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public List<LeaveFormDO> findById(Integer id) {
        List<LeaveFormDO> LeaveFormDOList = leaveFormDAO.findById(id);
        return LeaveFormDOList;
    }

    @Override
    public LeaveFormDO save(LeaveFormDO leaveFormDO) {
        LeaveFormDO savedLeaveFormDO = leaveFormDAO.save(leaveFormDO);
        return savedLeaveFormDO;
    }

    @Override
    public LeaveFormDO writeForm(LeaveFormDO leaveFormDO) {
        //启动流程实例，字符串"vacation"是BPMN模型文件里process元素的id
        ProcessInstance leaveProcessInstance = runtimeService.startProcessInstanceByKey("leave");
        logger.info("创建流程成功:" + leaveProcessInstance);
        //将流程实例id存入我们的表里
        leaveFormDO.setProcessInstanceId(leaveProcessInstance.getId());
        LeaveFormDO savedLeaveFormDO = leaveFormDAO.save(leaveFormDO);

        //流程实例启动后，流程会跳转到请假申请节点
        Task leaveTask = taskService.createTaskQuery().processInstanceId(leaveProcessInstance.getId()).singleResult();
        logger.info("leaveApply:" + leaveTask);
        //设置请假申请任务的执行人
        taskService.setAssignee(leaveTask.getId(), leaveFormDO.getUserId().toString());

        //设置流程参数：请假天数和表单ID
        //流程引擎会根据请假天数days>3判断流程走向
        //formId是用来将流程数据和表单数据关联起来
        Map<String, Object> args = new HashMap<>();
        args.put("days", savedLeaveFormDO.getDays());
        args.put("formId", savedLeaveFormDO.getId());

        //完成请假申请任务
        taskService.complete(leaveTask.getId(), args);

        return savedLeaveFormDO;
    }

    @Override
    public List<LeaveFormDO> getUserTodo(Integer userId, int pageNum, int pageSize) {
        List<LeaveFormDO> leaveFormDOList = new ArrayList<LeaveFormDO>();

        RoleDO userRole = userService.getUserRole(userId);

        List<String> groupNames = new ArrayList<>();
        groupNames.add(userRole.getName());

        //查询用户组的待审批请假流程列表
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("leave")
                .taskCandidateGroupIn(groupNames)
                .listPage(pageNum - 1, pageSize);

        List<Task> tasks2 = taskService.createTaskQuery()
                .processDefinitionKey("leave").taskCandidateOrAssigned(userId.toString()).list();

        tasks.addAll(tasks2);

        //根据流程实例ID查询请假申请表单数据
        List<String> processInstanceIds = tasks.stream()
                .map(task -> task.getProcessInstanceId())
                .collect(Collectors.toList());

        System.out.println(processInstanceIds);

        if (!CollectionUtils.isEmpty(processInstanceIds)) {
            for (String processInstanceId : processInstanceIds) {
                List<LeaveFormDO> leaveFormDOList1 = leaveFormDAO.findByProcessInstanceId(processInstanceId);
                leaveFormDOList.add(leaveFormDOList1.get(0));
            }

        }

        return leaveFormDOList;
    }

    @Override
    public void approve(Integer formId, String comment, Integer isApprove, String userId) {
        Map<String, Object> args = new HashMap<>();
        args.put("approve", isApprove);

        LeaveFormDO leaveFormDO = leaveFormDAO.findById(formId).get(0);


        Task task = taskService.createTaskQuery().processInstanceId(leaveFormDO.getProcessInstanceId()).singleResult();
        String taskId = task.getId();
        Authentication.setAuthenticatedUserId(userId);

        taskService.addComment(taskId, task.getProcessInstanceId(), comment);
        taskService.complete(taskId, args);
    }

    public List<Comment> getCommentHistory(Integer formId){
        LeaveFormDO leaveFormDO = leaveFormDAO.findById(formId).get(0);
        List<Comment> commentList = taskService.getProcessInstanceComments(leaveFormDO.getProcessInstanceId());
        return commentList;
    }
}
