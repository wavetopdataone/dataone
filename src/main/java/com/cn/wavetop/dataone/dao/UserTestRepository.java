package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.User;
import com.cn.wavetop.dataone.entity.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/10、11:48
 */
@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    //@Query(value = "select * from user", nativeQuery = true)
    List<UserTest> findByUsername(String username);
}
