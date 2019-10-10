package com.cn.wavetop.dataone.util;

import com.alibaba.druid.sql.visitor.functions.Hex;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public  class MD5Util {


    public static String getStrMD5(String str) {

// 获取MD5实例

        MessageDigest md5 =null;

        try {

            md5 = MessageDigest.getInstance("MD5");

        }catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            System.out.println(e.toString());

            return "获取MD5实例异常";

        }

// 将加密字符串转换为字符数组

        char[] charArray = str.toCharArray();

        byte[] byteArray =new byte[charArray.length];

        // 开始加密

        for (int i =0; i < charArray.length; i++)

            byteArray[i] = (byte) charArray[i];

        byte[] digest = md5.digest(byteArray);

        StringBuilder sb =new StringBuilder();

        for (int i =0; i < digest.length; i++) {

            int var = digest[i] &0xff;

            if (var <16)

                sb.append("0");

            sb.append(Integer.toHexString(var));

        }

        return sb.toString();

    }

    /**

     * 字符串MD5(大写+字母)

     *

     * @param password 要进行MD5的字符串

     */

    public static String getStrrMD5(String password) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {

            byte strTemp[] = password.getBytes("UTF-8");

            MessageDigest mdTemp = MessageDigest.getInstance("MD5");

            mdTemp.update(strTemp);

            byte md[] = mdTemp.digest();

            int j = md.length;

            char str[] =new char[j *2];

            int k =0;

            for (int i =0; i < j; i++) {

                byte byte0 = md[i];

                str[k++] = hexDigits[byte0 >>>4 &15];

                str[k++] = hexDigits[byte0 &15];

            }

            return new String(str);

        }catch (Exception e) {

            return null;

        }

    }

    /**

     * 加盐MD5

     */

    public static String getSaltMD5(String password) {

// 生成一个16位的随机数

        Random random =new Random();

        StringBuilder sBuilder =new StringBuilder(16);

        sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));

        int len = sBuilder.length();

        if (len <16) {

            for (int i =0; i <16 - len; i++) {

                sBuilder.append("0");

            }

        }

// 生成最终的加密盐

        String Salt = sBuilder.toString();

        password =md5Hex(password + Salt);

        char[] cs =new char[48];

        for (int i =0; i <48; i +=3) {

            cs[i] = password.charAt(i /3 *2);

            char c = Salt.charAt(i /3);

            cs[i +1] = c;

            cs[i +2] = password.charAt(i /3 *2 +1);

        }

        return String.valueOf(cs);

    }

    /**

     * 验证加盐后是否和原文一致

     * @param password  md5原文

     * @param md5str    加盐md5后的md5串

     */

    public static boolean getSaltverifyMD5(String password, String md5str) {

        char[] cs1 =new char[32];

        char[] cs2 =new char[16];

        for (int i =0; i <48; i +=3) {

            cs1[i /3 *2] = md5str.charAt(i);

            cs1[i /3 *2 +1] = md5str.charAt(i +2);

            cs2[i /3] = md5str.charAt(i +1);

        }

        String Salt =new String(cs2);

        return md5Hex(password + Salt).equals(String.valueOf(cs1));

    }

    /**

     * 单次加密，双次解密

     */

    public static StringgetconvertMD5(String inStr) {

        char[] charArray = inStr.toCharArray();

        for (int i =0; i < charArray.length; i++) {

            charArray[i] = (char) (charArray[i] ^'t');

        }

        String str = String.valueOf(charArray);

        return str;

    }

    /**

     * 使用Apache的Hex类实现Hex(16进制字符串和)和字节数组的互转

     */

    @SuppressWarnings("unused")

    private static Stringmd5Hex(String str) {

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digest = md.digest(str.getBytes());

            return new String(new Hex().encode(digest));

        }catch (Exception e) {

            e.printStackTrace();

            System.out.println(e.toString());

            return "";

        }

    }

    public static void main(String[] args) {

        System.out.println(getSaltMD5("admin"));

        System.out.println(getSaltverifyMD5("admin","092956b6b61a40a011f3076c650194991954f3584fa92220"));

        System.out.println(getconvertMD5("admin"));

        System.out.println(getconvertMD5(getconvertMD5("admin")));

    }

}



