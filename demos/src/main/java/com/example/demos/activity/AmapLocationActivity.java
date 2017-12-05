package com.example.demos.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.demos.R;

/**
 * Created by 524202 on 2017/12/4.
 */

public class AmapLocationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultText1, resultText2, resultText3;
    Button locationType1, locationType2, locationType3;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationListener aMapLocationListener1 = null;
    private AMapLocationListener aMapLocationListener2 = null;
    private AMapLocationListener aMapLocationListener3 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amap_location_activity);

        resultText1 = (TextView) findViewById(R.id.result_text_1);
        resultText2 = (TextView) findViewById(R.id.result_text_2);
        resultText3 = (TextView) findViewById(R.id.result_text_3);

        locationType1 = (Button) findViewById(R.id.location_type_1);
        locationType2 = (Button) findViewById(R.id.location_type_2);
        locationType3 = (Button) findViewById(R.id.location_type_3);

        locationType1.setOnClickListener(this);
        locationType2.setOnClickListener(this);
        locationType3.setOnClickListener(this);

        initAmap();
    }

    private void initAmap() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_type_1:
                startLocation1();
                break;
            case R.id.location_type_2:
                startLocation2();
                break;
            case R.id.location_type_3:
                startLocation3();
                break;
            default:
                break;
        }
    }

    private void startLocation1() {
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mLocationClient.setLocationOption(mLocationOption);
        aMapLocationListener1 = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        double latitude = aMapLocation.getLatitude();
                        double longitude = aMapLocation.getLongitude();
                        String province = aMapLocation.getProvince();
                        String city = aMapLocation.getCity();
                        String street = aMapLocation.getStreet();
                        String result = "位置[" + latitude + "," + longitude + "]," + province + city + street;
                        resultText1.setText(result);
                    }
                }
                mLocationClient.unRegisterLocationListener(aMapLocationListener1);
                mLocationClient.stopLocation();
            }
        };
        mLocationClient.setLocationListener(aMapLocationListener1);
        mLocationClient.startLocation();
    }

    private void startLocation2() {
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mLocationClient.setLocationOption(mLocationOption);
        aMapLocationListener2 = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        double latitude = aMapLocation.getLatitude();
                        double longitude = aMapLocation.getLongitude();
                        String province = aMapLocation.getProvince();
                        String city = aMapLocation.getCity();
                        String street = aMapLocation.getStreet();
                        String result = "位置[" + latitude + "," + longitude + "]," + province + city + street;
                        resultText2.setText(result);
                    }
                }
                mLocationClient.unRegisterLocationListener(aMapLocationListener2);
                mLocationClient.stopLocation();
            }
        };
        mLocationClient.setLocationListener(aMapLocationListener2);
        mLocationClient.startLocation();
    }

    private void startLocation3() {
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mLocationClient.setLocationOption(mLocationOption);
        aMapLocationListener3 = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        double latitude = aMapLocation.getLatitude();
                        double longitude = aMapLocation.getLongitude();
                        String province = aMapLocation.getProvince();
                        String city = aMapLocation.getCity();
                        String street = aMapLocation.getStreet();
                        String result = "位置[" + latitude + "," + longitude + "]," + province + city + street;
                        resultText3.setText(result);
                    }
                }
                mLocationClient.unRegisterLocationListener(aMapLocationListener3);
                mLocationClient.stopLocation();
            }
        };
        mLocationClient.setLocationListener(aMapLocationListener3);
        mLocationClient.startLocation();
    }
}
