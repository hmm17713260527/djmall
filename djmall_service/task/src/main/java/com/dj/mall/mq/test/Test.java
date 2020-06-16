package com.dj.mall.mq.test;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mq.test
 * @ClassName: Test
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/12 11:50
 * @Version: 1.0
 */
public class Test {


    public static void main(String[] args) {
//        Integer a=-129;
//        Integer b=-129;
//        System.err.println(a==b);

        int c=200;
        int d=200;
        System.out.println(c==d);
        System.out.println("--------------");
        String s1="abc";
        String s2="abc";
        String s3=new String("abc");

        System.err.println(s1 == s2);
        System.err.println(s1 == s3);

        StringBuffer sb1=new StringBuffer("abc");
        StringBuffer sb2=new StringBuffer("abc");

        System.err.println(sb1 == sb2);
        System.err.println(sb1.equals(sb2));
        System.err.println(sb1.equals(sb1));

        int k=0;
        int ret= ++k + k++ + ++k + k++;
        System.err.println(ret);

    }

}
