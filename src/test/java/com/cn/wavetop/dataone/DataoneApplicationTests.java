package com.cn.wavetop.dataone;

import com.cn.wavetop.dataone.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataoneApplicationTests {

    @Autowired
   private  RedisUtil redisUtil;
//    private RedisTemplate<String,Object> redisTemplate;
    @Test
    public void sawq(){
//        RedisUtil redisUtil=new RedisUtil();
        List<String> list=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("read","2000");
        map.put("write","3000");
        map.put("read","4000");
        list.add("1");
        list.add("薛子豪");
        redisUtil.set("a",list);
        redisUtil.set("b",map);
        System.out.println(redisUtil.get("a"));
        System.out.println(redisUtil.get("b"));

    }

}
