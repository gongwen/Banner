package com.gw.banner.listener;

import android.view.View;

/**
 * Created by GongWen on 17/11/9.
 */

public interface OnBannerItemClickListener<T, V extends View> {
    void OnBannerItemClick(V mView, T mData, int mPosition);
}
