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
 * 假如真实数据为ABCDEF，则在ViewPager中填充为FABCDEFA
 */

public class BannerPagerAdapter<T, V extends View> extends PagerAdapter {
    private static final String TAG = "BannerPagerAdapter";

    private Context context;
    private ViewLoaderInterface<T, V> viewLoader;
    private final List<T> dataList = new ArrayList<>();
    private final List<V> mViews = new ArrayList<>();
    private OnBannerItemClickListener mOnBannerItemClickListener;

    public BannerPagerAdapter(Context context, ViewLoaderInterface<T, V> viewLoader) {
        this(context, viewLoader, null);
    }

    public BannerPagerAdapter(Context context, ViewLoaderInterface<T, V> viewLoader, List<T> dataList) {
        this.context = context;
        this.viewLoader = viewLoader;
        if (dataList != null) {
            this.dataList.addAll(dataList);
        }
        createViewList();
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
        final View mView = mViews.get(position);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBannerItemClickListener != null) {
                    mOnBannerItemClickListener.OnBannerItemClick(Util.toRealPosition(getRealCount(), position));
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

    public void createViewList() {
        if (viewLoader == null) {
            throw new IllegalArgumentException("ViewLoaderInterface must be not null!");
        }
        final int count = getRealCount();
        if (count == 0) {
            return;
        }
        if (count == 1) {
            V mView = viewLoader.createView(context);
            mViews.add(mView);
            viewLoader.fillData(context, dataList.get(0), mView);
            return;
        }
        for (int i = 0; i < count + 2; i++) {
            V mView = viewLoader.createView(context);
            mViews.add(mView);
            T data;
            if (i == 0) {
                data = dataList.get(count - 1);
            } else if (i == count + 1) {
                data = dataList.get(0);
            } else {
                data = dataList.get(i - 1);
            }
            viewLoader.fillData(context, data, mView);
        }
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

    public int getRealCount() {
        return dataList.size();
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener mOnBannerItemClickListener) {
        this.mOnBannerItemClickListener = mOnBannerItemClickListener;
    }
}
