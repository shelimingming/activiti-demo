package com.sheliming.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ApplyListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("----------" + delegateTask);
        delegateTask.setAssignee("1");
    }
}
