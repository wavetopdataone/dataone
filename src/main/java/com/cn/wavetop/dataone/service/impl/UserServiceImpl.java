package com.cn.wavetop.dataone.service.impl;

import com.cn.wavetop.dataone.dao.UserRepository;
import com.cn.wavetop.dataone.dao.UserTestRepository;
import com.cn.wavetop.dataone.entity.User;
import com.cn.wavetop.dataone.entity.UserTest;
import com.cn.wavetop.dataone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Author yongz
 * @Date 2019/7/7、22:52
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserTestRepository repository2;

    @Override
    public List<User> getUserByUsername(String username) {
        List<User> users = repository.findByUsername(username);
        return users;
    }

    @Override
    public List<UserTest> getUserTestByUsername(String username) {
        List<UserTest> users = repository2.findByUsername(username);
        return users;
    }
}
