package com.sheliming.controller;

import com.sheliming.domain.LeaveFormDO;
import com.sheliming.service.LeaveFormService;
import com.sheliming.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@RestController
public class LeaveFormController {

    @Autowired
    private LeaveFormService leaveFormService;

    //添加请假单
    @PostMapping( "/writeForm")
    public ResultInfo writeForm(HttpServletRequest request){
        ResultInfo result = new ResultInfo();
        String days = request.getParameter("days");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String vacationType = request.getParameter("vacationType");
        String reason = request.getParameter("reason");



        LeaveFormDO leaveFormDO = new LeaveFormDO();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            System.out.println(cookie.getName()+":"+cookie.getValue());
            if("userInfo".equals(cookie.getName())) {
                leaveFormDO.setUserId(Integer.parseInt(cookie.getValue()));
            }
        }
        leaveFormDO.setDays(Integer.parseInt(days));
        leaveFormDO.setCreateTime(new Date());
        leaveFormDO.setUpdateTime(new Date());
        try {
            leaveFormDO.setBeginDate(DateFormat.getDateInstance().parse(beginDate));
            leaveFormDO.setEndDate(DateFormat.getDateInstance().parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        LeaveFormDO savedLeaveFormDO = leaveFormService.writeForm(leaveFormDO);

        System.out.println(savedLeaveFormDO);

        result.setCode(200);
        result.setMsg("填写请假条成功");
        result.setInfo(savedLeaveFormDO);
        return result;
    }
}
