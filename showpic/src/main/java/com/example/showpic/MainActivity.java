package com.example.showpic;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MSG_CHECK = 100;
    private static final int MSG_CHECK_SUCCESS = 101;

    private Button preview = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHECK:
                    ThreadPoolUtils.execute(new CheckThread());
                    break;
                case MSG_CHECK_SUCCESS:
                    ThreadPoolUtils.execute(new CheckThread());
                    break;
            }
        }
    };
    private ArrayList<String> sourceImageList;
    private ArrayList<String> curImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sourceImageList = new ArrayList<>();
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-12-18380140_455327614813449_854681840315793408_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-11-18380166_305443499890139_8426655762360565760_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-10-18382517_1955528334668679_3605707761767153664_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-09-18443931_429618670743803_5734501112254300160_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-08-18252341_289400908178710_9137908350942445568_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-05-18251898_1013302395468665_8734429858911748096_n.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/61e74233ly1feuogwvg27j20p00zkqe7.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-05-02-926821_1453024764952889_775781470_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-28-18094719_120129648541065_8356500748640452608_n.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/mw690/610dc034ly1ffwb7npldpj20u00u076z.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1fftusiwb8hj20u00zan1j.jpg");
        sourceImageList.add("http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
        sourceImageList.add("https://ws1.sinaimg.cn/large/610dc034ly1ffxjlvinj5j20u011igri.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-11-17881546_248332202297978_2420944671002853376_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-12-17662441_1675934806042139_7236493360834281472_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-13-17882785_926451654163513_7725522121023029248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-14-17881962_1329090457138411_8289893708619317248_n.jpg");
        sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
        startActivity(new Intent(this, PreviewPhotoActivity.class));
        handler.sendEmptyMessage(MSG_CHECK);

        curImageList = new ArrayList<>();

        preview = (Button) findViewById(R.id.preview_btn);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PreviewPhotoActivity.class);
                intent.putStringArrayListExtra("imagelist", curImageList);
                startActivity(intent);
            }
        });

        findViewById(R.id.et_input).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("zlt", "hasFocus = " + hasFocus);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class CheckThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                curImageList.clear();
                for (String id : sourceImageList) {
                    int rands = (int) (Math.random() * 100);
                    if (rands % 3 == 0) {
                        curImageList.add(id);
                    }
                }

                EventBus.getDefault().post(new ImageListMessage(curImageList));
                handler.sendEmptyMessageDelayed(MSG_CHECK_SUCCESS, 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                handler.sendEmptyMessageDelayed(MSG_CHECK_SUCCESS, 60 * 1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
