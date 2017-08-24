package com.example.showpic;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 524202 on 2017/8/24.
 */

public class PreviewPhotoActivity extends FragmentActivity {

    private FixedViewPager mViewpager;
    private List<String> imageUrls;
    private ImagePagerAdapter mAdapter = null;
    private Context mContext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private GestureView mGestureView;
    private RelativeLayout rl_black_bg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(null);
        EventBus.getDefault().register(this);
        mContext = this;
        setContentView(R.layout.preview_layout);
        mViewpager = (FixedViewPager) findViewById(R.id.viewpager);

        if (imageUrls == null) {
            imageUrls = new ArrayList<>();
        }
        mAdapter = new ImagePagerAdapter();
        mViewpager.setAdapter(mAdapter);
        mViewpager.setPageTransformer(false, TransitionHelper.cubeInTransformer());

        rl_black_bg = (RelativeLayout) findViewById(R.id.rl_background);

        mGestureView = (GestureView) findViewById(R.id.gestureview);
        mGestureView.setOnSwipeListener(new GestureView.OnSwipeListener() {
            @Override
            public void downSwipe() {
            }

            @Override
            public void onSwiping(float deltaY) {

                float mAlpha = 1 - deltaY / 500;
                if (mAlpha < 0.3) {
                    mAlpha = 0.3f;
                }
                if (mAlpha > 1) {
                    mAlpha = 1;
                }
                rl_black_bg.setAlpha(mAlpha);
            }

            @Override
            public void overSwipe() {
                rl_black_bg.setAlpha(1);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageListMessage event) {
        List<String> list = event.getImages();
        Log.d("showpic", "receiver message: " + Arrays.toString(list.toArray()));
        imageUrls.clear();
        imageUrls.addAll(list);
        mAdapter.notifyDataSetChanged();
        mViewpager.setCurrentItem(0, true);
    }


    private class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.preview_item_layout, null);
            final PhotoView item_photo = (PhotoView) view.findViewById(R.id.item_photoview);
            final ProgressWheel item_progress = (ProgressWheel) view.findViewById(R.id.item_progress);

            Target target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                    item_photo.setImageBitmap(bitmap);
                    item_progress.setVisibility(View.GONE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    item_photo.setImageResource(R.mipmap.mn_icon_load_fail);
                    item_progress.setVisibility(View.GONE);
                }
            };

            Glide.with(mContext).load(imageUrls.get(position)).asBitmap().into(target);


            container.addView(view);
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
