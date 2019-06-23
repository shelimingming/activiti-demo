package com.sheliming.controller;

import com.sheliming.domain.LeaveFormDO;
import com.sheliming.service.LeaveFormService;
import com.sheliming.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LeaveFormController {

    @Autowired
    private LeaveFormService leaveFormService;

    //添加请假单
    @GetMapping( "/writeForm")
    public ResultInfo writeForm(HttpServletRequest request){
        ResultInfo result = new ResultInfo();
        String days = request.getParameter("days");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String vacationType = request.getParameter("vacationType");
        String reason = request.getParameter("reason");

        LeaveFormDO leaveFormDO = new LeaveFormDO();
        leaveFormDO.setDays(Integer.parseInt(days));
        LeaveFormDO savedLeaveFormDO = leaveFormService.save(leaveFormDO);

        System.out.println(savedLeaveFormDO);

        result.setCode(200);
        result.setMsg("填写请假条成功");
        result.setInfo(savedLeaveFormDO);
        return result;
    }
}
