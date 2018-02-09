package com.zhy.zhy_ormlite;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.j256.ormlite.dao.Dao;
import com.zhy.zhy_ormlite.bean.Student;
import com.zhy.zhy_ormlite.bean.UserCoupon;
import com.zhy.zhy_ormlite.db.DatabaseHelper;
import com.zhy.zhy_ormlite.db.UserCouponDao;
import com.zhy.zhy_ormlite.db.UserDao;

import java.sql.SQLException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        try {
            UserCoupon student = new UserCoupon();
            student.setIdPerson("30078");
            student.setCouponCode("C000001");
            student.setStatus(5);
            new UserCouponDao(getApplicationContext()).addOrUpdate(student);

            UserCoupon student2 = new UserCoupon();
            student2.setIdPerson("30078");
            student2.setCouponCode("C000002");
            student2.setStatus(2);
            new UserCouponDao(getApplicationContext()).addOrUpdate(student2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
