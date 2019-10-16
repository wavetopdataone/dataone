package com.cn.wavetop.dataone;

import org.junit.Test;

import java.util.*;

public class test {

    @Test
    public void show() {
        List<Object> list = new ArrayList<>();
        String[] a="aa,bb,cc,".split(",");
        for(int b=0;b<a.length;b++){
            System.out.println(a[b]);
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
