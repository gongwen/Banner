package com.gw.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.loader.ViewLoaderInterface;
import com.gw.banner.util.BannerConfig;

import java.util.List;

/**
 * Created by GongWen on 17/11/10.
 */

public abstract class BaseBannerView<T, V extends View> extends FrameLayout implements IBanner<T, V> {
    protected DisplayMetrics dm;
    protected BannerViewPager bannerViewPager;
    protected ImageView emptyBannerView;
    protected BannerPagerAdapter<T, V> adapter;

    private long delayTime;
    private boolean isAutoPlay;
    protected int emptyBannerResId = R.drawable.no_banner;
    protected int hIndicatorMargin;
    protected int vIndicatorMargin;

    public BaseBannerView(@NonNull Context context) {
        this(context, null);
    }

    public BaseBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseBannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dm = getResources().getDisplayMetrics();
        hIndicatorMargin = (int) (15 * dm.density + 0.5f);
        vIndicatorMargin = (int) (15 * dm.density + 0.5f);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseBannerView);
            delayTime = a.getInt(R.styleable.BaseBannerView_delayTime, BannerConfig.TIME);
            isAutoPlay = a.getBoolean(R.styleable.BaseBannerView_isAutoPlay, BannerConfig.IS_AUTO_PLAY);
            emptyBannerResId = a.getResourceId(R.styleable.BaseBannerView_emptyBannerDrawable, emptyBannerResId);
            hIndicatorMargin = a.getDimensionPixelSize(R.styleable.BaseBannerView_hIndicatorMargin, hIndicatorMargin);
            vIndicatorMargin = a.getDimensionPixelSize(R.styleable.BaseBannerView_vIndicatorMargin, vIndicatorMargin);
            a.recycle();
        }
        LayoutInflater.from(context).inflate(getLayout(), this, true);
        emptyBannerView = findViewById(R.id.emptyView);
        emptyBannerView.setImageResource(emptyBannerResId);
        bannerViewPager = findViewById(R.id.bannerViewPager);
        bannerViewPager.setAutoPlay(isAutoPlay);
        bannerViewPager.setDelayTime(delayTime);
        adapter = new BannerPagerAdapter<T, V>(getContext());
        bannerViewPager.setAdapter(adapter);
    }

    public void setViewLoader(ViewLoaderInterface<T, V> viewLoader) {
        adapter.setViewLoader(viewLoader);
    }

    public BaseBannerView<T, V> setData(List<T> dataList) {
        if (adapter.getViewLoader() == null) {
            throw new IllegalStateException("viewLoader must be not null,please set the viewLoader firstly!");
        }
        onDataChanged(dataList == null || dataList.size() == 0);
        return this;
    }

    public abstract int getLayout();

    public void onDataChanged(boolean isEmpty) {
        emptyBannerView.setVisibility(isEmpty ? VISIBLE : GONE);
    }
}
