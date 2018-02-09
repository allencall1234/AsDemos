package com.example.showpic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 524202 on 2017/8/24.
 */

public class PreviewPhotoActivity extends FragmentActivity {

    private static final int MSG_NEXT = 100;
    private static final int MSG_DEFAULT_DURATION = 3 * 1000;

    private FixedViewPager mViewpager;
    private List<String> imageUrls;
    private ImagePagerAdapter mAdapter = null;
    private Context mContext;
    private int curItemPosition;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GestureView mGestureView;
    private RelativeLayout rl_black_bg;

    private Handler autoPlayHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_NEXT:
                    if (curItemPosition < imageUrls.size() - 1) {
                        curItemPosition++;
                        mViewpager.setCurrentItem(curItemPosition, true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setBackgroundDrawable(null);
        EventBus.getDefault().register(this);
        mContext = this;
        setContentView(R.layout.preview_layout);
        mViewpager = (FixedViewPager) findViewById(R.id.viewpager);

        if (imageUrls == null) {
            imageUrls = new ArrayList<>();
        }

        if (getIntent() != null) {
            List<String> imagelist = getIntent().getStringArrayListExtra("imagelist");
            if (imagelist != null) {
                imageUrls.addAll(imagelist);
            }
        }

        mAdapter = new ImagePagerAdapter();
        mViewpager.setPageMargin(10);
        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setAdapter(mAdapter);
        mViewpager.setPageTransformer(false, TransitionHelper.cubeInTransformer());
        setViewPagerScroller(mViewpager);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curItemPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("zlt","state = " + state);
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    autoPlayHandle.removeCallbacksAndMessages(null);
                } else {
                    autoPlayHandle.sendEmptyMessageDelayed(MSG_NEXT, MSG_DEFAULT_DURATION);
                }
            }
        });

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

        autoPlayHandle.sendEmptyMessageDelayed(MSG_NEXT, MSG_DEFAULT_DURATION);
    }

    private void setViewPagerScroller(ViewPager viewPager) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(this, (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, duration * 7);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(viewPager, scroller);
        } catch (Exception e) {
            // Do nothing.
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageListMessage event) {
        List<String> list = event.getImages();
        Log.d("showpic", "receiver message: " + Arrays.toString(list.toArray()));
        imageUrls.clear();
        imageUrls.addAll(list);
        mAdapter.notifyDataSetChanged();
        mViewpager.setCurrentItem(0, true);
        curItemPosition = 0;
        autoPlayHandle.removeCallbacksAndMessages(null);
        autoPlayHandle.sendEmptyMessageDelayed(MSG_NEXT, MSG_DEFAULT_DURATION);
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
        autoPlayHandle.removeCallbacksAndMessages(null);
    }
}
