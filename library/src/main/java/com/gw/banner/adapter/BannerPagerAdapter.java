package com.gw.banner.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.gw.banner.listener.OnBannerItemClickListener;
import com.gw.banner.loader.ViewLoaderInterface;
import com.gw.banner.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GongWen on 17/11/9.
 * 假如真实数据为ABCDEF，则在ViewPager中填充为FABCDEFA,以实现循环滑动
 */

public class BannerPagerAdapter<T, V extends View> extends PagerAdapter {
    private static final String TAG = "BannerPagerAdapter";

    private Context context;
    private ViewLoaderInterface<T, V> viewLoader;
    private final List<T> dataList = new ArrayList<>();
    private final List<V> mViews = new ArrayList<>();
    private OnBannerItemClickListener<T, V> mOnBannerItemClickListener;

    public BannerPagerAdapter(Context context) {
        this(context, null);
    }

    public BannerPagerAdapter(Context context, ViewLoaderInterface<T, V> viewLoader) {
        this(context, viewLoader, null);
    }

    public BannerPagerAdapter(Context context, ViewLoaderInterface<T, V> viewLoader, List<T> dataList) {
        this.context = context;
        this.viewLoader = viewLoader;
        if (dataList != null) {
            this.dataList.addAll(dataList);
            if (viewLoader != null) {
                createViewList();
            }
        }
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final V mView = mViews.get(position);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBannerItemClickListener != null) {
                    final int realPosition = Util.toRealPosition(getRealCount(), position);
                    mOnBannerItemClickListener.OnBannerItemClick(mView, dataList.get(realPosition), realPosition);
                }
            }
        });
        container.addView(mView);
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    public int getRealCount() {
        return dataList.size();
    }

    public void setData(List<T> dataList) {
        this.dataList.clear();
        this.mViews.clear();
        if (dataList != null) {
            this.dataList.addAll(dataList);
        }
        createViewList();
        notifyDataSetChanged();
    }

    public void setViewLoader(ViewLoaderInterface<T, V> viewLoader) {
        this.viewLoader = viewLoader;
    }

    public ViewLoaderInterface<T, V> getViewLoader() {
        return viewLoader;
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener<T, V> mOnBannerItemClickListener) {
        this.mOnBannerItemClickListener = mOnBannerItemClickListener;
    }

    public void createViewList() {
        if (viewLoader == null) {
            throw new IllegalStateException("viewLoader must be not null,please set the viewLoader firstly!");
        }
        if (dataList.size() == 0) {
            return;
        }
        final int count = getRealCount();
        if (count == 1) {
            V mView = viewLoader.createView(context);
            mViews.add(mView);
            viewLoader.fillData(context, mView, dataList.get(0), 0);
            return;
        }
        for (int i = 0; i < count + 2; i++) {
            V mView = viewLoader.createView(context);
            mViews.add(mView);
            int position;
            if (i == 0) {
                position = count - 1;
            } else if (i == count + 1) {
                position = 0;
            } else {
                position = i - 1;
            }
            viewLoader.fillData(context, mView, dataList.get(position), position);
        }
    }
}
