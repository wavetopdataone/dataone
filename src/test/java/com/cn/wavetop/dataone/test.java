package com.cn.wavetop.dataone;

import com.cn.wavetop.dataone.dao.SysUserRepository;
import com.cn.wavetop.dataone.entity.SysFieldrule;
import com.cn.wavetop.dataone.entity.SysJobrela;
import com.cn.wavetop.dataone.entity.SysUser;
import com.cn.wavetop.dataone.entity.SysUserJobrela;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class test {
    @Test
public void s(){

    List<SysJobrela> list=new ArrayList<>();
        SysJobrela sysUserJobrela=new SysJobrela();
    sysUserJobrela.setId(Long.valueOf(95));
    sysUserJobrela.setJobName("test1");
        SysJobrela sysUserJobrela1=new SysJobrela();
        sysUserJobrela1.setId(Long.valueOf(96));
        sysUserJobrela1.setJobName("test2");
    list.add(sysUserJobrela);
    list.add(sysUserJobrela1);
    List<SysJobrela> list1=new ArrayList<>();
    SysJobrela sysUserJobrela2=new SysJobrela();
        sysUserJobrela2.setId(Long.valueOf(95));
        sysUserJobrela2.setJobName("test1");
    list1.add(sysUserJobrela2);
        for (SysJobrela str:list) {
            if(list.contains(str)){
                list.remove(str);
            }
        }
        for (SysJobrela str:list1) {
            str.setJobStatus("1");
            list.add(str);
        }
//    for(SysJobrela set:list1){
//        list.add(set);
//    }
//        Set set = new HashSet();
//        List<SysJobrela> listNew=new ArrayList<>();
//        set.addAll(list);
//        listNew.addAll(set);
    for (SysJobrela s:list){
        System.out.println(s+"----");
    }
}

    @Test
    public void show() {
        String splits = "id,id,NUMBER,22,$,name,name,VARCHAR2,255,$";
        String[]  splitss= splits.replace("$","@").split(",@,");
     for(String s:splitss){
         System.out.println(s);
     }
//        List<SysFieldrule> list = new ArrayList<>();
////        String[] a="aa,bb,cc,".split(",");
////        for(int b=0;b<a.length;b++){
////            System.out.println(a[b]);
////        }
//        SysFieldrule s=new SysFieldrule();
//         s.setSourceName("123");
//         s.setDestName("abc");
//        SysFieldrule s1=new SysFieldrule();
//        s1.setSourceName("456");
//        s1.setDestName("abc");
//        SysFieldrule s3=new SysFieldrule();
//        s3.setSourceName("789");
//        s3.setDestName("abc");
//        SysFieldrule s2=new SysFieldrule();
//        s2.setSourceName("123");
//        s2.setDestName("abc");
//        SysFieldrule s4=new SysFieldrule();
//        s4.setSourceName("456");
//        s4.setDestName("abc");
//        list.add(s);
//        list.add(s3);
//    list.add(s1);
//    Set<SysFieldrule> sysFieldrules=new HashSet<SysFieldrule>();
//        sysFieldrules.add(s2);
//        sysFieldrules.add(s4);
//        Iterator<SysFieldrule> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            SysFieldrule num = iterator.next();
//            if (sysFieldrules.contains(num)) {
//                iterator.remove();
//            }
//        }
//        for (SysFieldrule b : list) {
//            System.out.println(b);
//        }
        //        Long a = Long.valueOf(5);
//        System.out.println(a.longValue() + a);
//        List<String> list = new ArrayList<>();
//        list.add("aa");
//        list.add("aa");
//        list.add("aa");
//        list.add("bb");
//        List<String> newList = new ArrayList();
//        newList.add("aa");
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            String num = iterator.next();
//            if (newList.contains(num)) {
//                iterator.remove();
//            }
//        }
//        for (String b : list) {
//            System.out.println(b);
//        }
    }
}
