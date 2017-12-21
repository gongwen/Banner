package com.gw.banner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.gw.banner.util.BannerConfig;
import com.gw.banner.util.Util;

import java.util.List;

/**
 * Created by GongWen on 17/11/9.
 */

public class NumberIndicatorBanner<T, V extends View> extends AIndicatorBanner {

    protected TextView indicatorTv;

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
        int mIndicatorWidth = BannerConfig.DEFAULT_NUM_INDICATOR_WIDTH;
        int mIndicatorHeight = BannerConfig.DEFAULT_NUM_INDICATOR_HEIGHT;
        int hIndicatorEdgeMargin = BannerConfig.DEFAULT_INDICATOR_EDGE_MARGIN;
        int vIndicatorEdgeMargin = BannerConfig.DEFAULT_INDICATOR_EDGE_MARGIN;
        int indicatorGravity = BannerConfig.DEFAULT_NUM_INDICATOR_GRAVITY;
        Drawable indicatorBackground = null;

        float numTextSize = BannerConfig.DEFAULT_TEXT_SIZE;
        ColorStateList numTextColor = null;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberIndicatorBanner);
            if (a.hasValue(R.styleable.NumberIndicatorBanner_numTextSize)) {
                numTextSize = Util.px2sp(context, a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_numTextSize, -1));
            }
            numTextColor = a.getColorStateList(R.styleable.NumberIndicatorBanner_numTextColor);
            indicatorBackground = a.getDrawable(R.styleable.NumberIndicatorBanner_indicatorBackground);
            //如果没有设置指示器的宽高，则取mSelectedDrawable或mUnSelectedDrawable的宽高
            if (a.hasValue(R.styleable.NumberIndicatorBanner_indicatorWidth)) {
                mIndicatorWidth = a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_indicatorWidth, -1);
            } else if (indicatorBackground != null) {
                mIndicatorWidth = indicatorBackground.getIntrinsicWidth();
            }
            if (a.hasValue(R.styleable.NumberIndicatorBanner_indicatorHeight)) {
                mIndicatorHeight = a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_indicatorHeight, -1);
            } else if (indicatorBackground != null) {
                mIndicatorHeight = indicatorBackground.getIntrinsicHeight();
            }
            //指示器距离边缘的距离
            hIndicatorEdgeMargin = a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_hIndicatorEdgeMargin, hIndicatorEdgeMargin);
            vIndicatorEdgeMargin = a.getDimensionPixelSize(R.styleable.NumberIndicatorBanner_vIndicatorEdgeMargin, vIndicatorEdgeMargin);
            //指示器Gravity位置
            indicatorGravity = a.getInt(R.styleable.NumberIndicatorBanner_indicatorGravity, indicatorGravity);
            a.recycle();
        }

        indicatorTv = findViewById(R.id.indicatorTv);
        indicatorTv.setTextSize(numTextSize);
        indicatorTv.setTextColor(numTextColor != null ? numTextColor : BannerConfig.DEFAULT_TEXT_COLOR);

        initNumberIndicatorAttribute(mIndicatorWidth, mIndicatorHeight, hIndicatorEdgeMargin, vIndicatorEdgeMargin, indicatorGravity, indicatorBackground);
    }

    protected void initNumberIndicatorAttribute(int mIndicatorWidth, int mIndicatorHeight, int hIndicatorEdgeMargin, int vIndicatorEdgeMargin, int indicatorGravity, Drawable indicatorBackground) {
        indicatorTv.setGravity(Gravity.CENTER);
        LayoutParams params = (LayoutParams) indicatorTv.getLayoutParams();
        params.width = mIndicatorWidth;
        params.height = mIndicatorHeight;
        params.setMargins(hIndicatorEdgeMargin, vIndicatorEdgeMargin, hIndicatorEdgeMargin, vIndicatorEdgeMargin);
        params.gravity = indicatorGravity;
        indicatorTv.setLayoutParams(params);

        if (indicatorBackground == null) {
            indicatorBackground = ContextCompat.getDrawable(getContext(), R.drawable.default_num_indicator_bg);
        }
        ViewCompat.setBackground(indicatorTv, indicatorBackground);

        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicatorTv.setText((position + 1) + "/" + adapter.getRealCount());
            }
        });
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.banner_indicator_number;
    }

    // <editor-fold desc="工具方法">

    public void setData(List<T> dataList) {
        adapter.setData(dataList);
        boolean isEmpty = adapter.getRealCount() == 0;
        indicatorTv.setVisibility(isEmpty ? GONE : VISIBLE);
        emptyImageView.setVisibility(isEmpty ? VISIBLE : GONE);
        if (!isEmpty) {
            indicatorTv.setText("1/" + adapter.getRealCount());
        }
    }

    /**
     * 设置指示器距离边缘margin
     *
     * @param hIndicatorEdgeMargin
     * @param vIndicatorEdgeMargin
     */
    public void setIndicatorEdgeMargin(int hIndicatorEdgeMargin, int vIndicatorEdgeMargin) {
        LayoutParams params = ((LayoutParams) indicatorTv.getLayoutParams());
        params.setMargins(hIndicatorEdgeMargin, vIndicatorEdgeMargin, hIndicatorEdgeMargin, vIndicatorEdgeMargin);
    }

    /**
     * 设置指示器显示位置
     *
     * @param gravity
     */
    public void setIndicatorGravity(int gravity) {
        ((LayoutParams) indicatorTv.getLayoutParams()).gravity = gravity;
    }

    public void setNumTextSize(float size) {
        indicatorTv.setTextSize(size);
    }

    public void setNumTextSize(int unit, float size) {
        indicatorTv.setTextSize(unit, size);
    }

    public void setNumTextColor(@ColorInt int color) {
        indicatorTv.setTextColor(color);
    }

    public void setNumTextColor(ColorStateList colors) {
        indicatorTv.setTextColor(colors);
    }

    public void setIndicatorSize(int indicatorWidth, int indicatorHeight) {
        LayoutParams params = (LayoutParams) indicatorTv.getLayoutParams();
        params.width = indicatorWidth;
        params.height = indicatorHeight;
        indicatorTv.setLayoutParams(params);
    }

    public void setIndicatorBackgroundColor(@ColorInt int color) {
        indicatorTv.setBackgroundColor(color);
    }

    public void setIndicatorCircleBackgroundColor(@ColorInt int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(color);
        ViewCompat.setBackground(indicatorTv, gradientDrawable);
    }

    public void setIndicatorBackground(Drawable background) {
        ViewCompat.setBackground(indicatorTv, background);
    }

    public void setIndicatorBackgroundResource(@DrawableRes int resId) {
        indicatorTv.setBackgroundResource(resId);
    }
    // </editor-fold>
}
