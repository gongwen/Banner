package com.gw.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.listener.OnBannerItemClickListener;
import com.gw.banner.loader.ViewLoaderInterface;
import com.gw.banner.util.BannerConfig;

public class AIndicatorBanner<T, V extends View> extends FrameLayout {
    protected final DisplayMetrics displayMetrics;
    protected BannerViewPager bannerViewPager;
    protected ImageView emptyImageView;
    protected BannerPagerAdapter<T, V> adapter;

    public AIndicatorBanner(@NonNull Context context) {
        this(context, null);
    }

    public AIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        displayMetrics = getResources().getDisplayMetrics();
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;
        int delayTime = BannerConfig.TIME;
        //Banner为空时，默认显示图占位图
        int emptyBannerResId = R.drawable.no_banner;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AIndicatorBanner);
            isAutoPlay = a.getBoolean(R.styleable.AIndicatorBanner_isAutoPlay, isAutoPlay);
            delayTime = a.getInt(R.styleable.AIndicatorBanner_delayTime, delayTime);
            emptyBannerResId = a.getResourceId(R.styleable.AIndicatorBanner_emptyBannerDrawable, emptyBannerResId);
            a.recycle();
        }

        LayoutInflater.from(context).inflate(getViewLayoutId(), this, true);
        bannerViewPager = findViewById(R.id.bannerViewPager);
        bannerViewPager.setAutoPlay(isAutoPlay);
        bannerViewPager.setDelayTime(delayTime);

        emptyImageView = findViewById(R.id.emptyImageView);
        emptyImageView.setImageResource(emptyBannerResId);

        adapter = new BannerPagerAdapter<>(getContext());
        bannerViewPager.setAdapter(adapter);
    }

    protected int getViewLayoutId() {
        return R.layout.banner_indicator;
    }
    // <editor-fold desc="工具方法">

    /**
     * 设置轮播View的数据填充模型
     *
     * @param viewLoader
     */
    public void setViewLoader(ViewLoaderInterface<T, V> viewLoader) {
        adapter.setViewLoader(viewLoader);
    }

    /**
     * 设置轮播View的Item点击事件
     *
     * @param mOnBannerItemClickListener
     */
    public void setOnBannerItemClickListener(OnBannerItemClickListener<T, V> mOnBannerItemClickListener) {
        adapter.setOnBannerItemClickListener(mOnBannerItemClickListener);
    }

    public BannerPagerAdapter<T, V> getAdapter() {
        return adapter;
    }

    public ImageView getEmptyImageView() {
        return emptyImageView;
    }

    /**
     * 设置是否自动播放
     *
     * @param isAutoPlay
     */
    public void setAutoPlay(boolean isAutoPlay) {
        bannerViewPager.setAutoPlay(isAutoPlay);
    }

    public void setDelayTime(long delayTime) {
        bannerViewPager.setDelayTime(delayTime);
    }

    public void startPlay() {
        bannerViewPager.startPlay();
    }

    public void stopPlay() {
        bannerViewPager.stopPlay();
    }

    /**
     * Banner数据为空时显示的占位图
     *
     * @param drawable
     */
    public void setBannerEmptyDrawable(Drawable drawable) {
        emptyImageView.setImageDrawable(drawable);
    }
    // </editor-fold>
}