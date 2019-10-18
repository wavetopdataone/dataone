package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysDbinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author yongz
 * @Date 2019/10/11、14:37
 */
@Repository
public interface SysDbinfoRespository  extends JpaRepository<SysDbinfo,Long> {


    List<SysDbinfo> findBySourDest(long i);


    boolean existsByIdOrName(long id, String name);

    SysDbinfo findByIdOrName(long id, String name);

    SysDbinfo findByNameAndSourDest(String name, long SourDest);

    boolean existsByName(String name);

    SysDbinfo findByName(String name);

    SysDbinfo findById(long id);
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