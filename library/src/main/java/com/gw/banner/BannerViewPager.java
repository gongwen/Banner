package com.gw.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.listener.OnPageChangeListenerWrapper;
import com.gw.banner.util.BannerConfig;
import com.gw.banner.util.WeakHandler;

/**
 * Created by GongWen on 17/11/9.
 */

public class BannerViewPager extends ViewPager {
    private OnPageChangeListenerWrapper mOnPageChangeListenerWrapper;
    private boolean isJustOnceFlag = true;
    private long delayTime;
    private boolean isAutoPlay;

    private WeakHandler handler = new WeakHandler();

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOnPageChangeListenerWrapper = new OnPageChangeListenerWrapper() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
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
            delayTime = a.getInt(R.styleable.BannerViewPager_delayTime, BannerConfig.TIME);
            isAutoPlay = a.getBoolean(R.styleable.BannerViewPager_isAutoPlay, BannerConfig.IS_AUTO_PLAY);
            a.recycle();
        }
    }

    //<editor-fold desc="重写setAdapter,getAdapter">
    @Override
    public BannerPagerAdapter getAdapter() {
        return (BannerPagerAdapter) super.getAdapter();
    }

    @Override
    public void setAdapter(final PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter == null) {
            return;
        }
        if (!(adapter instanceof BannerPagerAdapter)) {
            throw new IllegalArgumentException("Only BannerPagerAdapter is supported!");
        }
        adapter.registerDataSetObserver(dataSetObserver);
        mOnPageChangeListenerWrapper.attachAdapter((BannerPagerAdapter) adapter);
        onDataSetChanged();
    }
    //</editor-fold>

    //<editor-fold desc="重写setOnPageChangeListener,addOnPageChangeListener,removeOnPageChangeListener,clearOnPageChangeListeners">
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

    @Override
    public void clearOnPageChangeListeners() {
        mOnPageChangeListenerWrapper.clearOnPageChangeListenersWrapper();
    }
    //</editor-fold>

    //<editor-fold desc="自动播放">
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay(true);
            } else if (action == MotionEvent.ACTION_UP
                    || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay(true);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void startAutoPlay() {
        startAutoPlay(false);
    }

    public void stopAutoPlay() {
        stopAutoPlay(false);
    }

    public void setAutoPlay(boolean isAutoPlay) {
        if (this.isAutoPlay != isAutoPlay) {
            this.isAutoPlay = isAutoPlay;
            if (isAutoPlay) {
                startAutoPlay(true);
            } else {
                stopAutoPlay(true);
            }
        }
    }

    /**
     * @param isInnerCall 是否BannerViewPager内部调用
     */
    private void startAutoPlay(boolean isInnerCall) {
        if (!isInnerCall && !isAutoPlay) {
            isAutoPlay = true;
        }
        if (isShouldAutoPlay()) {
            handler.removeCallbacks(task);
            handler.postDelayed(task, delayTime);
        }
    }

    /**
     * @param isInnerCall 是否BannerViewPager内部调用
     */
    private void stopAutoPlay(boolean isInnerCall) {
        if (!isInnerCall && isAutoPlay) {
            isAutoPlay = false;
        }
        handler.removeCallbacks(task);
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    /**
     * isAutoPlay == false 时，task停止
     * isAutoPlay == true时，分以下两种情况
     * 1. 手指touch屏幕时，task停止
     * 2. count<=1时，task停止
     */
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            final int count = getAdapter().getRealCount();
            int currentItem = getCurrentItem();
            currentItem = currentItem % (count + 1) + 1;
            if (currentItem == 1) {
                setCurrentItem(currentItem, false);
                handler.post(task);
            } else {
                setCurrentItem(currentItem);
                handler.postDelayed(task, delayTime);
            }
        }
    };

    //</editor-fold>
    private boolean isShouldAutoPlay() {
        return isAutoPlay && getAdapter() != null && getAdapter().getRealCount() > 1;
    }

    private final DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            onDataSetChanged();
        }
    };

    private void onDataSetChanged() {
        if (getAdapter() != null && getAdapter().getRealCount() > 1) {
            setCurrentItem(1);
            startAutoPlay(true);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
        if (getAdapter() != null) {
            getAdapter().unregisterDataSetObserver(dataSetObserver);
        }
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }
}