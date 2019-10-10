package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author yongz
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    //@Query(value = "select * from user", nativeQuery = true)
    List<User> findByUsername(String username);
}
