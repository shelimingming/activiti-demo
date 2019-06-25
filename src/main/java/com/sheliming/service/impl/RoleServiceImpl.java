package com.sheliming.service.impl;

import com.sheliming.dao.RoleDAO;
import com.sheliming.domain.RoleDO;
import com.sheliming.service.RoleService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private IdentityService identityService;

    @Override
    public RoleDO create(RoleDO roleDO) {
        RoleDO savedRoleDO = roleDAO.save(roleDO);

        //项目中每创建一个角色，对应的要创建一个Activiti用户组
        Group adminGroup=identityService.newGroup(savedRoleDO.getId().toString());
        adminGroup.setName(savedRoleDO.getName());
        identityService.saveGroup(adminGroup);

        return savedRoleDO;
    }
}
