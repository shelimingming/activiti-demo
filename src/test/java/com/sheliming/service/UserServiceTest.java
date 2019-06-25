package com.sheliming.service;

import com.sheliming.domain.RoleDO;
import com.sheliming.domain.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    public void createUserAndRoleTest() {
        RoleDO employeeRoleDO = new RoleDO();
        employeeRoleDO.setId(1);
        employeeRoleDO.setName("员工");
        roleService.create(employeeRoleDO);

        RoleDO teamleaderRoleDO = new RoleDO();
        teamleaderRoleDO.setId(2);
        teamleaderRoleDO.setName("组长");
        roleService.create(teamleaderRoleDO);


        RoleDO directorRoleDO = new RoleDO();
        directorRoleDO.setId(3);
        directorRoleDO.setName("经理");
        roleService.create(directorRoleDO);

        RoleDO hrRoleDO = new RoleDO();
        hrRoleDO.setId(4);
        hrRoleDO.setName("人力主管");
        roleService.create(hrRoleDO);


        UserDO userDO1 = new UserDO();
        userDO1.setId(1);
        userDO1.setName("1");
        userDO1.setRoleId(1);
        userService.create(userDO1);

        UserDO userDO2 = new UserDO();
        userDO2.setId(2);
        userDO2.setName("2");
        userDO2.setRoleId(2);
        userService.create(userDO2);

        UserDO userDO3 = new UserDO();
        userDO3.setId(3);
        userDO3.setName("3");
        userDO3.setRoleId(3);
        userService.create(userDO3);

        UserDO userDO4 = new UserDO();
        userDO4.setId(4);
        userDO4.setName("4");
        userDO4.setRoleId(4);
        userService.create(userDO4);
    }

}
