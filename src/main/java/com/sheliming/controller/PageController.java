package com.sheliming.controller;

import com.sheliming.domain.LeaveFormDO;
import com.sheliming.domain.UserDO;
import com.sheliming.service.LeaveFormService;
import com.sheliming.service.UserService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 页面跳转相关Controller
 */
@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveFormService leaveFormService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    //员工的首页
    @GetMapping("/index")
    public String index(ModelMap model, HttpServletRequest request) {

        String username = getUsername(request);
        UserDO userDO = userService.login(username);

        List<LeaveFormDO> leaveFormDOList = leaveFormService.findByUserId(userDO.getId());

        List<HashMap<String, Object>> formsMap = new ArrayList<HashMap<String, Object>>();
        for (LeaveFormDO leaveFormDO : leaveFormDOList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", leaveFormDO.getId());
            map.put("days", leaveFormDO.getDays());
            map.put("beginDate", leaveFormDO.getBeginDate());
            map.put("endDate", leaveFormDO.getEndDate());
            map.put("reason", leaveFormDO.getReason());
            map.put("vacationType", leaveFormDO.getVacationType());
            map.put("processStatus", leaveFormDO.getProcessStatus());
            formsMap.add(map);
        }
        //将forms参数返回
        model.addAttribute("forms", formsMap);
        return "index";
    }


    //请假单页面
    @GetMapping("/form")
    public String form() {
        return "form";
    }

    //我的待办页面
    @GetMapping("/todo")
    public String todo(ModelMap model, HttpServletRequest request) {
        String username = getUsername(request);
        UserDO userDO = userService.login(username);

        List<LeaveFormDO> leaveFormDOList = leaveFormService.getUserTodo(userDO.getId(), 1, 10);

        List<HashMap<String, Object>> formsMap = new ArrayList<HashMap<String, Object>>();
        if (!CollectionUtils.isEmpty(leaveFormDOList)) {
            for (LeaveFormDO leaveFormDO : leaveFormDOList) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", leaveFormDO.getId());
                map.put("days", leaveFormDO.getDays());
                map.put("beginDate", leaveFormDO.getBeginDate());
                map.put("endDate", leaveFormDO.getEndDate());
                map.put("reason", leaveFormDO.getReason());
                map.put("vacationType", leaveFormDO.getVacationType());
                map.put("processStatus", leaveFormDO.getProcessStatus());
                map.put("processInstanceId", leaveFormDO.getProcessInstanceId());
                formsMap.add(map);
            }
        }
        //将forms参数返回
        model.addAttribute("forms", formsMap);
        return "todo";
    }

    //审批页面
    @GetMapping("/approve")
    public String approve(ModelMap model, HttpServletRequest request) {
        Integer formId = Integer.parseInt(request.getParameter("formId"));
        LeaveFormDO leaveFormDO = leaveFormService.findById(formId).get(0);

        List<Comment> commentHistoryList = leaveFormService.getCommentHistory(formId);
        model.addAttribute("form", leaveFormDO);
        model.addAttribute("commentHistoryList", commentHistoryList);
        return "approve";
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

}
