package com.zhy.zhy_ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 524202 on 2018/1/16.
 */

@DatabaseTable(tableName = "tb_usercoupon")
public class UserCoupon {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "idPerson")
    private String idPerson;

    @DatabaseField(columnName = "couponCode")
    private String couponCode;

    @DatabaseField(columnName = "status")
    private int status;

    public UserCoupon() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
