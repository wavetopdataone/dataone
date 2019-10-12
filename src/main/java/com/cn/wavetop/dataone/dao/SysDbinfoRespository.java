package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysDbinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/11、14:37
 */
public interface SysDbinfoRespository  extends JpaRepository<SysDbinfo,Long> {


    List<SysDbinfo> findBySourDest(long i);


    boolean existsByIdOrName(long id, String name);

    SysDbinfo findByIdOrName(long id, String name);

//    @Modifying
//    @Query("update SysDbinfo u set " +
//            " u.host = :host," +
//            " u.user = :user," +
//            " u.password = :password," +
//            " u.name = :name," +
//            " u.dbname = :dbname," +
//            " u.schema = :schema," +
//            " u.port = :port," +
//            //"u.sourDest = :sourDest," +
//            " u.type = :type " +
//            "where u.Id = :id")
//    void updateById(String host,String user,String password,String name,String dbname,String schema,long port,long type,long id);
}