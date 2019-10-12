package com.cn.wavetop.dataone;

import org.junit.Test;

public class test {

    @Test
    public void show(){
        String a ="test,sasd,dsad";
      String[] b=  a.split(",");
      for(int i=0;i<b.length;i++) {
          System.out.println(b[i]);
      }
    }
}
