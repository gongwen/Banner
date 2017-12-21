package com.gw.banner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gw.banner.util.BannerConfig;
import com.gw.banner.util.Util;

import java.util.List;

/**
 * Created by GongWen on 17/11/9.
 */

public class NumberTitleIndicatorBanner<T, V extends View> extends NumberIndicatorBanner {

    private RelativeLayout indicatorContainerView;
    private TextView titleTv;

    private List<String> titleList;

    public NumberTitleIndicatorBanner(@NonNull Context context) {
        this(context, null);
    }

    public NumberTitleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberTitleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        //指示器背景高度设置
        Drawable titleIndicatorBackground = BannerConfig.DEFAULT_TITLE_INDICATOR_BACKGROUND;
        int titleIndicatorHeight = BannerConfig.DEFAULT_TITLE_INDICATOR_HEIGHT;
        //标题大小颜色设置
        int titleIndicatorTextSize = BannerConfig.DEFAULT_TEXT_SIZE;
        ColorStateList titleIndicatorTextColor = BannerConfig.DEFAULT_TEXT_COLOR;
        //指示器内间距设置
        int titleIndicatorPaddingLeft = BannerConfig.DEFAULT_TITLE_INDICATOR_PADDING_LEFT;
        int titleIndicatorPaddingRight = BannerConfig.DEFAULT_TITLE_INDICATOR_PADDING_RIGHT;
        int titleIndicatorSpace = BannerConfig.DEFAULT_TITLE_INDICATOR_SPACE;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberTitleIndicatorBanner);
            if (a.hasValue(R.styleable.NumberTitleIndicatorBanner_titleIndicatorBackground)) {
                titleIndicatorBackground = a.getDrawable(R.styleable.NumberTitleIndicatorBanner_titleIndicatorBackground);
            }
            if (a.hasValue(R.styleable.NumberTitleIndicatorBanner_titleIndicatorHeight)) {
                titleIndicatorHeight = a.getDimensionPixelSize(R.styleable.NumberTitleIndicatorBanner_titleIndicatorHeight, -1);
            } else if (titleIndicatorBackground.getIntrinsicHeight() != -1) {
                titleIndicatorHeight = titleIndicatorBackground.getIntrinsicHeight();
            }

            if (a.hasValue(R.styleable.NumberTitleIndicatorBanner_titleIndicatorTextSize)) {
                titleIndicatorTextSize = Util.px2sp(context, a.getDimensionPixelSize(R.styleable.NumberTitleIndicatorBanner_titleIndicatorTextSize, -1));
            }
            if (a.hasValue(R.styleable.NumberTitleIndicatorBanner_titleIndicatorTextColor)) {
                titleIndicatorTextColor = a.getColorStateList(R.styleable.NumberTitleIndicatorBanner_titleIndicatorTextColor);
            }
            titleIndicatorPaddingLeft = a.getDimensionPixelSize(R.styleable.NumberTitleIndicatorBanner_titleIndicatorPaddingLeft, titleIndicatorPaddingLeft);
            titleIndicatorPaddingRight = a.getDimensionPixelSize(R.styleable.NumberTitleIndicatorBanner_titleIndicatorPaddingRight, titleIndicatorPaddingRight);
            titleIndicatorSpace = a.getDimensionPixelSize(R.styleable.NumberTitleIndicatorBanner_titleIndicatorSpace, titleIndicatorSpace);

            a.recycle();
        }

        indicatorContainerView = findViewById(R.id.rlIndicatorContainer);
        ViewCompat.setBackground(indicatorContainerView, titleIndicatorBackground);
        indicatorContainerView.getLayoutParams().height = titleIndicatorHeight;
        indicatorContainerView.setPadding(titleIndicatorPaddingLeft, 0, titleIndicatorPaddingRight, 0);

        titleTv = findViewById(R.id.titleTv);
        titleTv.setTextSize(titleIndicatorTextSize);
        titleTv.setTextColor(titleIndicatorTextColor);
        titleTv.setSelected(true);

        ((RelativeLayout.LayoutParams) indicatorTv.getLayoutParams()).leftMargin = titleIndicatorSpace;

        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicatorTv.setText((position + 1) + "/" + adapter.getRealCount());
                titleTv.setText(titleList.get(position));
            }
        });
    }

    @Override
    protected void initNumberIndicatorAttribute(int mIndicatorWidth, int mIndicatorHeight, int hIndicatorEdgeMargin, int vIndicatorEdgeMargin, int indicatorGravity, Drawable indicatorBackground) {
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.banner_indicator_number_title;
    }
    // <editor-fold desc="工具方法">

    public void setData(List<T> dataList, List<String> titleList) {
        this.titleList = titleList;
        adapter.setData(dataList);
        boolean isEmpty = adapter.getRealCount() == 0;
        indicatorContainerView.setVisibility(isEmpty ? GONE : VISIBLE);
        emptyImageView.setVisibility(isEmpty ? VISIBLE : GONE);
        if (!isEmpty) {
            titleTv.setText(titleList.get(0));
            indicatorTv.setText("1/" + adapter.getRealCount());
        }
    }

    @Override
    public void setIndicatorBackground(Drawable background) {
        ViewCompat.setBackground(indicatorContainerView, background);
    }

    @Override
    public void setIndicatorBackgroundColor(@ColorInt int color) {
        indicatorContainerView.setBackgroundColor(color);
    }

    @Override
    public void setIndicatorBackgroundResource(@DrawableRes int resId) {
        indicatorContainerView.setBackgroundResource(resId);
    }

    public void setTitleIndicatorHeight(int height) {
        indicatorContainerView.getLayoutParams().height = height;
    }

    public void setTitleIndicatorTextSize(float size) {
        titleTv.setTextSize(size);
    }

    public void setTitleIndicatorTextSize(int unit, float size) {
        titleTv.setTextSize(unit, size);
    }

    public void setTitleIndicatorTextColor(@ColorInt int color) {
        titleTv.setTextColor(color);
    }

    public void setTitleIndicatorTextColor(ColorStateList colors) {
        titleTv.setTextColor(colors);
    }

    public void setTitleIndicatorHorizontalPadding(int paddingLeft, int paddingRight) {
        indicatorContainerView.setPadding(paddingLeft, 0, paddingRight, 0);
    }

    public void indicatorTextSpace(int space) {
        ((RelativeLayout.LayoutParams) indicatorTv.getLayoutParams()).leftMargin = space;
    }

    public TextView getTitleView() {
        return titleTv;
    }

    @Override
    public void setIndicatorEdgeMargin(int hIndicatorEdgeMargin, int vIndicatorEdgeMargin) {
        throw new UnsupportedOperationException("The edge margin of the indicator is not supported!");
    }

    @Override
    public void setIndicatorGravity(int gravity) {
        throw new UnsupportedOperationException("The gravity of the indicator is not supported!");
    }

    @Override
    public void setIndicatorSize(int indicatorWidth, int indicatorHeight) {
        throw new UnsupportedOperationException("The size of the indicator is not supported!");
    }

    public void setIndicatorCircleBackgroundColor(@ColorInt int color) {
        throw new UnsupportedOperationException("The CircleBackground of the indicator is not supported!");
    }
    // </editor-fold>
}
