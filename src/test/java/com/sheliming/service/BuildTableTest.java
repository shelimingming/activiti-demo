package com.sheliming.service;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuildTableTest {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Test
    public void test() {
        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave");
        System.out.println("leave:" + leave);
    }

    @Test
    public void test2() {
        runtimeService.createProcessInstanceQuery().startedBy("");
    }

    @Test
    public void test3() {
//        taskService.setVariable("7505","");
        taskService.complete("7505");
    }

    @Test
    public void test4() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().list();
        System.out.println(list);
    }

}
