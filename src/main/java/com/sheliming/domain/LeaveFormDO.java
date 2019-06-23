package com.sheliming.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

//请假信息表
@Entity
@Table(name = "leave_form")
public class LeaveFormDO {
    @Id
    @GeneratedValue
    private Integer id;

    private String processInstanceId;

    private Integer userId;

    private Integer days;

    private Date beginDate;

    private Date endDate;

    //请假类型：1带薪假 2病假 3事假
    private Integer vacationType;

    private String reason;

    //流程状态：0申请 1审批中 2已完成
    private Integer processStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getVacationType() {
        return vacationType;
    }

    public void setVacationType(Integer vacationType) {
        this.vacationType = vacationType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "LeaveFormDO{" +
                "id=" + id +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", userId=" + userId +
                ", days=" + days +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", vacationType=" + vacationType +
                ", reason='" + reason + '\'' +
                ", processStatus=" + processStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
