package com.gw.banner.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

import com.gw.banner.R;

public class BannerConfig {
    private static final float density = Resources.getSystem().getDisplayMetrics().density;

    public static final int TIME = 3000;
    public static final boolean IS_AUTO_PLAY = true;

    //默认字体大小
    public static final int DEFAULT_TEXT_SIZE = 15;
    //默认字体颜色
    public static final ColorStateList DEFAULT_TEXT_COLOR = ColorStateList.valueOf(Color.WHITE);

    //指示器之间距离
    public static final int INDICATOR_SPACE = (int) (5 * density);
    //指示器距离边缘
    public static final int DEFAULT_INDICATOR_EDGE_MARGIN = (int) (10 * density);
    //Banner为空时，默认显示图片
    public static int BANNER_EMPTY_RES_ID = R.drawable.no_banner;

    //Simple指示器默认值
    public static final int DEFAULT_SIMPLE_INDICATOR_WIDTH = (int) (7 * density);
    public static final int DEFAULT_SIMPLE_INDICATOR_HEIGHT = (int) (7 * density);
    public static final int DEFAULT_SIMPLE_INDICATOR_GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    //Number指示器默认值
    public static final int DEFAULT_NUM_INDICATOR_WIDTH = (int) (35 * density);
    public static final int DEFAULT_NUM_INDICATOR_HEIGHT = (int) (35 * density);
    public static final int DEFAULT_NUM_INDICATOR_GRAVITY = Gravity.RIGHT | Gravity.BOTTOM;
    //Title指示器默认值
    public static final int DEFAULT_TITLE_INDICATOR_HEIGHT = (int) (40 * density);
    public static final int DEFAULT_TITLE_INDICATOR_PADDING_LEFT = (int) (15 * density);
    public static final int DEFAULT_TITLE_INDICATOR_PADDING_RIGHT = (int) (15 * density);
    public static final int DEFAULT_TITLE_INDICATOR_SPACE = (int) (15 * density);
    public static final Drawable DEFAULT_TITLE_INDICATOR_BACKGROUND = new ColorDrawable(0x77000000);
}