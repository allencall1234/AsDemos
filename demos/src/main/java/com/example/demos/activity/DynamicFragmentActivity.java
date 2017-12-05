package com.example.demos.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demos.R;
import com.example.demos.fragment.DynamicFragment;

import org.w3c.dom.Text;

/**
 * Created by 524202 on 2017/10/9.
 */

public class DynamicFragmentActivity extends FragmentActivity {

    DynamicFragment fragment = null;
    FragmentManager fragmentManager = null;

    Button button = null;
    TextView emptyView = null;
    Context mContext;
    RelativeLayout contentLayout = null;
    boolean isAdd = false;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment_activity);

        mContext = this;
        fragmentManager = getSupportFragmentManager();
        fragment = DynamicFragment.newInstance();

        emptyView = (TextView) findViewById(R.id.empty_view);
        contentLayout = (RelativeLayout) findViewById(R.id.fragment_content_layout);
        fragmentManager.beginTransaction().add(R.id.fragment_content_layout, fragment);
        button = (Button) findViewById(R.id.move_add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posY = 600;
                if (isAdd) {
                    startAnimation(posY, 0);
                    fragmentManager.beginTransaction().hide(fragment);
                } else {
                    fragmentManager.beginTransaction().show(fragment);
                    startAnimation(0, posY);
                }

                isAdd = !isAdd;
            }
        });

    }

    public void startAnimation(int fromY, int toY) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
        animation.setFillAfter(true);
        animation.setDuration(1000);
        emptyView.startAnimation(animation);
    }
}
