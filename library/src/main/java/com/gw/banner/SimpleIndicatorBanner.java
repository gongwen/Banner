package com.gw.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.gw.banner.util.BannerConfig;
import com.gw.banner.util.Util;

import java.util.List;

public class SimpleIndicatorBanner<T, V extends View> extends AIndicatorBanner<T, V> {
    protected Drawable mSelectedDrawable;
    protected Drawable mUnSelectedDrawable;
    protected int mIndicatorWidth = BannerConfig.DEFAULT_SIMPLE_INDICATOR_WIDTH;
    protected int mIndicatorHeight = BannerConfig.DEFAULT_SIMPLE_INDICATOR_HEIGHT;
    protected int indicatorSpace = BannerConfig.INDICATOR_SPACE;
    protected int hIndicatorEdgeMargin = BannerConfig.DEFAULT_INDICATOR_EDGE_MARGIN;
    protected int vIndicatorEdgeMargin = BannerConfig.DEFAULT_INDICATOR_EDGE_MARGIN;
    protected int indicatorGravity = BannerConfig.DEFAULT_SIMPLE_INDICATOR_GRAVITY;

    protected LinearLayout indicatorContainer;

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
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleIndicatorBanner);
            mSelectedDrawable = a.getDrawable(R.styleable.SimpleIndicatorBanner_indicatorSelectedDrawable);
            mUnSelectedDrawable = a.getDrawable(R.styleable.SimpleIndicatorBanner_indicatorUnSelectedDrawable);
            //如果没有设置指示器的宽高，则取mSelectedDrawable或mUnSelectedDrawable的宽高
            if (a.hasValue(R.styleable.SimpleIndicatorBanner_indicatorWidth)) {
                mIndicatorWidth = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_indicatorWidth, -1);
            } else if (mSelectedDrawable != null && mSelectedDrawable.getIntrinsicWidth() != -1) {
                mIndicatorWidth = mSelectedDrawable.getIntrinsicWidth();
            } else if (mUnSelectedDrawable != null && mUnSelectedDrawable.getIntrinsicWidth() != -1) {
                mIndicatorWidth = mUnSelectedDrawable.getIntrinsicWidth();
            }
            if (a.hasValue(R.styleable.SimpleIndicatorBanner_indicatorHeight)) {
                mIndicatorHeight = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_indicatorHeight, -1);
            } else if (mSelectedDrawable != null && mSelectedDrawable.getIntrinsicHeight() != -1) {
                mIndicatorHeight = mSelectedDrawable.getIntrinsicHeight();
            } else if (mUnSelectedDrawable != null && mUnSelectedDrawable.getIntrinsicHeight() != -1) {
                mIndicatorHeight = mUnSelectedDrawable.getIntrinsicHeight();
            }
            //指示器间水平间距
            indicatorSpace = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_indicatorSpace, indicatorSpace);
            //指示器距离边缘的距离
            hIndicatorEdgeMargin = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_hIndicatorEdgeMargin, hIndicatorEdgeMargin);
            vIndicatorEdgeMargin = a.getDimensionPixelSize(R.styleable.SimpleIndicatorBanner_vIndicatorEdgeMargin, vIndicatorEdgeMargin);
            //指示器Gravity位置
            indicatorGravity = a.getInt(R.styleable.SimpleIndicatorBanner_indicatorGravity, indicatorGravity);
            a.recycle();
        }

        if (mSelectedDrawable == null) {
            mSelectedDrawable = getDefaultSelectDrawable();
        }
        if (mUnSelectedDrawable == null) {
            mUnSelectedDrawable = getDefaultUnSelectDrawable();
        }
        indicatorContainer = findViewById(R.id.indicatorContainer);
        initSimpleIndicatorAttribute();
    }

    protected void initSimpleIndicatorAttribute() {
        LayoutParams params = ((LayoutParams) indicatorContainer.getLayoutParams());
        params.setMargins(hIndicatorEdgeMargin, vIndicatorEdgeMargin, hIndicatorEdgeMargin, vIndicatorEdgeMargin);
        params.gravity = indicatorGravity;

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

    @Override
    protected int getViewLayoutId() {
        return R.layout.banner_indicator_simple;
    }

    protected Drawable getDefaultSelectDrawable() {
        return Util.getDrawable(getContext(), R.drawable.default_circle_white);
    }

    protected Drawable getDefaultUnSelectDrawable() {
        return Util.getDrawable(getContext(), R.drawable.default_circle_gray);
    }
    // <editor-fold desc="工具方法">

    public void setData(List<T> dataList) {
        adapter.setData(dataList);
        indicatorContainer.removeAllViews();
        final int realCount = adapter.getRealCount();
        emptyImageView.setVisibility(realCount == 0 ? VISIBLE : GONE);

        if (realCount > 1) {
            for (int i = 0; i < realCount; i++) {
                View mView = new View(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
                if (i != realCount - 1) {
                    params.rightMargin = indicatorSpace;
                }
                ViewCompat.setBackground(mView, i == 0 ? mSelectedDrawable : mUnSelectedDrawable);
                indicatorContainer.addView(mView, params);
            }
        }
    }

    /**
     * 设置指示器宽高
     *
     * @param mIndicatorWidth
     * @param mIndicatorHeight
     */
    public void setIndicatorSize(int mIndicatorWidth, int mIndicatorHeight) {
        this.mIndicatorWidth = mIndicatorWidth;
        this.mIndicatorHeight = mIndicatorHeight;
        for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
            View mView = indicatorContainer.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mView.getLayoutParams();
            params.width = mIndicatorWidth;
            params.height = mIndicatorHeight;
            mView.setLayoutParams(params);
        }
    }

    /**
     * 设置指示器之间距离
     *
     * @param indicatorSpace
     */
    public void setIndicatorSpace(int indicatorSpace) {
        this.indicatorSpace = indicatorSpace;
        for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
            View mView = indicatorContainer.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mView.getLayoutParams();
            params.leftMargin = indicatorSpace;
            mView.setLayoutParams(params);
        }
    }

    /**
     * 设置指示器距离边缘margin
     *
     * @param hIndicatorEdgeMargin
     * @param vIndicatorEdgeMargin
     */
    public void setIndicatorEdgeMargin(int hIndicatorEdgeMargin, int vIndicatorEdgeMargin) {
        this.hIndicatorEdgeMargin = hIndicatorEdgeMargin;
        this.vIndicatorEdgeMargin = vIndicatorEdgeMargin;
        LayoutParams params = ((LayoutParams) indicatorContainer.getLayoutParams());
        params.setMargins(hIndicatorEdgeMargin, vIndicatorEdgeMargin, hIndicatorEdgeMargin, vIndicatorEdgeMargin);
    }

    /**
     * 设置指示器选中Drawable
     *
     * @param mSelectedDrawable
     */
    public void setSelectedDrawable(Drawable mSelectedDrawable) {
        this.mSelectedDrawable = mSelectedDrawable;
    }

    /**
     * 设置指示器未选中Drawable
     *
     * @param mUnSelectedDrawable
     */
    public void setUnSelectedDrawable(Drawable mUnSelectedDrawable) {
        this.mUnSelectedDrawable = mUnSelectedDrawable;
    }

    /**
     * 设置指示器显示位置
     *
     * @param gravity
     */
    public void setIndicatorGravity(int gravity) {
        this.indicatorGravity = gravity;
        ((LayoutParams) indicatorContainer.getLayoutParams()).gravity = gravity;
    }
    // </editor-fold>
}