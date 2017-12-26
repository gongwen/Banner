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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GongWen on 17/11/9.
 */

public class SimpleTitleIndicatorBanner<T, V extends View> extends SimpleIndicatorBanner<T, V> {

    private RelativeLayout indicatorContainerView;
    private TextView titleTv;

    private List<String> titleList;

    public SimpleTitleIndicatorBanner(@NonNull Context context) {
        this(context, null);
    }

    public SimpleTitleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleTitleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        Drawable titleIndicatorBackground = BannerConfig.DEFAULT_TITLE_INDICATOR_BACKGROUND;
        int titleIndicatorHeight = BannerConfig.DEFAULT_TITLE_INDICATOR_HEIGHT;

        int titleIndicatorTextSize = BannerConfig.DEFAULT_TEXT_SIZE;
        ColorStateList titleIndicatorTextColor = BannerConfig.DEFAULT_TEXT_COLOR;

        int titleIndicatorPaddingLeft = BannerConfig.DEFAULT_TITLE_INDICATOR_PADDING_LEFT;
        int titleIndicatorPaddingRight = BannerConfig.DEFAULT_TITLE_INDICATOR_PADDING_RIGHT;
        int titleIndicatorSpace = BannerConfig.DEFAULT_TITLE_INDICATOR_SPACE;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleTitleIndicatorBanner);
            if (a.hasValue(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorBackground)) {
                titleIndicatorBackground = a.getDrawable(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorBackground);
            }
            if (a.hasValue(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorHeight)) {
                titleIndicatorHeight = a.getDimensionPixelSize(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorHeight, -1);
            } else if (titleIndicatorBackground.getIntrinsicHeight() != -1) {
                titleIndicatorHeight = titleIndicatorBackground.getIntrinsicHeight();
            }

            if (a.hasValue(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorTextSize)) {
                titleIndicatorTextSize = Util.px2sp(context, a.getDimensionPixelSize(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorTextSize, -1));
            }
            if (a.hasValue(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorTextColor)) {
                titleIndicatorTextColor = a.getColorStateList(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorTextColor);
            }

            titleIndicatorPaddingLeft = a.getDimensionPixelSize(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorPaddingLeft, titleIndicatorPaddingLeft);
            titleIndicatorPaddingRight = a.getDimensionPixelSize(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorPaddingRight, titleIndicatorPaddingRight);
            titleIndicatorSpace = a.getDimensionPixelSize(R.styleable.SimpleTitleIndicatorBanner_titleIndicatorSpace, titleIndicatorSpace);

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

        indicatorContainer.setPadding(titleIndicatorSpace, 0, 0, 0);
        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                titleTv.setText(titleList.get(position));
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
    protected void initSimpleIndicatorAttribute() {
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.banner_indicator_simple_title;
    }

    // <editor-fold desc="工具方法">

    public void setData(List<T> dataList, List<String> titleList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        this.titleList = titleList == null ? new ArrayList<String>() : titleList;
        if (dataList.size() != this.titleList.size()) {
            throw new IllegalArgumentException("The size of DataList and the size of TitleList must be equal!");
        }
        super.setData(dataList);

        final int realCount = adapter.getRealCount();
        indicatorContainerView.setVisibility(realCount == 0 ? GONE : VISIBLE);
        if (realCount > 0) {
            titleTv.setText(this.titleList.get(0));
        }
    }

    public void setTitleIndicatorBackground(Drawable background) {
        ViewCompat.setBackground(indicatorContainerView, background);
    }

    public void setTitleIndicatorBackgroundColor(@ColorInt int color) {
        indicatorContainerView.setBackgroundColor(color);
    }

    public void setTitleIndicatorBackgroundResource(@DrawableRes int resId) {
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
        indicatorContainer.setPadding(space, 0, 0, 0);
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
// </editor-fold>
}
