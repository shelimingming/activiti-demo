package com.sheliming.service;

import com.sheliming.domain.RoleDO;
import com.sheliming.domain.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeaveFormServiceTest {
    @Autowired
    private LeaveFormService leaveFormService;

    @Test
    public void getUserLeaveFormTest() {
//        leaveFormService.getUserLeaveForm(2,1,10);
//        leaveFormService.approve("121212121212121212",true,"10022","2");
        leaveFormService.getCommentHistory(5);
    }

}
