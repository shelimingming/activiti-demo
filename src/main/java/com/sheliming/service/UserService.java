package com.sheliming.service;

import com.sheliming.domain.RoleDO;
import com.sheliming.domain.UserDO;

public interface UserService {
    UserDO login(String username);

    UserDO create(UserDO userDO);

    RoleDO getUserRole(Integer userId);
}