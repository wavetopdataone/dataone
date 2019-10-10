package com.cn.wavetop.dataone.service;



import com.cn.wavetop.dataone.entity.User;
import com.cn.wavetop.dataone.entity.UserTest;

import java.util.List;

public interface UserService {

     List<User> getUserByUsername(String username);

     List<UserTest> getUserTestByUsername(String username);
}
