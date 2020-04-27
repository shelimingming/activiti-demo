package com.sheliming.controller;

import com.sheliming.domain.LeaveFormDO;
import com.sheliming.service.LeaveFormService;
import com.sheliming.util.ResultInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("leaveForm")
public class LeaveFormController {

    @Autowired
    private LeaveFormService leaveFormService;

    //添加请假单
    @PostMapping("/writeForm")
    public ResultInfo writeForm(HttpServletRequest request) {
        ResultInfo result = new ResultInfo();
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String vacationType = request.getParameter("vacationType");
        String reason = request.getParameter("reason");


        LeaveFormDO leaveFormDO = new LeaveFormDO();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + ":" + cookie.getValue());
            if ("userInfo".equals(cookie.getName())) {
                leaveFormDO.setUserId(Integer.parseInt(cookie.getValue()));
            }
        }

        leaveFormDO.setCreateTime(new Date());
        leaveFormDO.setUpdateTime(new Date());
        leaveFormDO.setVacationType(Integer.parseInt(vacationType));
        leaveFormDO.setReason(reason);
        try {
            leaveFormDO.setBeginDate(DateFormat.getDateInstance().parse(beginDate));
            leaveFormDO.setEndDate(DateFormat.getDateInstance().parse(endDate));
            leaveFormDO.setDays(getLeaveDays(DateFormat.getDateInstance().parse(beginDate), DateFormat.getDateInstance().parse(endDate)));
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

    //审批请假单
    @PostMapping("/approve")
    public ResultInfo approve(HttpServletRequest request) {
        ResultInfo result = new ResultInfo();
        Integer formId = Integer.parseInt(request.getParameter("formId"));
        String comment = request.getParameter("comment");
        Integer isApprove = Integer.parseInt(request.getParameter("isApprove"));

        leaveFormService.approve(formId,comment,isApprove, getUsername(request));

        result.setCode(200);
        result.setMsg("审批成功");
        return result;
    }

    /**
     * 从cookie里获取用户名
     *
     * @param request
     * @return
     */
    private String getUsername(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String username = "";
        //从cookie中获取当前用户
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userInfo")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        return username;
    }

    public int getLeaveDays(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }
}
