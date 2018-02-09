package com.example.ormlite.demo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 524202 on 2018/1/26.
 */

public class PersonInfoDao {
    private static final String TAG = "DatabaseManager";
    // 静态引用
    private volatile static PersonInfoDao mInstance;
    // DatabaseHelper
    private DBHelper dbHelper;

    private PersonInfoDao(Context context) {
        dbHelper = new DBHelper(context.getApplicationContext());
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static PersonInfoDao getInstance(Context context) {
        PersonInfoDao inst = mInstance;
        if (inst == null) {
            synchronized (PersonInfoDao.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new PersonInfoDao(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    public void updateOrInsert(PersonInfo info) {
        if (query(info)) {
            update(info);
        } else {
            insert(info);
        }
    }

    public void insert(PersonInfo info) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO " + DBHelper.TABLE_NAME
                + " ("
                + DBHelper.FIELD_NAME + ","
                + DBHelper.FIELD_SEX + ","
                + DBHelper.FIELD_AGE
                + ") "
                + "VALUES"
                + " ("
                + "\'" + info.getName() + "\'" + ","
                + "\'" + info.getSex() + "\'" + ","
                + info.getAge() +
                ")");
        db.close();
    }

    public boolean query(PersonInfo info) {
        //指定要查询的是哪几列数据
        String[] columns = {DBHelper.FIELD_NAME};
        //获取可读数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //查询数据库

        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append(DBHelper.FIELD_NAME).append(" = ").append("'").append(info.getName()).append("'");
        Cursor cursor = null;
        boolean result = false;
        try {
            cursor = db.query(DBHelper.TABLE_NAME, columns, whereBuffer.toString(), null, null, null, null);//获取数据游标
            if (cursor == null) {
                return false;
            }

            result = cursor.moveToFirst();
            cursor.close();
        } catch (SQLException e) {
        }
        //关闭数据库
        db.close();

        return result;
    }

    public void update(PersonInfo info) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE " + DBHelper.TABLE_NAME
                + " SET "
                + DBHelper.FIELD_AGE + " = "
                + "\'" + info.getAge() + "\'" + ","
                + DBHelper.FIELD_SEX + " = "
                + "\'" + info.getSex() + "\'"
                + " WHERE "
                + DBHelper.FIELD_NAME + " = "
                + "\'" + info.getName() + "\'");
        db.close();
    }

    public void delete(PersonInfo info) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DBHelper.TABLE_NAME
                + " WHERE "
                + DBHelper.FIELD_NAME +
                " = "
                + "\'" + info.getName() + "\'");
        db.close();
    }

}