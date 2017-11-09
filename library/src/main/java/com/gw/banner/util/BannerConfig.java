package com.gw.banner.util;

import android.content.res.Resources;

import com.gw.banner.R;

public class BannerConfig {
    private static final float density = Resources.getSystem().getDisplayMetrics().density;

    public static final int TIME = 3000;
    public static final boolean IS_AUTO_PLAY = true;

    public static final int TEXT_SIZE = (int) (15 * density + 0.5f);
    public static final int TEXT_PADDING = (int) (8 * density + 0.5f);

    //指示器之间距离
    public static final int INDICATOR_SPACE = (int) (5 * density + 0.5f);
    //指示器距离边缘
    public static final int INDICATOR_EDGE_MARGIN = (int) (10 * density + 0.5f);
    //Banner为空时，默认显示图片
    public static int BANNER_EMPTY_RES_ID = R.drawable.no_banner;

}