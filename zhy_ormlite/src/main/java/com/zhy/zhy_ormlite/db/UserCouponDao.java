package com.zhy.zhy_ormlite.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.zhy.zhy_ormlite.bean.User;
import com.zhy.zhy_ormlite.bean.UserCoupon;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 524202 on 2018/1/16.
 */

public class UserCouponDao {
    private Dao<UserCoupon, Integer> userCouponDaoOpe;
    private DatabaseHelper helper;

    @SuppressWarnings("unchecked")
    public UserCouponDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            userCouponDaoOpe = helper.getDao(UserCoupon.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个
     *
     * @param userCoupon
     */
    public void addOrUpdate(UserCoupon userCoupon) {
        try {

            UserCoupon item = get(userCoupon.getIdPerson(), userCoupon.getCouponCode());
            if (item != null) {
                item.setStatus(userCoupon.getStatus());
                userCouponDaoOpe.update(item);
            } else {
                userCouponDaoOpe.create(userCoupon);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过idPerson和couponCode查询唯一优惠券
     *
     * @param idPerson
     * @param couponCode
     * @return
     */
    public UserCoupon get(String idPerson, String couponCode) {
        List<UserCoupon> userCoupons = null;
        try {
            userCoupons = userCouponDaoOpe.queryBuilder().where().eq("idPerson", idPerson)
                    .and().eq("couponCode", couponCode).query();
            return userCoupons.size() > 0 ? userCoupons.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 通过Id得到一篇文章
     *
     * @param id
     * @return
     */
    public UserCoupon get(int id) {
        UserCoupon userCoupon = null;
        try {
            userCoupon = userCouponDaoOpe.queryForId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userCoupon;
    }

    /**
     * 通过UserId获取所有的文章
     *
     * @param userId
     * @return
     */
    public List<UserCoupon> listByUserId(int userId) {
        try {
            /*QueryBuilder<Article, Integer> articleBuilder = articleDaoOpe
                    .queryBuilder();
			QueryBuilder userBuilder = helper.getDao(User.class).queryBuilder();
			articleBuilder.join(userBuilder);


			Where<Article, Integer> where = queryBuilder.where();
			where.eq("user_id", 1);
			where.and();
			where.eq("name", "xxx");

			// 或者
			articleDaoOpe.queryBuilder().//
					where().//
					eq("user_id", 1).and().//
					eq("name", "xxx");
			//
			articleDaoOpe.updateBuilder().updateColumnValue("name","zzz").where().eq("user_id", 1);
			where.or(
					//
					where.and(//
							where.eq("user_id", 1), where.eq("name", "xxx")),
					where.and(//
							where.eq("user_id", 2), where.eq("name", "yyy")));*/

            return userCouponDaoOpe.queryBuilder().where().eq("idPerson", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
