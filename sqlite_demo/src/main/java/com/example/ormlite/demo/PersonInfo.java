package com.example.ormlite.demo;

/**
 * Created by 524202 on 2018/2/8.
 */

public class PersonInfo {
    private int age;
    private String sex;
    private String name;
    private int id;

    public PersonInfo() {

    }

    public PersonInfo(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
