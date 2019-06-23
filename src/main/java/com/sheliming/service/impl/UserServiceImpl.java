package com.sheliming.service.impl;

import com.sheliming.dao.UserDAO;
import com.sheliming.domain.UserDO;
import com.sheliming.service.UserService;
import com.sheliming.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDO login(String username) {
        List<UserDO> userList = userDAO.findByName(username);
        if (userList != null && userList.size() > 0) {
            UserDO userDO = userList.get(0);
            return userDO;
        }
        return null;
    }
}
