package com.gw.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by GongWen on 17/11/9.
 */

public class SpacialCircleIndicatorBanner<T, V extends View> extends SimpleIndicatorBanner<T, V> {
    private ImageView selectedImageView;

    public SpacialCircleIndicatorBanner(@NonNull Context context) {
        this(context, null);
    }

    public SpacialCircleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpacialCircleIndicatorBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectedImageView = findViewById(R.id.selectedImageView);
//        selectedImageView.setBackgroundResource(mIndicatorSelectedResId);
    }

   /* @Override
    public int getLayout() {
        return R.layout.banner_indicator_special_circle;
    }

    @Override
    protected void createIndicators(List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                View mView = new View(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
                params.leftMargin = hIndicatorPadding;
                mView.setBackgroundResource(mIndicatorUnselectedResId);
                indicatorContainer.addView(mView, params);
            }
            LayoutParams lp = new LayoutParams(mIndicatorWidth, mIndicatorHeight);
            lp.gravity = indicatorGravity;
            int margin;
            if(dataList.size()%2==0){}else {

            }
            int leftMargin = 0;
            int rightMargin = 20;
            lp.setMargins(leftMargin, vIndicatorMargin, rightMargin, vIndicatorMargin);
            selectedImageView.setLayoutParams(lp);
        }
    }

    @Override
    public void onDataChanged(boolean isEmpty) {
        super.onDataChanged(isEmpty);
        selectedImageView.setVisibility(isEmpty ? GONE : VISIBLE);
    }

    @Override
    public void setIndicatorListener() {
        bannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("TAg", positionOffset + "," + positionOffsetPixels);
                selectedImageView.setTranslationX((hIndicatorPadding + mIndicatorWidth) * (position + positionOffset));
            }
        });
    }*/
}
