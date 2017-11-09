package com.gw.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;

import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.listener.OnBannerItemClickListener;
import com.gw.banner.loader.ViewLoaderInterface;
import com.gw.banner.util.BannerConfig;

import java.util.List;

public class SimpleIndicatorBanner<T, V extends View> extends FrameLayout {
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int indicatorSpace;
    private Drawable mSelectedDrawable;
    private Drawable mUnSelectedDrawable;

    private BannerViewPager bannerViewPager;
    private ImageView emptyImageView;
    private LinearLayout indicatorContainer;

    private BannerPagerAdapter<T, V> adapter;

    public SimpleIndicatorBanner(@NonNull Context context) {
        this(context, null);
    }

    public SimpleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        long delayTime;
        boolean isAutoPlay;
        int indicatorEdgeMargin;
        int emptyBannerResId;
        int indicatorGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleIndicatorBanner);
            isAutoPlay = a.getBoolean(R.styleable.SimpleIndicatorBanner_isAutoPlay, BannerConfig.IS_AUTO_PLAY);
            delayTime = a.getInt(R.styleable.SimpleIndicatorBanner_delayTime, BannerConfig.TIME);
            mSelectedDrawable = a.getDrawable(R.styleable.SimpleIndicatorBanner_indicatorSelectedDrawable);
            mUnSelectedDrawable = a.getDrawable(R.styleable.SimpleIndicatorBanner_indicatorUnSelectedDrawable);
            //如果没有设置指示器的宽高，则取mSelectedDrawable或mUnSelectedDrawable的宽高
            if (a.hasValue(R.styleable.SimpleIndicatorBanner_indicatorWidth)) {
                mIndicatorWidth = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_indicatorWidth, -1);
            } else if (mSelectedDrawable != null) {
                mIndicatorWidth = mSelectedDrawable.getIntrinsicWidth();
            } else if (mUnSelectedDrawable != null) {
                mIndicatorWidth = mUnSelectedDrawable.getIntrinsicWidth();
            }
            if (a.hasValue(R.styleable.SimpleIndicatorBanner_indicatorHeight)) {
                mIndicatorHeight = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_indicatorHeight, -1);
            } else if (mSelectedDrawable != null) {
                mIndicatorHeight = mSelectedDrawable.getIntrinsicHeight();
            } else if (mUnSelectedDrawable != null) {
                mIndicatorHeight = mUnSelectedDrawable.getIntrinsicHeight();
            }
            indicatorSpace = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_indicatorSpace, BannerConfig.INDICATOR_SPACE);
            indicatorEdgeMargin = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_indicatorEdgeMargin, BannerConfig.INDICATOR_EDGE_MARGIN);
            emptyBannerResId = a.getResourceId(R.styleable.SimpleIndicatorBanner_emptyBannerDrawable, BannerConfig.BANNER_EMPTY_RES_ID);
            indicatorGravity = a.getInt(R.styleable.SimpleIndicatorBanner_indicatorGravity, indicatorGravity);
            a.recycle();
        } else {
            isAutoPlay = BannerConfig.IS_AUTO_PLAY;
            delayTime = BannerConfig.TIME;
            indicatorSpace = BannerConfig.INDICATOR_SPACE;
            indicatorEdgeMargin = BannerConfig.INDICATOR_EDGE_MARGIN;
            emptyBannerResId = BannerConfig.BANNER_EMPTY_RES_ID;
        }

        LayoutInflater.from(context).inflate(R.layout.banner_indicator_simple, this, true);
        bannerViewPager = findViewById(R.id.bannerViewPager);
        bannerViewPager.setAutoPlay(isAutoPlay);
        bannerViewPager.setDelayTime(delayTime);

        emptyImageView = findViewById(R.id.emptyImageView);
        emptyImageView.setImageResource(emptyBannerResId);

        indicatorContainer = findViewById(R.id.indicatorContainer);
        LayoutParams params = ((LayoutParams) indicatorContainer.getLayoutParams());
        params.setMargins(indicatorEdgeMargin, indicatorEdgeMargin, indicatorEdgeMargin, indicatorEdgeMargin);
        params.gravity = indicatorGravity;

        adapter = new BannerPagerAdapter<>(getContext());
        bannerViewPager.setAdapter(adapter);
        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
                    ViewCompat.setBackground(
                            indicatorContainer.getChildAt(i),
                            position == i ? mSelectedDrawable : mUnSelectedDrawable
                    );
                }
            }
        });
    }

    // <editor-fold desc="工具方法">
    public void setViewLoader(ViewLoaderInterface<T, V> viewLoader) {
        adapter.setViewLoader(viewLoader);
    }

    public void setData(List<T> dataList) {
        adapter.setData(dataList);
        indicatorContainer.removeAllViews();
        if (dataList != null && dataList.size() > 0) {
            emptyImageView.setVisibility(GONE);
            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    View mView = new View(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
                    params.leftMargin = indicatorSpace;
                    ViewCompat.setBackground(mView, i == 0 ? mSelectedDrawable : mUnSelectedDrawable);
                    indicatorContainer.addView(mView, params);
                }
            }
        } else {
            emptyImageView.setVisibility(VISIBLE);
        }
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener<T, V> mOnBannerItemClickListener) {
        if (mOnBannerItemClickListener == null)
            throw new IllegalArgumentException("The OnBannerItemClickListener must be not nul!");
        adapter.setOnBannerItemClickListener(mOnBannerItemClickListener);
    }
    // </editor-fold>

    // <editor-fold desc="属性方法">

    public SimpleIndicatorBanner setDelayTime(long delayTime) {
        bannerViewPager.setDelayTime(delayTime);
        return this;
    }

    /**
     * 设置是否自动播放
     *
     * @param isAutoPlay
     */
    public SimpleIndicatorBanner setAutoPlay(boolean isAutoPlay) {
        bannerViewPager.setAutoPlay(isAutoPlay);
        return this;
    }

    /**
     * 设置指示器宽高
     *
     * @param mIndicatorWidth
     */
    public SimpleIndicatorBanner setIndicatorSize(int mIndicatorWidth, int mIndicatorHeight) {
        this.mIndicatorWidth = mIndicatorWidth;
        this.mIndicatorHeight = mIndicatorHeight;
        return this;
    }

    /**
     * 设置指示器之间距离
     *
     * @param indicatorSpace
     */
    public SimpleIndicatorBanner setIndicatorSpace(int indicatorSpace) {
        this.indicatorSpace = indicatorSpace;
        return this;
    }

    /**
     * 设置指示器距离边缘margin
     *
     * @param indicatorEdgeMargin
     */
    public SimpleIndicatorBanner setIndicatorEdgeMargin(int indicatorEdgeMargin) {
        LayoutParams params = ((LayoutParams) indicatorContainer.getLayoutParams());
        params.setMargins(indicatorEdgeMargin, indicatorEdgeMargin, indicatorEdgeMargin, indicatorEdgeMargin);
        return this;
    }

    /**
     * 设置指示器选中Drawable
     *
     * @param mSelectedDrawable
     */
    public SimpleIndicatorBanner setSelectedDrawable(Drawable mSelectedDrawable) {
        this.mSelectedDrawable = mSelectedDrawable;
        return this;
    }

    /**
     * 设置指示器未选中Drawable
     *
     * @param mUnSelectedDrawable
     */
    public SimpleIndicatorBanner setUnSelectedDrawable(Drawable mUnSelectedDrawable) {
        this.mUnSelectedDrawable = mUnSelectedDrawable;
        return this;
    }

    /**
     * 设置指示器显示位置
     *
     * @param gravity
     */
    public SimpleIndicatorBanner setIndicatorGravity(int gravity) {
        ((LayoutParams) indicatorContainer.getLayoutParams()).gravity = gravity;
        return this;
    }

    /**
     * Banner数据为空时显示的占位图
     *
     * @param drawable
     */
    public SimpleIndicatorBanner setBannerEmptyDrawable(Drawable drawable) {
        emptyImageView.setImageDrawable(drawable);
        return this;
    }

    public BannerPagerAdapter<T, V> getAdapter() {
        return adapter;
    }
    // </editor-fold>
}