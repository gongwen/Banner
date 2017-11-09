package com.gw.banner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.listener.OnBannerItemClickListener;
import com.gw.banner.loader.ViewLoaderInterface;
import com.gw.banner.util.BannerConfig;
import com.gw.banner.util.Util;

import java.util.List;

/**
 * Created by GongWen on 17/11/9.
 */

public class NumberIndicatorBanner<T, V extends View> extends FrameLayout {

    private int hIndicatorMargin;
    private int vIndicatorMargin;

    private BannerViewPager bannerViewPager;
    private ImageView emptyImageView;
    private TextView indicatorTv;

    private BannerPagerAdapter<T, V> adapter;

    public NumberIndicatorBanner(@NonNull Context context) {
        this(context, null);
    }

    public NumberIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        long delayTime;
        boolean isAutoPlay;
        float numTextSize;
        int numTextPadding;
        ColorStateList numTextColor = null;
        Drawable numTextBackGround = null;
        int emptyBannerResId;
        int indicatorGravity = Gravity.RIGHT | Gravity.BOTTOM;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberIndicatorBanner);
            isAutoPlay = a.getBoolean(R.styleable.NumberIndicatorBanner_isAutoPlay, BannerConfig.IS_AUTO_PLAY);
            delayTime = a.getInt(R.styleable.NumberIndicatorBanner_delayTime, BannerConfig.TIME);
            numTextSize = Util.px2sp(context, a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_numTextSize, BannerConfig.TEXT_SIZE));
            numTextColor = a.getColorStateList(R.styleable.NumberIndicatorBanner_numTextColor);
            numTextPadding = a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_numTextPadding, BannerConfig.TEXT_PADDING);
            numTextBackGround = a.getDrawable(R.styleable.NumberIndicatorBanner_numTextBackGround);
            hIndicatorMargin = a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_hIndicatorMargin, BannerConfig.INDICATOR_EDGE_MARGIN);
            vIndicatorMargin = a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_vIndicatorMargin, BannerConfig.INDICATOR_EDGE_MARGIN);
            indicatorGravity = a.getInt(R.styleable.NumberIndicatorBanner_indicatorGravity, indicatorGravity);
            emptyBannerResId = a.getResourceId(R.styleable.NumberIndicatorBanner_emptyBannerDrawable, BannerConfig.BANNER_EMPTY_RES_ID);
            a.recycle();
        } else {
            isAutoPlay = BannerConfig.IS_AUTO_PLAY;
            delayTime = BannerConfig.TIME;
            numTextSize = BannerConfig.TEXT_SIZE;
            numTextPadding = BannerConfig.TEXT_PADDING;
            hIndicatorMargin = BannerConfig.INDICATOR_EDGE_MARGIN;
            vIndicatorMargin = BannerConfig.INDICATOR_EDGE_MARGIN;
            emptyBannerResId = BannerConfig.BANNER_EMPTY_RES_ID;
        }

        LayoutInflater.from(context).inflate(R.layout.banner_indicator_number, this, true);
        bannerViewPager = findViewById(R.id.bannerViewPager);
        bannerViewPager.setAutoPlay(isAutoPlay);
        bannerViewPager.setDelayTime(delayTime);

        emptyImageView = findViewById(R.id.emptyImageView);
        emptyImageView.setImageResource(emptyBannerResId);

        indicatorTv = findViewById(R.id.indicatorTv);
        indicatorTv.setTextSize(numTextSize);
        indicatorTv.setTextColor(numTextColor != null ? numTextColor : ColorStateList.valueOf(Color.WHITE));
        indicatorTv.setPadding(numTextPadding, numTextPadding, numTextPadding, numTextPadding);
        if (numTextBackGround == null) {
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(0x77000000);
            gd.setShape(GradientDrawable.OVAL);
            numTextBackGround = gd;
        }
        ViewCompat.setBackground(indicatorTv, numTextBackGround);
        LayoutParams params = ((LayoutParams) indicatorTv.getLayoutParams());
        params.setMargins(hIndicatorMargin, vIndicatorMargin, hIndicatorMargin, vIndicatorMargin);
        params.gravity = indicatorGravity;

        adapter = new BannerPagerAdapter<>(getContext());
        bannerViewPager.setAdapter(adapter);
        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicatorTv.setText((position + 1) + "/" + adapter.getRealCount());
            }
        });
    }

    // <editor-fold desc="工具方法">
    public void setViewLoader(ViewLoaderInterface<T, V> viewLoader) {
        adapter.setViewLoader(viewLoader);
    }

    public void setData(List<T> dataList) {
        adapter.setData(dataList);
        boolean isEmptyData = dataList == null || dataList.size() == 0;
        indicatorTv.setVisibility(isEmptyData ? GONE : VISIBLE);
        emptyImageView.setVisibility(isEmptyData ? VISIBLE : GONE);
        if (!isEmptyData) {
            indicatorTv.setText("1/" + adapter.getRealCount());
        }
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener<T, V> mOnBannerItemClickListener) {
        if (mOnBannerItemClickListener == null)
            throw new IllegalArgumentException("The OnBannerItemClickListener must be not nul!");
        adapter.setOnBannerItemClickListener(mOnBannerItemClickListener);
    }
    // </editor-fold>

    // <editor-fold desc="属性方法">

    public NumberIndicatorBanner<T, V> setDelayTime(long delayTime) {
        bannerViewPager.setDelayTime(delayTime);
        return this;
    }

    /**
     * 设置是否自动播放
     *
     * @param isAutoPlay
     */
    public NumberIndicatorBanner setAutoPlay(boolean isAutoPlay) {
        bannerViewPager.setAutoPlay(isAutoPlay);
        return this;
    }

    /**
     * 设置指示器距离边缘margin
     *
     * @param hIndicatorMargin
     * @return
     */
    public NumberIndicatorBanner setIndicatorMargin(int hIndicatorMargin, int vIndicatorMargin) {
        LayoutParams params = ((LayoutParams) indicatorTv.getLayoutParams());
        params.setMargins(hIndicatorMargin, vIndicatorMargin, hIndicatorMargin, vIndicatorMargin);
        return this;
    }

    /**
     * 设置指示器显示位置
     *
     * @param gravity
     */
    public NumberIndicatorBanner setIndicatorGravity(int gravity) {
        ((LayoutParams) indicatorTv.getLayoutParams()).gravity = gravity;
        return this;
    }

    /**
     * Banner数据为空时显示的占位图
     *
     * @param drawable
     */
    public NumberIndicatorBanner setBannerEmptyDrawable(Drawable drawable) {
        emptyImageView.setImageDrawable(drawable);
        return this;
    }

    public NumberIndicatorBanner setNumTextSize(float size) {
        indicatorTv.setTextSize(size);
        return this;
    }

    public NumberIndicatorBanner setNumTextSize(int unit, float size) {
        indicatorTv.setTextSize(unit, size);
        return this;
    }

    public NumberIndicatorBanner setNumTextColor(@ColorInt int color) {
        indicatorTv.setTextColor(color);
        return this;
    }

    public NumberIndicatorBanner setNumTextColor(ColorStateList colors) {
        indicatorTv.setTextColor(colors);
        return this;
    }

    public NumberIndicatorBanner setNumTextPadding(int padding) {
        indicatorTv.setPadding(padding, padding, padding, padding);
        return this;
    }

    public NumberIndicatorBanner setNumBackgroundColor(@ColorInt int color) {
        indicatorTv.setBackgroundColor(color);
        return this;
    }

    public NumberIndicatorBanner setNumCircleBackgroundColor(@ColorInt int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(color);
        ViewCompat.setBackground(indicatorTv, gradientDrawable);
        return this;
    }

    public NumberIndicatorBanner setNumBackground(Drawable background) {
        ViewCompat.setBackground(indicatorTv, background);
        return this;
    }

    public NumberIndicatorBanner setNumBackgroundResource(@DrawableRes int resId) {
        indicatorTv.setBackgroundResource(resId);
        return this;
    }

    public BannerPagerAdapter<T, V> getAdapter() {
        return adapter;
    }
    // </editor-fold>
}
