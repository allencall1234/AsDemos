package com.example.ormlite.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    private void initData() {
        PersonInfo personInfo = new PersonInfo("Tom", "female", 18);
        if (!PersonInfoDao.getInstance(this).query(personInfo)) {
            PersonInfoDao.getInstance(this).insert(personInfo);
            Log.d("zlt", "insert data success!");
        } else {
            PersonInfoDao.getInstance(this).delete(personInfo);
        }
    }

}
