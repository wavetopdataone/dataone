package com.cn.wavetop.dataone.dao;

import com.cn.wavetop.dataone.entity.SysFieldrule;
import com.cn.wavetop.dataone.entity.SysJobrela;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.util.List;


/**
 * @Author yongz
 * @Date 2019/10/11、15:47
 */
public interface SysJobrelaRespository   extends JpaRepository<SysJobrela,Long>
        , JpaSpecificationExecutor<SysJobrela> {


    Page<SysJobrela> findAll(Pageable pageable);
    boolean existsByDestNameOrSourceName(String name, String name1);
    boolean existsByDestIdOrSourceId(long id, long id1);
    List<SysJobrela> findAllByOrderByIdDesc();

    SysJobrela  findById(long id);

    SysJobrela findByJobName(String jobName);

    boolean existsByIdOrJobName(long id, String jobName);

    SysJobrela findByIdOrJobName(long id, String jobName);
    int countByJobStatusLike(String i);

    List<SysJobrela> findByJobNameContainingOrderByIdDesc(String job_name, Pageable pageable);
    List<SysJobrela> findByJobNameContainingOrderByIdDesc(String job_name);
   // @Query(value ="select * from SysJobrela where jobStatus like concat(:job_status,'%')" ,nativeQuery = true)
    List<SysJobrela> findByJobStatusLikeOrderByIdDesc(String job_status,Pageable pageable);
    List<SysJobrela> findByJobStatusLikeOrderByIdDesc(String job_status);

    boolean existsByJobName(String jobName);


    //根据用户id查询任务分页显示
    @Query(value = "select * from sys_jobrela j,sys_user u,sys_user_jobrela uj where u.id=uj.user_id and uj.jobrela_id=j.id and u.id=?1",nativeQuery = true)
    List<SysJobrela>  findByUserId(Long userId,Pageable pageable);
    @Query(value = "select * from sys_jobrela j,sys_user u,sys_user_jobrela uj where u.id=uj.user_id and uj.jobrela_id=j.id and u.id=?1",nativeQuery = true)
    List<SysJobrela> findByUserId(Long userId);
}
