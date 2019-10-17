package com.cn.wavetop.dataone;

import com.cn.wavetop.dataone.entity.SysFieldrule;
import org.junit.Test;

import java.util.*;

public class test {

    @Test
    public void show() {
        List<SysFieldrule> list = new ArrayList<>();
//        String[] a="aa,bb,cc,".split(",");
//        for(int b=0;b<a.length;b++){
//            System.out.println(a[b]);
//        }
        SysFieldrule s=new SysFieldrule();
         s.setSourceName("123");
         s.setDestName("abc");
        SysFieldrule s1=new SysFieldrule();
        s1.setSourceName("456");
        s1.setDestName("abc");
        SysFieldrule s2=new SysFieldrule();
        s2.setSourceName("123");
        s2.setDestName("abc");
        list.add(s);
    list.add(s1);
    Set<SysFieldrule> sysFieldrules=new HashSet<SysFieldrule>();
        sysFieldrules.add(s2);
        Iterator<SysFieldrule> iterator = list.iterator();
        while (iterator.hasNext()) {
            SysFieldrule num = iterator.next();
            if (sysFieldrules.contains(num)) {
                iterator.remove();
            }
        }
        for (SysFieldrule b : list) {
            System.out.println(b);
        }
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
