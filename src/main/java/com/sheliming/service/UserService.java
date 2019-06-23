package com.sheliming.service;

import com.sheliming.domain.UserDO;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDO login(String username);
}