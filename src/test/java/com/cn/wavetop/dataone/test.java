package com.cn.wavetop.dataone;

import org.junit.Test;

import java.util.*;

public class test {

    @Test
    public void show() {
        List<Object> list = new ArrayList<>();
        list.add("aa,bb,cc".split(","));
        System.out.println(list.get(0));
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
