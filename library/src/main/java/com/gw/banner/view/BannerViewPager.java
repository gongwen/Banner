package com.gw.banner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.gw.banner.R;
import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.listener.OnPageChangeListenerWrapper;
import com.gw.banner.util.DefaultBannerConfig;

/**
 * Created by GongWen on 17/11/9.
 */

public class BannerViewPager extends ViewPager {
    private OnPageChangeListenerWrapper mOnPageChangeListenerWrapper;
    private boolean isJustOnceFlag = true;
    private int scrollState = SCROLL_STATE_IDLE;
    private int delayTime;
    private boolean isAutoPlay;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOnPageChangeListenerWrapper = new OnPageChangeListenerWrapper() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                scrollState = state;
                final int count = getAdapter().getRealCount();
                final int currentItem = getCurrentItem();
                switch (state) {
                    case SCROLL_STATE_IDLE://No operation
                    case SCROLL_STATE_DRAGGING://start Sliding
                        if (currentItem == 0) {
                            setCurrentItem(count, false);
                        } else if (currentItem == count + 1) {
                            setCurrentItem(1, false);
                        }
                        break;
                    case SCROLL_STATE_SETTLING://end Sliding
                        break;
                }
            }
        };
        addOnPageChangeListener(mOnPageChangeListenerWrapper);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
            delayTime = a.getInt(R.styleable.BannerViewPager_delayTime, DefaultBannerConfig.TIME);
            isAutoPlay = a.getBoolean(R.styleable.BannerViewPager_isAutoPlay, DefaultBannerConfig.IS_AUTO_PLAY);
            a.recycle();
        }
    }

    //<editor-fold desc="重写setAdapter,getAdapter">
    @Override
    public BannerPagerAdapter getAdapter() {
        return (BannerPagerAdapter) super.getAdapter();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (!(adapter instanceof BannerPagerAdapter)) {
            throw new IllegalArgumentException("Only BannerPagerAdapter is supported!");
        }
        mOnPageChangeListenerWrapper.attachAdapter((BannerPagerAdapter) adapter);
        super.setAdapter(adapter);
        if (((BannerPagerAdapter) adapter).getRealCount() > 1) {
            setCurrentItem(1);
        }
    }
    //</editor-fold>

    //<editor-fold desc="重写setOnPageChangeListener,addOnPageChangeListener,removeOnPageChangeListener">
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListenerWrapper.onPageChangeListenerWrapper(listener);
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (isJustOnceFlag) {
            super.addOnPageChangeListener(listener);
            isJustOnceFlag = false;
            return;
        }
        mOnPageChangeListenerWrapper.onPageChangeListenersWrapper(listener);
    }

    @Override
    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListenerWrapper.removeOnPageChangeListenerWrapper(listener);
    }
    //</editor-fold>

    //<editor-fold desc="自动播放">
    // TODO: 17/11/9
    public void startAutoPlay() {
        removeCallbacks(task);
        postDelayed(task, delayTime);
    }

    public void stopAutoPlay() {
        removeCallbacks(task);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            final int count = getAdapter().getRealCount();
            if (count > 1) {
                if (scrollState == SCROLL_STATE_DRAGGING) {
                    postDelayed(task, delayTime);
                } else {
                    int currentItem = getCurrentItem();
                    currentItem = currentItem % (count + 1) + 1;
                    if (currentItem == 1) {
                        setCurrentItem(currentItem, false);
                        post(task);
                    } else {
                        setCurrentItem(currentItem);
                        postDelayed(task, delayTime);
                    }
                }
            }
        }
    };

    //</editor-fold>
    // TODO: 17/11/9
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoPlay();
    }
}