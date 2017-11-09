package com.gw.banner.listener;

import android.support.v4.view.ViewPager;

import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GongWen on 17/11/9.
 */

public class OnPageChangeListenerWrapper implements ViewPager.OnPageChangeListener {
    private BannerPagerAdapter adapter;
    private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        position = toRealPosition(position);
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                ViewPager.OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrolled(position, offset, offsetPixels);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        position = toRealPosition(position);
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                ViewPager.OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageSelected(position);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                ViewPager.OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        }
    }

    public void attachAdapter(BannerPagerAdapter adapter) {
        this.adapter = adapter;
    }

    public void onPageChangeListenerWrapper(ViewPager.OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public void onPageChangeListenersWrapper(ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        this.mOnPageChangeListeners.add(listener);
    }

    public void removeOnPageChangeListenerWrapper(ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    private int toRealPosition(int position) {
        if (adapter != null) {
            position = Util.toRealPosition(adapter.getRealCount(), position);
        }
        return position;
    }
}
