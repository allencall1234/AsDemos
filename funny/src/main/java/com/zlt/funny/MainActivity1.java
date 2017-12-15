package com.zlt.funny;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class MainActivity1 extends Activity {

    //    String BASE_RUL = "http://k3igj.3710011.cn/zbcPDK7x/004/zwgLCk/toutu.php?id=33&name=%E5%88%98%E6%B5%B7&sex=w";
    String BASE_RUL = "http://k3igj.3710011.cn/zbcPDK7x/004/zwgLCk/toutu.php?id=%1$d&name=%2$s&sex=%3$s";
    ImageView ivImageView;
    Button btnPre;
    Button btnNext;
    String name;
    String sex;
    int id = 1;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);

        name = getIntent().getStringExtra("name");
        sex = getIntent().getStringExtra("sex");

        ivImageView = (ImageView) findViewById(R.id.iv_image);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnPre = (Button) findViewById(R.id.btn_pre);

        loadImage();

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id > 1) {
                    id--;
                    loadImage();
                } else {
                    Toast.makeText(MainActivity1.this, "当前已经是第一张图片了", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id++;
                loadImage();
            }
        });
    }

    private void loadImage() {
        url = String.format(Locale.CHINA, BASE_RUL, id, name, sex);

        Glide.with(this).load(url).into(ivImageView);
    }
}
