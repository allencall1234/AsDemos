package com.example.ormlite.demo;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;


/**
 * Created by 524202 on 2018/2/8.
 */
public class PersonInfoDaoTest {

    @Test
    public void insert() throws Exception {
        PersonInfoDao dao = PersonInfoDao.getInstance(InstrumentationRegistry.getTargetContext());
        PersonInfo info = new PersonInfo("Rose","female",15);
        dao.insert(info);
    }

    @Test
    public void query() throws Exception {
        PersonInfoDao dao = PersonInfoDao.getInstance(InstrumentationRegistry.getTargetContext());
        PersonInfo info = new PersonInfo("vicky","female",17);
        boolean result = dao.query(info);
        System.out.print("result = " + result);
    }

    @Test
    public void update() throws Exception {
        PersonInfoDao dao = PersonInfoDao.getInstance(InstrumentationRegistry.getTargetContext());
        PersonInfo info = new PersonInfo("zlt","male",22);
        dao.update(info);
    }

    @Test
    public void delete() throws Exception {
        PersonInfoDao dao = PersonInfoDao.getInstance(InstrumentationRegistry.getTargetContext());
        PersonInfo info = new PersonInfo("Jim","female",17);
        dao.delete(info);
    }

}