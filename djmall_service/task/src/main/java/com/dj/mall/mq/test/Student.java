package com.dj.mall.mq.test;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Data;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mq.test
 * @ClassName: Student
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/11 16:09
 * @Version: 1.0
 */
@Data
@Builder
public class Student {

    private String userName;

    private String subject;

    private Integer grade;


    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();

        list.add(Student.builder().userName("zs").subject("java").grade(99).build());
        list.add(Student.builder().userName("zs").subject(".net").grade(70).build());
        list.add(Student.builder().userName("ls").subject(".net").grade(90).build());
        list.add(Student.builder().userName("ls").subject("java").grade(80).build());
        list.add(Student.builder().userName("ww").subject("java").grade(62).build());
        list.add(Student.builder().userName("zl").subject("java").grade(55).build());

        //每个人分组
        Map<String, List<Student>> collect = list.stream().collect(Collectors.groupingBy(Student::getUserName));
        System.out.println(collect.toString());

        //每个人的总成绩
        TreeMap<String, Integer> collect5 = list.stream().collect(Collectors.groupingBy(Student::getUserName, TreeMap::new, Collectors.summingInt(o -> o.getGrade())));
        System.out.println(collect5.toString());

        //每个人的科目个数
        Map<String, Long> collect4 = list.stream().collect(Collectors.groupingBy(Student::getUserName, Collectors.counting()));
        System.out.println(collect4.toString());

        //成绩大于60的
        List<Student> collect1 = list.stream().filter(std -> std.grade > 60).collect(Collectors.toList());
        System.out.println(collect1.toString());

        //成绩大于70的
        List<Student> collect2 = list.stream().filter(std -> std.grade > 70).collect(Collectors.toList());
        System.out.println(collect2.toString());

        //正序
        List<Student> collect3 = list.stream().sorted(Comparator.comparing(Student::getGrade)).collect(Collectors.toList());
        System.out.println(collect3.toString());


    }







}
