package com.gw.banner.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;

import java.util.List;

/**
 * Created by GongWen on 17/11/9.
 */

public class Util {
    private Util() {
    }

    /**
     * @param count(adapter.getRealCount())
     * @param position
     * @return
     */
    public static int toRealPosition(int count, int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }

    public static int px2dp(Context context, float px) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }


    public static int dp2px(Context context, float dp) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public static int px2sp(Context context, float px) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity + 0.5f);
    }

    public static int sp2px(Context context, float sp) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    public static boolean isEmpty(List mList) {
        return mList == null || mList.size() == 0;
    }

    public static Drawable getDrawable(@NonNull Context mContext, @DrawableRes int id) {
        return getDrawable(mContext.getResources(), id, mContext.getTheme());
    }

    public static Drawable getDrawable(@NonNull Resources res, @DrawableRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(res, id, theme);
    }
}
