package com.sheliming.service.impl;

import com.sheliming.dao.RoleDAO;
import com.sheliming.dao.UserDAO;
import com.sheliming.domain.RoleDO;
import com.sheliming.domain.UserDO;
import com.sheliming.service.UserService;
import com.sheliming.util.ResultInfo;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private IdentityService identityService;

    @Override
    public UserDO login(String username) {
        List<UserDO> userList = userDAO.findByName(username);
        if (userList != null && userList.size() > 0) {
            UserDO userDO = userList.get(0);
            return userDO;
        }
        return null;
    }

    @Override
    public UserDO create(UserDO userDO) {
        UserDO savedUserDO = userDAO.save(userDO);


        //项目中每创建一个新用户，对应的要创建一个Activiti用户
        //两者的userId和userName一致
        User admin = identityService.newUser(savedUserDO.getId().toString());
        admin.setLastName(savedUserDO.getName());
        identityService.saveUser(admin);

        //用户与用户组关系绑定
        identityService.createMembership(savedUserDO.getId().toString(), savedUserDO.getRoleId().toString());
        return savedUserDO;
    }

    @Override
    public RoleDO getUserRole(Integer userId) {
        List<RoleDO> roleDOList = roleDAO.findById(userId);
        if (!CollectionUtils.isEmpty(roleDOList)) {
            return roleDOList.get(0);
        } else {
            return null;
        }

    }
}
