package com.sheliming.service;

import com.sheliming.dao.LeaveFormDAO;
import com.sheliming.domain.LeaveFormDO;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface LeaveFormService {
    List<LeaveFormDO> findByUserId(Integer userId);

    List<LeaveFormDO> findById(Integer id);

    LeaveFormDO save(LeaveFormDO leaveFormDO);

    LeaveFormDO writeForm(LeaveFormDO leaveFormDO);

    List<LeaveFormDO> getUserTodo(Integer userId, int pageNum, int pageSize);

    void approve(Integer formId, String comment, Integer isApprove, String userId);

    List<Comment> getCommentHistory(Integer formId);
}