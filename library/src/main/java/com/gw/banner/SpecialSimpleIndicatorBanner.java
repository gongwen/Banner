package com.gw.banner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

public class SpecialSimpleIndicatorBanner<T, V extends View> extends SimpleIndicatorBanner<T, V> {

    protected FrameLayout flIndicator;
    protected View indicatorSelectView;

    public SpecialSimpleIndicatorBanner(@NonNull Context context) {
        this(context, null);
    }

    public SpecialSimpleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialSimpleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        flIndicator = findViewById(R.id.flIndicator);
        indicatorSelectView = findViewById(R.id.indicatorSelectView);

        LayoutParams params = ((LayoutParams) flIndicator.getLayoutParams());
        params.setMargins(hIndicatorEdgeMargin, vIndicatorEdgeMargin, hIndicatorEdgeMargin, vIndicatorEdgeMargin);
        params.gravity = indicatorGravity;
        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                final int indicatorCount = indicatorContainer.getChildCount();
                final int itemWidth = indicatorSpace + mIndicatorWidth;

                if (position == 0 && positionOffset < 0) {
                    if (positionOffset >= -0.5) {
                        indicatorSelectView.setTranslationX(0);
                    } else {
                        indicatorSelectView.setTranslationX((indicatorCount - 1) * itemWidth);
                    }
                } else if (position == indicatorCount - 1 && positionOffset > 0) {
                    if (positionOffset >= 0.5) {
                        indicatorSelectView.setTranslationX(0);
                    } else {
                        indicatorSelectView.setTranslationX((indicatorCount - 1) * itemWidth);
                    }
                } else {
                    indicatorSelectView.setTranslationX(itemWidth * (position + positionOffset));
                }
            }
        });
    }

    @Override
    protected void initSimpleIndicatorAttribute() {
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.banner_indicator_special_simple;
    }

    // <editor-fold desc="工具方法">

    public void setData(List<T> dataList) {
        adapter.setData(dataList);
        indicatorContainer.removeAllViews();
        final int realCount = adapter.getRealCount();
        emptyImageView.setVisibility(realCount == 0 ? VISIBLE : GONE);
        flIndicator.setVisibility(realCount == 0 ? GONE : VISIBLE);

        if (realCount > 1) {
            for (int i = 0; i < realCount; i++) {
                View mView = new View(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
                if (i != realCount - 1) {
                    params.rightMargin = indicatorSpace;
                }
                ViewCompat.setBackground(mView, mUnSelectedDrawable);
                indicatorContainer.addView(mView, params);
            }
            LayoutParams params = (LayoutParams) indicatorSelectView.getLayoutParams();
            params.width = mIndicatorWidth;
            params.height = mIndicatorHeight;
            indicatorSelectView.setLayoutParams(params);
            ViewCompat.setBackground(indicatorSelectView, mSelectedDrawable);
        }
    }

    /**
     * 设置指示器宽高
     *
     * @param mIndicatorWidth
     * @param mIndicatorHeight
     */
    public void setIndicatorSize(int mIndicatorWidth, int mIndicatorHeight) {
        super.setIndicatorSize(mIndicatorWidth, mIndicatorHeight);
        LayoutParams params = (LayoutParams) indicatorSelectView.getLayoutParams();
        params.width = mIndicatorWidth;
        params.height = mIndicatorHeight;
        indicatorSelectView.setLayoutParams(params);
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
        LayoutParams params = ((LayoutParams) flIndicator.getLayoutParams());
        params.setMargins(hIndicatorEdgeMargin, vIndicatorEdgeMargin, hIndicatorEdgeMargin, vIndicatorEdgeMargin);
    }

    /**
     * 设置指示器选中Drawable
     *
     * @param mSelectedDrawable
     */
    public void setSelectedDrawable(Drawable mSelectedDrawable) {
        super.setSelectedDrawable(mSelectedDrawable);
        ViewCompat.setBackground(indicatorSelectView, mSelectedDrawable);
    }

    /**
     * 设置指示器显示位置
     *
     * @param gravity
     */
    public void setIndicatorGravity(int gravity) {
        this.indicatorGravity = gravity;
        ((LayoutParams) flIndicator.getLayoutParams()).gravity = gravity;
    }
    // </editor-fold>
}